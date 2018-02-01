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
	/*DOES NOT WORK, DON'T USE
	 * public boolean waitMillis(long startTime, long wait) {
		
		if (System.currentTimeMillis() < startTime + wait) {
			return true;
		} else {
			return false;
		}
	}*/
	public void grabPrism() {
		//long startTime = System.currentTimeMillis();
		Value pistonState = grabber.get();
		Value pistonOn = DoubleSolenoid.Value.kForward;
		Value pistonOff = DoubleSolenoid.Value.kReverse;
		if (pistonState == pistonOff && IRDetector.get()) {
			grabber.set(pistonOn);
			sideRoller(0);
		}
		if (!IRDetector.get()) {
			sideRoller(1);
		} else {
			sideRoller(0);
		}
	}
	public void emptyPrism() {
		//long startTime = System.currentTimeMillis();
		Value pistonState = grabber.get();
		Value pistonOn = DoubleSolenoid.Value.kForward;
		Value pistonOff = DoubleSolenoid.Value.kReverse;
		if (pistonState == pistonOn) {
			grabber.set(pistonOff);
		}
		sideRoller(-1);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

