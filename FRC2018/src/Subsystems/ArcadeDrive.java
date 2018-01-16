package Subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArcadeDrive extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private final CANTalon lFront, rFront, lBack, rBack;
    private final DoubleSolenoid shifter;
    private final int WHEEL_CIRCUMFERENCE = 5;

    public double DEADBAND = 0.05;
	public double STRAIGHT_RESTRICTER = 1; 
	public double TURN_SPEED_BOOST = 0.35;
	public double INTERPOLATION_FACTOR = 0.420;
    
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
	//if we win, be happy robot :)
	
	public ArcadeDrive() {
	
		lFront = new CANTalon(0, 1);
		rFront = new CANTalon(2, 3);
		lBack = new CANTalon(4, 5);
		rBack = new CANTalon(6, 7);
		
		shifter = new DoubleSolenoid(3, 4, 20);
	
	}
	
	public Value getShifter() {
		return shifter.get();
	}
	
	public void setMotors(double left, double right) {
		lFront.set(left);
		lBack.set(left);
		rFront.set(right);
		rBack.set(right);
	}
	
	public double[] getDistance() {
		return new double[]{lFront.getPosition()*WHEEL_CIRCUMFERENCE,
				rFront.getPosition()*WHEEL_CIRCUMFERENCE};
	}
	
}