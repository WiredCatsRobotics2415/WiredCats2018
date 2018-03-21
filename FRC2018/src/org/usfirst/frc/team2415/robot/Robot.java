package org.usfirst.frc.team2415.robot;

import Cheesy.CheesyDriveHelper;
import Subsystems.ArcadeDrive;
import Subsystems.Beast;
import Subsystems.Intake;
import Subsystems.Ramps;
import Subsystems.VelocityDrive;
import autonomous.CrossAutoLine;
import autonomous.LeftSwitch;
import autonomous.RightSwitch;
import autonomous.StraightDumpPrism;
import edu.wpi.cscore.UsbCamera;
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
	public static Compressor compressor;

	public static CheesyDriveHelper cheesyDriveHelper;

	public static ArcadeDrive arcadeDrive;
	public static VelocityDrive velocityDrive;
	public static Intake intake;
	public static Beast beast;
//	public static Ramps ramps; //Added by Yash
	
	public char mySide;
	public long autoStopTime;
	public boolean shooting;
	public boolean rampDeployed;

	public boolean center = true;
	public boolean left = false;

	// public static PowerDistributionPanel pdp;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// chooser.addDefault("Default Auto", defaultAuto);
		// chooser.addObject("My Auto", customAuto);
		// SmartDashboard.putData("Auto choices", chooser);
//		UsbCamera camera = new UsbCamera("cam0", 0);
//		camera.setFPS(15);
//		CameraServer.getInstance().startAutomaticCapture(camera);

		gamepad = new XboxController(0);
		compressor = new Compressor(20);

		cheesyDriveHelper = new CheesyDriveHelper();

		arcadeDrive = new ArcadeDrive();
		// velocityDrive = new VelocityDrive();
		intake = new Intake();
		beast = new Beast();
//		ramps = new Ramps(); //Added by Yash
		
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
		//
		updateShuffle();

		// pdp = new PowerDistributionPanel(0);
		
//		setPeriod(0.02);

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {

		// autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector");
		// defaultAuto);
		// System.out.println("Auto selected: " + autoSelected);
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();

		
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
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
				Command dumpSwitch = new StraightDumpPrism();
				dumpSwitch.start();
			} else {
				Command crossLine = new CrossAutoLine();
				crossLine.start();
			}
		} else if (!left) {
			if (mySide == 'R') {
				Command dumpSwitch = new StraightDumpPrism();
				dumpSwitch.start();
			} else if (mySide == 'L') {
				Command crossLine = new CrossAutoLine();
				crossLine.start();
			} else {
				Command crossLine = new CrossAutoLine();
				crossLine.start();
			}
		} else {
//			System.out.println("RUNNING");
			Command crossLine = new CrossAutoLine();
			crossLine.start();
		}
		
		
		

//		Command crossLine = new LeftSwitch();
//		crossLine.start();

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
		// beast.zeroShooterEncoder();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		// beast.checkLimits();
		// System.out.println(beast.getHeight());

		updateShuffle();

		// System.out.println("ENCODER LEFT: " + arcadeDrive.getDistance()[0] +
		// ", ENCODER RIGHT: " + arcadeDrive.getDistance()[1]);

		// System.out.println("IR SENSOR: " + intake.hasPrism());
		// System.out.println("YAW: " + arcadeDrive.getYaw());

		double leftY;
		double rightX;

		if (!gamepad.getBumper(Hand.kRight)) {
			arcadeDrive.setHighGear(true);
		} else {
			arcadeDrive.setHighGear(false);
		}

		leftY = -Robot.gamepad.getRawAxis(1);
		rightX = Robot.gamepad.getRawAxis(4);

		if (Math.abs(leftY) < Math.abs(arcadeDrive.DEADBAND))
			leftY = 0;
		if (Math.abs(rightX) < Math.abs(arcadeDrive.DEADBAND))
			rightX = 0;

		boolean isQuickTurn = leftY < 0.1;

		if (gamepad.getBumper(Hand.kRight)) {
			arcadeDrive.drive(cheesyDriveHelper.cheesyDrive(leftY, rightX, isQuickTurn, true));
		} else {
			arcadeDrive.drive(cheesyDriveHelper.cheesyDrive(leftY, rightX, isQuickTurn, false));
		}

		// Robot.velocityDrive.velDrive(leftY, rightX);
		// System.out.println(Robot.velocityDrive.getCurrent());

		if (gamepad.getTriggerAxis(Hand.kLeft) > 0.5) {
			intake.grabPrism();
		} else if (gamepad.getTriggerAxis(Hand.kRight) > 0.5) {
			intake.emptyPrism();
		} else if (gamepad.getBumper(Hand.kLeft)) {
			intake.turnPrism();
		} else {
			intake.stopGrab();
		}

		if (gamepad.getBButton()) {
//			beast.testShoot((byte) 1); // switch
			beast.switchShot();
		} else if (gamepad.getAButton()) {
//			beast.testShoot((byte) 0); // scale
//			beast.testShoot((byte) 1); 
			beast.switchShot();
		} else if (gamepad.getYButton()) {
//			System.out.println("RESET");
			beast.resetBools();
			beast.eStop();
		} else if (gamepad.getXButton()) {
			beast.resetShooter();
		} else if (beast.hitBottom()) {
			beast.eStop();
		} else if (beast.reachTop()) {
			beast.backDown();
		}
		
		//Added by Yash
//		if (gamepad.getBumper(Hand.kRight)) {
//			ramp.rampsOut(rampDeployed);
//		}

		// System.out.println("TELEOP");

	}

	public void testInit() {
		 arcadeDrive.zeroEncoders();
		 arcadeDrive.zeroYaw();
		// beast.zeroShooterEncoder();
		
		Command leftSwitch = new LeftSwitch();
		leftSwitch.start();
		
//		Command rightSwitch = new RightSwitch();
//		rightSwitch.start();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		// System.out.println("YAW: " + arcadeDrive.getYaw());
		updateShuffle();

		
		
		double leftY;
		 leftY = -Robot.gamepad.getRawAxis(1);
		// System.out.println(beast.getHeight());

		 beast.testMotor(leftY * 0.65);

		// updateShuffle();
		//
//		 System.out.println("ENCODER LEFT: " + arcadeDrive.getDistance()[0] +
//		 ", ENCODER RIGHT: " + arcadeDrive.getDistance()[1]);
		//
		// double leftY;
		// double rightX;

//		leftY = Robot.gamepad.getRawAxis(1);
		// System.out.println(leftY);
		// arcadeDrive.setOne(leftY);
//		rightX = Robot.gamepad.getRawAxis(4);
//
//		Robot.arcadeDrive.setVelocity(leftY * 9000, leftY * 9000);

		// Robot.velocityDrive.velDrive(leftY, rightX);

		//
		// if (Math.abs(leftY) < Math.abs(arcadeDrive.DEADBAND)) leftY = 0;
		// if (Math.abs(rightX) < Math.abs(arcadeDrive.DEADBAND)) rightX = 0;
		//
		//
		// leftY = arcadeDrive.INTERPOLATION_FACTOR*Math.pow(leftY, 3) +
		// (1-arcadeDrive.INTERPOLATION_FACTOR)*leftY;
		// rightX = arcadeDrive.INTERPOLATION_FACTOR*Math.pow(rightX, 3) +
		// (1-arcadeDrive.INTERPOLATION_FACTOR)*rightX;
		// //
		// double left = arcadeDrive.STRAIGHT_RESTRICTER*leftY +
		// arcadeDrive.TURN_SPEED_BOOST*rightX;
		// double right = arcadeDrive.STRAIGHT_RESTRICTER*leftY -
		// arcadeDrive.TURN_SPEED_BOOST*rightX;
		//
		// arcadeDrive.setMotors(left, right);

		// arcadeDrive.setMotors(leftY, -leftY);
		// System.out.println("OUTPUT: " + Robot.arcadeDrive.getMotorOutput() +
		// "\tVEL: " + Robot.arcadeDrive.getVelocity()[0]);
//		System.out.println(intake.hasPrism());

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

		updateShuffle();

//		 System.out.println("YAW: " + arcadeDrive.getYaw());
//		 System.out.println("IR SENSOR: " + intake.hasPrism());

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
		SmartDashboard.putBoolean("HAS PRISM: ", intake.hasPrism());

	}
}
