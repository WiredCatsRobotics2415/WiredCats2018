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

	public static final int LEFT_TALON_BACK = 2; 
	public static final int LEFT_TALON_FRONT = 1; 
	public static final int RIGHT_TALON_BACK = 3; 
	public static final int RIGHT_TALON_FRONT = 4;
  
	public static final int SHOOTER_TALON = 6; 
	public static final int CLIMBER_TALON = 9;
	public static final int GM_INTAKE = 8;
	public static final int CLIMBER_TALON2 = 5;
	
	public static final int GEAR_MANIP_SOLENOID = 4;
	
	
}

