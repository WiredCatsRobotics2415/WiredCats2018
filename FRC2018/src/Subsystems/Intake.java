package Subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	
	final double INTAKE_SPEED = 0.25;
	
	public long startTime, waitTime = 500;
	public boolean prismHeld = false;
	
	public static WPI_TalonSRX leftIntake, rightIntake;
	public static DoubleSolenoid grabber;
	public static DigitalInput IRDetector;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Intake() {
		leftIntake = new WPI_TalonSRX(RobotMap.LEFT_SIDE_ROLLER);
		rightIntake = new WPI_TalonSRX(RobotMap.RIGHT_SIDE_ROLLER);
		grabber = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.INTAKE_SOLENOID_FRONT, RobotMap.INTAKE_SOLENOID_BACK);
		IRDetector = new DigitalInput(RobotMap.IR_PORT);
	}
	
	public void sideRoller(double speed) {
		double newSpeed = speed * INTAKE_SPEED;
		leftIntake.set(newSpeed);
		rightIntake.set(-newSpeed);
	}
	
	public void holdPrism(boolean grip) {
		if(grip) {
			grabber.set(DoubleSolenoid.Value.kForward);
		} else {
			grabber.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
	public boolean clappersIn() {
		if(grabber.get() == DoubleSolenoid.Value.kForward) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasPrism() {
		return IRDetector.get();
	}
	
	public void grabPrism() {
		
		if(clappersIn()) {
			sideRoller(0);
		} else {
			sideRoller(1);
		}
		
		if(!hasPrism()) {
			startTime = System.currentTimeMillis();
		}
		
		if (!clappersIn() && hasPrism() && System.currentTimeMillis() - startTime >= waitTime) {
			holdPrism(true);
		}
			
	}
	public void emptyPrism() {
		//long startTime = System.currentTimeMillis();
		sideRoller(-1);
		if(!hasPrism()) {
			holdPrism(false);
		}
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

