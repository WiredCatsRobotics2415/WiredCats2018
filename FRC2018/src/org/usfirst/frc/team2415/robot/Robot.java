package org.usfirst.frc.team2415.robot;

import Cheesy.CheesyDriveHelper;
import Subsystems.ArcadeDrive;
import Subsystems.Beast;
import Subsystems.GroundIntake;
import Subsystems.VelocityDrive;
import autonomous.CrossAutoLine;
import autonomous.LeftDumpPrism;
import autonomous.LeftSwitch;
import autonomous.RightAroundLeftSwitch;
import autonomous.RightDumpPrism;
import autonomous.RightSwitch;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	public static XboxController gamepad;
	// public static XboxController rampsController;
	public static Compressor compressor;

	public static CheesyDriveHelper cheesyDriveHelper;

	public static ArcadeDrive arcadeDrive;
	public static VelocityDrive velocityDrive;
	// public static Intake intake;
	public static Beast beast;
	public static GroundIntake groundIntake;
	// public static Ramps ramps; //Added by Yash

	public char mySide;
	public long autoStopTime;
	public boolean shooting;
	public boolean rampDeployed;

	public boolean center = true;
	public boolean left = false;

	public DriverStation DS;


	// public static PowerDistributionPanel pdp;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		// chooser.addDefault("Default Auto", defaultAuto);
		// chooser.addObject("My Auto", customAuto);
		// SmartDashboard.putData("Auto choices", chooser);
		// UsbCamera camera = new UsbCamera("cam0", 0);
		// camera.setFPS(15);

		CameraServer.getInstance().startAutomaticCapture();

		gamepad = new XboxController(0);
		compressor = new Compressor(20);

		cheesyDriveHelper = new CheesyDriveHelper();

		arcadeDrive = new ArcadeDrive();
		// velocityDrive = new VelocityDrive();
		// intake = new Intake();
		beast = new Beast();
		groundIntake = new GroundIntake();
		// ramps = new Ramps(); //Added by Yash

		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
		DS = DriverStation.getInstance();
		//
		updateShuffle();

		// pdp = new PowerDistributionPanel(0);

		// setPeriod(0.02);

		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {

		// autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector");
		// defaultAuto);
		// System.out.println("Auto selected: " + autoSelected);
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
		arcadeDrive.setHighGear(true);

		// ramps.rampsOut(false);
		// ramps.platformsDown(false);

		String gameData;
		gameData = DS.getGameSpecificMessage();
		mySide = gameData.charAt(0);

		if (center) {
			if (mySide == 'R') {
				Command rightSwitch = new RightSwitch();
				rightSwitch.start();
			} else if (mySide == 'L') {
				Command leftSwitch = new LeftSwitch();
				leftSwitch.start();
			} else {
				Command crossLine = new CrossAutoLine();
				crossLine.start();
			}
		} else if (left) {
			if (mySide == 'R') {
				Command crossLine = new CrossAutoLine();
				crossLine.start();
			} else if (mySide == 'L') {
				Command dumpSwitch = new LeftDumpPrism();
				dumpSwitch.start();
			} else {
				Command crossLine = new CrossAutoLine();
				crossLine.start();
			}
		} else if (!left) {
			if (mySide == 'R') {
				Command dumpSwitch = new RightDumpPrism();
				dumpSwitch.start();
			} else if (mySide == 'L') {
				// Command crossLeft = new RightAroundLeftSwitch();
				Command crossLeft = new CrossAutoLine();
				crossLeft.start();
			} else {
				Command crossLine = new CrossAutoLine();
				crossLine.start();
			}
		} else {
			Command crossLine = new CrossAutoLine();
			crossLine.start();
		}

		// Command crossLine = new LeftSwitch();
		// crossLine.start();

		// double[][] waypoints = new double[][] {
		// { 1, 1 },
		// { 2, 1 },
		// { 5, 3 }
		// };

		// Command pathfind = new PathfindCommand(waypoints);
		// pathfind.start();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
//		groundIntake.testUptake(0.4);

		// updateShuffle();

		// if (Math.abs(System.currentTimeMillis() - autoStopTime) <= 1000) {
		// intake.sideRoller(-1);
		// } else {
		// intake.sideRoller(0);
		// }

		// System.out.println(Robot.arcadeDrive.getYaw());

		// switch (autoSelected) {
		// case customAuto:
		// // Put custom auto code here
		// break;
		// case defaultAuto:
		// default:
		// // Put default auto code here
		// break;
		// }
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopInit() {
		// System.out.println("START");
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
		arcadeDrive.setHighGear(true);
		// groundIntake.grabCube(false);
		// beast.zeroShooterEncoder();

		// ramps.rampsOut(false);
		// ramps.platformsDown(false);
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		updateShuffle();

		// compressor.start();

		double leftY;
		double rightX;

//		arcadeDrive.setHighGear(true);

		// if (gamepad.getBumper(Hand.kRight)) {
		// arcadeDrive.toggleHighGear();
		// // arcadeDrive.setHighGear(true);
		// // }
		// } else {
		// // arcadeDrive.setHighGear(false);
		// arcadeDrive.toggling = false;
		// }

		leftY = -Robot.gamepad.getRawAxis(1);
		rightX = Robot.gamepad.getRawAxis(4);

		if (Math.abs(leftY) < Math.abs(arcadeDrive.DEADBAND))
			leftY = 0;
		if (Math.abs(rightX) < Math.abs(arcadeDrive.DEADBAND))
			rightX = 0;

		boolean isQuickTurn = leftY < 0.1;

//		 if (arcadeDrive.isHighGear()) {
		arcadeDrive.drive(cheesyDriveHelper.cheesyDrive(leftY, rightX, isQuickTurn, true));
//		} else {
//			arcadeDrive.drive(cheesyDriveHelper.cheesyDrive(leftY, rightX, isQuickTurn, false));
//		}

		/*
		 * 
		 * if (gamepad.getTriggerAxis(Hand.kLeft) > 0.5) { intake.grabPrism(); } else if
		 * (gamepad.getTriggerAxis(Hand.kRight) > 0.5) { intake.emptyPrism(); } else if
		 * (gamepad.getBumper(Hand.kLeft)) { intake.turnPrism(); } else {
		 * intake.stopGrab(); }
		 * 
		 */
		
		if(gamepad.getBumper(Hand.kRight)) arcadeDrive.setHighGear(true);
//		if(gamepad.getRawButton(8)) arcadeDrive.setHighGear(false);
		
		if (gamepad.getTriggerAxis(Hand.kLeft) > 0.5) {
			groundIntake.grabPrism();
		} else if (gamepad.getTriggerAxis(Hand.kRight) > 0.5) {
			groundIntake.emptyPrism();
		} else if (gamepad.getBumper(Hand.kLeft)) {
			// groundIntake.groundSwitch(0.4);
			// if (groundIntake.hasPrism()) {
			// groundIntake.grabCube(true);
			// groundIntake.grabPrism();
			// } else {
			// groundIntake.grabCube(false);
			// }
			groundIntake.testUptake(0.69);
		} else if (gamepad.getRawButton(7)) {
			groundIntake.turnPrism();
		} else {
			groundIntake.stopGrab();
		}

		// if (groundIntake.hasPrism()) {
		// groundIntake.grabCube(true);
		// System.out.println("GOT");
		// } else {
		// groundIntake.grabCube(false);
		// System.out.println("NOT GOT");
		// }

		if (gamepad.getBButton()) {
			beast.switchShot();
			// beast.scaleShot();
		} else if (gamepad.getAButton()) {
			beast.switchShot();
			// beast.scaleShot();
		} else if (gamepad.getYButton()) {
			beast.resetBools();
			beast.eStop();
		} else if (gamepad.getXButton()) {
			beast.backDown();
		} else if (beast.hitBottom()) {
			 beast.eStop();
//			beast.stopShooter();
			beast.zeroShooterEncoder();
		} else if (beast.reachTop() || beast.encoderTop()) {
			beast.backDown();
		}

		/*
		 * if (gamepad.getAButton()) { // beast.switchShot(); sidney.fire(); } else if
		 * (gamepad.getBButton()) { // beast.switchShot(); sidney.nextFloor(); } else if
		 * (gamepad.getYButton()) { sidney.backDown(); } else if (gamepad.getXButton())
		 * { sidney.backDown(); } else if (sidney.scalePos() && sidney.isSearching()) {
		 * sidney.eStop(); } else if (sidney.switchPos() && sidney.isSearching()) {
		 * sidney.eStop(); } else if (sidney.hitBottom()) { sidney.eStop(); //
		 * sidney.stopShooter(); sidney.zeroShooterEncoder(); } else if
		 * (sidney.reachTop() || sidney.encoderTop()) { sidney.backDown(); }
		 */

		// if (rampsController.getTriggerAxis(Hand.kLeft) > 0.5) {
		// ramps.platformsDown(true);
		// } else if (rampsController.getTriggerAxis(Hand.kRight) > 0.5) {
		// ramps.rampsOut(true);
		// } else if (rampsController.getBumper(Hand.kLeft)){
		// ramps.rampsOut(false);
		// ramps.platformsDown(false);
		// }

	}

	public void testInit() {
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
		// beast.zeroShooterEncoder();

		// Command leftSwitch = new LeftSwitch();
		// leftSwitch.start();

		// compressor.start();

		// Command rightSwitch = new RightSwitch();
		// rightSwitch.start();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		// System.out.println("YAW: " + arcadeDrive.getYaw());
		updateShuffle();

		// if (beast.hitBottom()) {
		// beast.zeroShooterEncoder();
		// }

		// System.out.println("REACH TOP: " + beast.reachTop());

		System.out.println(arcadeDrive.getDistance()[0]);
		double leftY;
		leftY = -Robot.gamepad.getRawAxis(1);
		// System.out.println(beast.getHeight());

		// beast.testMotor(leftY * 0.65);
		// beast.printOutput();

		// System.out.println("HEIGHT: " + beast.getHeight());
		//
		// if (beast.hitBottom()) {
		// beast.zeroShooterEncoder();
		// }

		// groundIntake.testUptake(leftY);
		// groundIntake.getMotorOutput();
		// groundIntake.testShot(1);

	}

	public void disabledInit() {
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
	}

	public void disabledPeriodic() {
		// System.out.println(-Robot.gamepad.getRawAxis(1));
		Scheduler.getInstance().run();
		// System.out.println("R: " + velocityDrive.getDistance()[1] + "\tL: " +
		// velocityDrive.getDistance()[0]);
		// arcadeDrive.zeroEncoders();
		// arcadeDrive.zeroYaw();
		System.out.println(arcadeDrive.getDistance()[0]);

		updateShuffle();
		// System.out.println("REACH TOP: " + beast.reachTop());
		// System.out.println("REACH BOTTOM: " + beast.hitBottom());

		// System.out.println("YAW: " + arcadeDrive.getYaw());
		// System.out.println("IR SENSOR: " + intake.hasPrism());

	}

	public void updateShuffle() {
		SmartDashboard.putBoolean("HIGH GEAR: ", arcadeDrive.isHighGear());
		SmartDashboard.putBoolean("COMP ", compressor.enabled());
		SmartDashboard.putBoolean("SHOOTING: ", beast.isGoing());
		// SmartDashboard.putNumber("HEADING: ", arcadeDrive.getAngle());
		// SmartDashboard.putNumber("VELOCITY: ",
		// Math.abs(arcadeDrive.getVelocity()));
		SmartDashboard.putNumber("THROTTLE", -Robot.gamepad.getRawAxis(1));
		SmartDashboard.putNumber("YAW", Robot.arcadeDrive.getYaw());
		SmartDashboard.putBoolean("SHOOTER REACHED BOTTOM: ", beast.hitBottom());
		SmartDashboard.putBoolean("SHOOTER REACHED TOP: ", beast.reachTop());
		SmartDashboard.putBoolean("HAS PRISM: ", groundIntake.hasPrism());
		SmartDashboard.putBoolean("UPTAKE HIT TOP: ", groundIntake.hitTop());
		SmartDashboard.putBoolean("UPTAKE HIT BOTTOM: ", groundIntake.hitBottom());

	}
}
