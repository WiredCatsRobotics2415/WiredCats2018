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
		IRDetector = new DigitalInput(RobotMap.IR_PORT);
	}
	
	public void sideRoller(double speed) {
		double newSpeed = speed * INTAKE_SPEED;
		leftIntake.set(newSpeed);
		rightIntake.set(-newSpeed);
	}
	
	public boolean hasPrism() {
		return IRDetector.get();
	}
	
	public void grabPrism() {
			sideRoller(1);
	}
	public void emptyPrism() {
		//long startTime = System.currentTimeMillis();
		sideRoller(-1);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

