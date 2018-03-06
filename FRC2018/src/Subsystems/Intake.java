package Subsystems;

import java.awt.Robot;

import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	
	final double INTAKE_SPEED = 0.69;
	
	public long startTime, waitTime = 500, ejectTime = 500;
	
	public static WPI_TalonSRX leftIntake, rightIntake;
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
	
	public boolean hasPrism() {
		return IRDetector.get();
	}
	
	public void grabPrism() {
		
		
		if(!hasPrism()) {
			startTime = System.currentTimeMillis();
		}
		
		if (hasPrism() && Math.abs(System.currentTimeMillis() - startTime) >= waitTime) {
			sideRoller(0.5);
		} else {
//			sideRoller(1);
//			sideRoller(0.5);
			leftIntake.set(0.5*INTAKE_SPEED);
			rightIntake.set(-1.1*INTAKE_SPEED);
		}
			
	}
	
	public void turnPrism() {
		
		
//		if(!hasPrism()) {
//			startTime = System.currentTimeMillis();
//		}
		
//		if (hasPrism() && Math.abs(System.currentTimeMillis() - startTime) >= waitTime) {
//			sideRoller(0.5);
//		} else {
//			sideRoller(1);
//			sideRoller(0.5);
			leftIntake.set(-0.35*INTAKE_SPEED);
			rightIntake.set(-0.9*INTAKE_SPEED);
//		}
			
	}
	
	public void emptyPrism() {
		
		if(hasPrism()) {
			startTime = System.currentTimeMillis();
		}
		
		if (!hasPrism() && Math.abs(System.currentTimeMillis() - startTime) >= ejectTime) {
			stopGrab();
		} else {
			sideRoller(-1);
		}
		
	}
	
	public void openWheels() {
			sideRoller(-1);
	}
	
	public void stopGrab() {
		sideRoller(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

