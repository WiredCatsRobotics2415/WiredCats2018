package Subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GroundIntake extends Subsystem {
	
	final double INTAKE_SPEED = 0.83;
	
	public long startTime, waitTime = 500, ejectTime = 500;
	
	public static WPI_TalonSRX leftIntake, rightIntake, leftUptake, rightUptake;
	public static DigitalInput IRDetector, liftLimit, dropLimit;
	
	public GroundIntake() {
		leftIntake = new WPI_TalonSRX(RobotMap.LEFT_SIDE_ROLLER);
		rightIntake = new WPI_TalonSRX(RobotMap.RIGHT_SIDE_ROLLER);
		
		leftUptake = new WPI_TalonSRX(RobotMap.LEFT_UPTAKE);
		rightUptake = new WPI_TalonSRX(RobotMap.RIGHT_UPTAKE);
		
		IRDetector = new DigitalInput(RobotMap.IR_PORT);
		liftLimit = new DigitalInput(RobotMap.LIFT_LIMIT);
		dropLimit = new DigitalInput(RobotMap.DROP_LIMIT);
		
		leftIntake.setNeutralMode(NeutralMode.Brake);
		rightIntake.setNeutralMode(NeutralMode.Brake);
	}
	
	public void ferrisWheel(boolean up, double speed) {
		if (up && hitTop()) {
			leftUptake.set(speed);
			rightUptake.set(speed);
		} else if (!up && hitBottom()) {
			leftUptake.set(-speed);
			rightUptake.set(-speed);
		} else {
			leftUptake.set(0);
			rightUptake.set(0);
		}
 	}

	public boolean hitTop() {
		return !liftLimit.get();
	}
	
	public boolean hitBottom() {
		return !dropLimit.get();
	}
	
	public void sideRoller(double speed) {
		double newSpeed = speed * INTAKE_SPEED;
		leftIntake.set(newSpeed);
		rightIntake.set(-newSpeed);
	}
	
	public boolean hasPrism() {
		return IRDetector.get();
	}
	
	public void simpleIntake() {
		leftIntake.set(0.6*INTAKE_SPEED);
		rightIntake.set(-0.6*INTAKE_SPEED);
	}
	
	public void grabPrism() {
		
		if(!hasPrism()) {
			startTime = System.currentTimeMillis();
		}
		
		if (hasPrism() && Math.abs(System.currentTimeMillis() - startTime) >= waitTime) {
			sideRoller(0.5);
		} else {
			leftIntake.set(0.7*INTAKE_SPEED);
			rightIntake.set(-1.1*INTAKE_SPEED);
		}
			
	}
	
	public void turnPrism() {
			leftIntake.set(-0.5*INTAKE_SPEED);
			rightIntake.set(-0.9*INTAKE_SPEED);
			
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

