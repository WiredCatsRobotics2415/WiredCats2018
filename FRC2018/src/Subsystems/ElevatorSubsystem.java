package Subsystems;

import org.usfirst.frc.team2415.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElevatorSubsystem extends Subsystem {
	public static final double GEAR_CIRCUMFERENCE = 5;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private CANTalon cantalon1;
	private CANTalon cantalon2;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public ElevatorSubsystem() {
    	cantalon1 = new CANTalon(Robot.ELEVATOR_TALON_1);
    	cantalon2 = new CANTalon(Robot.ELEVATOR_TALON_2);
    	cantalon1.setPosition(0);
    	cantalon2.setPosition(0);
    }
    
    public void setTalon(double speed) {
    	cantalon1.set(speed);
    	cantalon2.set(-speed);
    }
    //returns in inches
    public double getDistance() {
    	return (cantalon1.getPosition()*GEAR_CIRCUMFERENCE + cantalon2.getPosition()*GEAR_CIRCUMFERENCE * 0.5);
    }
}
