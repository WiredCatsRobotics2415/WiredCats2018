package org.usfirst.frc.team2415.robot;

import Cheesy.CheesyDriveHelper;
import Subsystems.ArcadeDrive;
import Subsystems.Intake;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
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
	public static Intake intake;
	
	public char mySide;
	public long autoStopTime;
	
//	public static PowerDistributionPanel pdp;
	

	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
//		chooser.addDefault("Default Auto", defaultAuto);
//		chooser.addObject("My Auto", customAuto);
//		SmartDashboard.putData("Auto choices", chooser);
		gamepad = new XboxController(0);
		compressor = new Compressor(20);

		cheesyDriveHelper = new CheesyDriveHelper();
		
		arcadeDrive = new ArcadeDrive();
		intake = new Intake();
		
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
		
		updateShuffle();
		
//		pdp = new PowerDistributionPanel(0);
		

		
		
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
//		autoSelected = chooser.getSelected();
//		 autoSelected = SmartDashboard.getString("Auto Selector");
		// defaultAuto);
//		System.out.println("Auto selected: " + autoSelected);
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
		
		autoStopTime = System.currentTimeMillis();
		
		String gameData;
//		gameData = DriverStation.getInstance().getGameSpecificMessage();
//		mySide = gameData.charAt(0);
		
		if (mySide == 'R') {
			
		} else {
			
		}
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		updateShuffle();
		
		if (Math.abs(System.currentTimeMillis() - autoStopTime) <= 1000) {
			intake.sideRoller(-1);
		} else {
			intake.sideRoller(0);
		}
		
//		switch (autoSelected) {
//		case customAuto:
//			// Put custom auto code here
//			break;
//		case defaultAuto:
//		default:
//			// Put default auto code here
//			break;
//		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	public void teleopInit() {
//		System.out.println("START");
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
	}
	
	@Override
	public void teleopPeriodic() {
		
		updateShuffle();
		
//		System.out.println("ENCODER LEFT: " + arcadeDrive.getDistance()[0] + 
//				", ENCODER RIGHT: " + arcadeDrive.getDistance()[1]);
//		
//		System.out.println(
//				"VELOCITY: " + arcadeDrive.getVelocity());
		
//		System.out.println("IR SENSOR: " + intake.hasPrism());
//		System.out.println("YAW: " + arcadeDrive.getYaw());
		
		double leftY;
    	double rightX;
    	
    	if(gamepad.getBumper(Hand.kLeft)) {
    		arcadeDrive.setHighGear(true);
    	} else {
    		arcadeDrive.setHighGear(false);
    	}

    		leftY = Robot.gamepad.getRawAxis(1);
        	rightX = -Robot.gamepad.getRawAxis(4);
    	
    	if (Math.abs(leftY) < Math.abs(arcadeDrive.DEADBAND)) leftY = 0;
    	if (Math.abs(rightX) < Math.abs(arcadeDrive.DEADBAND)) rightX = 0; 
		
		boolean isQuickTurn = leftY < 0.1;
		
//		leftY = arcadeDrive.INTERPOLATION_FACTOR*Math.pow(leftY, 3) + (1-arcadeDrive.INTERPOLATION_FACTOR)*leftY;
//    	rightX = arcadeDrive.INTERPOLATION_FACTOR*Math.pow(rightX, 3) + (1-arcadeDrive.INTERPOLATION_FACTOR)*rightX;
////    	
//    	double left = arcadeDrive.STRAIGHT_RESTRICTER*leftY + arcadeDrive.TURN_SPEED_BOOST*rightX;
//    	double right = arcadeDrive.STRAIGHT_RESTRICTER*leftY - arcadeDrive.TURN_SPEED_BOOST*rightX;
//    	
//    	arcadeDrive.setMotors(left, right);
		
		if(gamepad.getBumper(Hand.kLeft)) {
			arcadeDrive.drive(cheesyDriveHelper.cheesyDrive(leftY, rightX, isQuickTurn, true));
    	} else {
    		arcadeDrive.drive(cheesyDriveHelper.cheesyDrive(leftY, rightX, isQuickTurn, false));
    	}
		
//    	System.out.println(arcadeDrive.getBattery());
		
		if(gamepad.getTriggerAxis(Hand.kLeft) > 0.5) {
			intake.grabPrism();
		} else if (gamepad.getTriggerAxis(Hand.kRight) > 0.5) {
			intake.emptyPrism();
		} else {
			intake.stopGrab();
		}
    	
	}
	
	public void testInit() {
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		updateShuffle();
		
		System.out.println("ENCODER LEFT: " + arcadeDrive.getDistance()[0] + ", ENCODER RIGHT: " + arcadeDrive.getDistance()[1]);

		double leftY;
    	double rightX;

    		leftY = -Robot.gamepad.getRawAxis(1);
        	rightX = Robot.gamepad.getRawAxis(4);
    	
    	if (Math.abs(leftY) < Math.abs(arcadeDrive.DEADBAND)) leftY = 0;
    	if (Math.abs(rightX) < Math.abs(arcadeDrive.DEADBAND)) rightX = 0; 
		
		
		leftY = arcadeDrive.INTERPOLATION_FACTOR*Math.pow(leftY, 3) + (1-arcadeDrive.INTERPOLATION_FACTOR)*leftY;
    	rightX = arcadeDrive.INTERPOLATION_FACTOR*Math.pow(rightX, 3) + (1-arcadeDrive.INTERPOLATION_FACTOR)*rightX;
//    	
    	double left = arcadeDrive.STRAIGHT_RESTRICTER*leftY + arcadeDrive.TURN_SPEED_BOOST*rightX;
    	double right = arcadeDrive.STRAIGHT_RESTRICTER*leftY - arcadeDrive.TURN_SPEED_BOOST*rightX;
    	
    	arcadeDrive.setMotors(left, right);

	}
	
	public void disabledInit() {
		arcadeDrive.zeroEncoders();
		arcadeDrive.zeroYaw();
	}
	
	public void disabledPeriodic() {
		updateShuffle();
		
//		System.out.println("IR SENSOR: " + intake.IRDetector.get());
		
	}
	
	public void updateShuffle() {
		SmartDashboard.putBoolean("HIGH GEAR: ", arcadeDrive.isHighGear());
		SmartDashboard.putBoolean("COMP ", compressor.enabled());

		SmartDashboard.putNumber("HEADING: ", arcadeDrive.getAngle());
		SmartDashboard.putNumber("VELOCITY: ", Math.abs(arcadeDrive.getVelocity()));
		SmartDashboard.putNumber("THROTTLE", -Robot.gamepad.getRawAxis(1));
		SmartDashboard.putNumber("YAW", Robot.gamepad.getRawAxis(4));
		
	}
}

