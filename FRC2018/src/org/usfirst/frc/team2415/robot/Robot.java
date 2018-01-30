package org.usfirst.frc.team2415.robot;

import Cheesy.CheesyDriveHelper;
import Subsystems.ArcadeDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

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

		arcadeDrive = new ArcadeDrive();
		
		cheesyDriveHelper = new CheesyDriveHelper();
		
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
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
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
	}
	
	@Override
	public void teleopPeriodic() {
		
		double leftY;
    	double rightX;
    	
    	if(gamepad.getBumper(Hand.kLeft)) {
    		arcadeDrive.setHighGear(true);
    	} else {
    		arcadeDrive.setHighGear(false);
    	}
    	
//    	if(arcadeDrive.getShifter() == DoubleSolenoid.Value.kForward) {
//    	
//    		leftY = Robot.gamepad.getRawAxis(1);
//    		rightX = -Robot.gamepad.getRawAxis(4);
//    	
//    	} else {
    		
    		leftY = -Robot.gamepad.getRawAxis(1);
        	rightX = Robot.gamepad.getRawAxis(4);
    		
//    	}
    	
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
    	
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}
}

