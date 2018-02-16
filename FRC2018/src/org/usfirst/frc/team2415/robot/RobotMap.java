package org.usfirst.frc.team2415.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static final int PCM_ID = 20;

	public static final int LEFT_TALON_BACK = 0; 
	public static final int LEFT_TALON_FRONT = 1; 
	public static final int RIGHT_TALON_BACK = 3; 
	public static final int RIGHT_TALON_FRONT = 2;
	
	public static final int GEAR_SHIFTER_FRONT = 0;
	public static final int GEAR_SHIFTER_BACK = 7;
	
	public static final int LEFT_SIDE_ROLLER = 9;
	public static final int RIGHT_SIDE_ROLLER = 8;
	
	public static final int LEFT_FAR_SHOOTER = 14;
	public static final int LEFT_NEAR_SHOOTER = 15;
	public static final int RIGHT_FAR_SHOOTER = 12;
	public static final int RIGHT_NEAR_SHOOTER = 13;
	
	public static final int IR_PORT = 0;
	public static final int LIMIT_SWITCH = 3;
	
	
}

