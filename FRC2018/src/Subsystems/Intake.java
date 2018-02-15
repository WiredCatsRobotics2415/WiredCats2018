package Subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	
	final double INTAKE_SPEED = 0.69;
	
	public long startTime, waitTime = 500, ejectTime = 500;
	public boolean prismHeld = false;
	
	public static WPI_TalonSRX leftIntake, rightIntake;
	public static DoubleSolenoid grabber;
	public static DigitalInput IRDetector;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Intake() {
		leftIntake = new WPI_TalonSRX(RobotMap.LEFT_SIDE_ROLLER);
		rightIntake = new WPI_TalonSRX(RobotMap.RIGHT_SIDE_ROLLER);
		IRDetector = new DigitalInput(RobotMap.IR_PORT);
		
		leftIntake.setNeutralMode(NeutralMode.Brake);
		rightIntake.setNeutralMode(NeutralMode.Brake);
	}
	
	public void sideRoller(double speed) {
		double newSpeed = speed * INTAKE_SPEED;
		leftIntake.set(newSpeed);
		rightIntake.set(-newSpeed);
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
		
		sideRoller(1);
		
		if(!hasPrism()) {
			startTime = System.currentTimeMillis();
		}
		
		if (hasPrism() && System.currentTimeMillis() - startTime >= waitTime) {
			stopGrab();
		}
			
	}
	public void emptyPrism() {
		
		sideRoller(-1);
		
		if(hasPrism()) {
			startTime = System.currentTimeMillis();
		}
		
		if (!hasPrism() && System.currentTimeMillis() - startTime >= ejectTime) {
			stopGrab();
		}
	}
	
	public void stopGrab() {
		sideRoller(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

