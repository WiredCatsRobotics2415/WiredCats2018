package org.usfirst.frc.team2415.robot;

import Subsystems.ArcadeDrive;
import Subsystems.ElevatorSubsystem;
import Subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	
	public static final int IR_SENSOR = 2;
	public static final int INTAKE_SOLENOID_1 = 3;
	public static final int INTAKE_SOLENOID_2 = 4;
	public static final int INTAKE_TALON_RIGHT = 5;
	public static final int INTAKE_TALON_LEFT = 6;
	public static final int ELEVATOR_TALON_1 = 7;
	public static final int ELEVATOR_TALON_2 = 8;
	
	public static XboxController gamepad;
	
	public static ArcadeDrive arcadeDrive;
	public static IntakeSubsystem intakeSubsystem;
	public static ElevatorSubsystem elevatorSubsytem;

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
		intakeSubsystem = new IntakeSubsystem();
		elevatorSubsytem = new ElevatorSubsystem();
		arcadeDrive = new ArcadeDrive();
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
		 autoSelected = SmartDashboard.getString("Auto Selector");
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
	@Override
	public void teleopPeriodic() {
		
		double leftY;
    	double rightX;
    	
    	if(arcadeDrive.getShifter() == DoubleSolenoid.Value.kForward) {
    	
    	leftY = Robot.gamepad.getRawAxis(1);
    	rightX = -Robot.gamepad.getRawAxis(4);
    	
    	} else {
    		
    		leftY = -Robot.gamepad.getRawAxis(1);
        	rightX = Robot.gamepad.getRawAxis(4);
    		
    	}
    	
    	if (Math.abs(leftY) < arcadeDrive.DEADBAND) leftY = 0;
    	if (Math.abs(rightX) < arcadeDrive.DEADBAND) rightX = 0;
    	
    	leftY = arcadeDrive.INTERPOLATION_FACTOR*Math.pow(leftY, 3) + (1-arcadeDrive.INTERPOLATION_FACTOR)*leftY;
    	rightX = arcadeDrive.INTERPOLATION_FACTOR*Math.pow(rightX, 3) + (1-arcadeDrive.INTERPOLATION_FACTOR)*rightX;
    	
    	double left = arcadeDrive.STRAIGHT_RESTRICTER*leftY + arcadeDrive.TURN_SPEED_BOOST*rightX;
    	double right = arcadeDrive.STRAIGHT_RESTRICTER*leftY - arcadeDrive.TURN_SPEED_BOOST*rightX;
		
    	if(gamepad.getAButton()) {
			new IntakeCommand(true);
		} else if(gamepad.getBButton()) {
			new IntakeCommand(false);
		}
		if(gamepad.getPOV() == 0) {
			new ElevatorCommand((byte)0);
		} else if(gamepad.getPOV() == 90) {
			new ElevatorCommand((byte)1);
		} else if(gamepad.getPOV() == 180) {
			new ElevatorCommand((byte)2);
		} else if(gamepad.getPOV() == 270) {
			new ElevatorCommand((byte)3);
		} else if(gamepad.getXButton()) {
			new ElevatorCommand((byte)4);
		}
    	
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

