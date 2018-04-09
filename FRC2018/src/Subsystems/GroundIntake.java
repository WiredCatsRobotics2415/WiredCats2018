package Subsystems;

import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GroundIntake extends Subsystem {

	final double INTAKE_SPEED = 0.83;

	public long startTime, waitTime = 500, ejectTime = 500;

	public static WPI_TalonSRX leftIntake, rightIntake, leftUptake, rightUptake;
	public static DigitalInput IRDetector, liftLimit, dropLimit;
//	public static DoubleSolenoid grabber;

	public GroundIntake() {
		leftIntake = new WPI_TalonSRX(RobotMap.LEFT_SIDE_ROLLER);
		rightIntake = new WPI_TalonSRX(RobotMap.RIGHT_SIDE_ROLLER);

		leftUptake = new WPI_TalonSRX(RobotMap.LEFT_UPTAKE);
		rightUptake = new WPI_TalonSRX(RobotMap.RIGHT_UPTAKE);
		rightUptake.setInverted(true);
		
//		grabber = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.GRABBER_FRONT, RobotMap.GRABBER_BACK);

		IRDetector = new DigitalInput(RobotMap.IR_PORT);
		liftLimit = new DigitalInput(RobotMap.LIFT_LIMIT);
		dropLimit = new DigitalInput(RobotMap.DROP_LIMIT);

		leftIntake.setNeutralMode(NeutralMode.Brake);
		rightIntake.setNeutralMode(NeutralMode.Brake);

		leftUptake.setNeutralMode(NeutralMode.Brake);
		rightUptake.setNeutralMode(NeutralMode.Brake);
	}

	public void testUptake(double speed) {
		if (Robot.gamepad.getTriggerAxis(Hand.kLeft) > 0.5) {
			grabPrism();
		} else if (Robot.gamepad.getTriggerAxis(Hand.kRight) > 0.5) {
			emptyPrism();
		} else {
			sideRoller(0);
		}
		leftUptake.set(speed);
		 rightUptake.set(speed);
	}
	
	public void getMotorOutput() {
		System.out.println("L: " + leftUptake.getMotorOutputPercent() + "\tR: " + rightUptake.getMotorOutputPercent());
	}

	public void ferrisWheel(boolean up, double speed) {
		if (up && !hitTop()) {
			leftUptake.set(speed);
			rightUptake.set(speed);
		} else if (!up && !hitBottom()) {
			leftUptake.set(-speed);
			rightUptake.set(-speed);
		} else {
			leftUptake.set(0);
			rightUptake.set(0);
		}
	}

	public void groundSwitch(double speed) {
		if (hasPrism()) {
			leftUptake.set(speed);
			rightUptake.set(speed);
		} else if (hitTop() && hasPrism()) {
			sideRoller((1 / INTAKE_SPEED) * INTAKE_SPEED);
		} else {
			sideRoller(0);
		}

	}
	
	public void testShot(double speed) {
		if (Robot.gamepad.getAButton()) {
			leftUptake.set(speed);
			rightUptake.set(speed);
		} else if (Robot.gamepad.getBButton()) {
			leftUptake.set(0);
			rightUptake.set(0);
		} else if (Robot.gamepad.getYButton()) {
			sideRoller((1 / INTAKE_SPEED) * INTAKE_SPEED);
		} else if (Robot.gamepad.getXButton()) {
			ferrisWheel(false, 0.3);
			sideRoller(0);
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

	public void simpleIntake(double speed) {
		leftIntake.set(speed * INTAKE_SPEED);
		rightIntake.set(-speed * INTAKE_SPEED);
	}

	public void grabPrism() {

		if (!hasPrism()) {
			startTime = System.currentTimeMillis();
			ferrisWheel(false, 0.65);
		}
		
//		if (hasPrism()) {
//			
//		}

		if (hasPrism() && Math.abs(System.currentTimeMillis() - startTime) >= waitTime) {
			sideRoller(0.7);
//			grabCube(true);
			// ferrisWheel(true, 0.5);
		} else {
			leftIntake.set(0.75 * INTAKE_SPEED);
			rightIntake.set(-1.0 * INTAKE_SPEED);
		}

	}

	public void turnPrism() {
		leftIntake.set(-0.5 * INTAKE_SPEED);
		rightIntake.set(-0.9 * INTAKE_SPEED);

	}

	public void emptyPrism() {
		
//		grabCube(false);
		
		if (hasPrism()) {
			startTime = System.currentTimeMillis();
		}

		if (!hasPrism() && Math.abs(System.currentTimeMillis() - startTime) >= ejectTime) {
			stopGrab();
		} else {
			sideRoller(-1.5);
//			ferrisWheel(false, 0.3);
		}

	}

	public void openWheels() {
		sideRoller(-1);
	}

	public void grabCube(boolean grabbing) {
//		if (grabbing) {
//			grabber.set(Value.kForward);
//		} else {
//			grabber.set(Value.kReverse);
//		}
	}
	
	public void stopGrab() {
		sideRoller(0);
		leftUptake.set(0);
		rightUptake.set(0);
//		ferrisWheel(false, 0.3);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
