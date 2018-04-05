package Subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Sidney extends Subsystem {

	private WPI_TalonSRX lFar, lNear, rFar, rNear;
	private DoubleSolenoid shooterShifter;
	private DigitalInput bottomLimit, topLimit;
	public boolean fired, searching;
	private Value freeSpin, gear;

	public Sidney() {
		lFar = new WPI_TalonSRX(RobotMap.LEFT_FAR_SHOOTER);
		lNear = new WPI_TalonSRX(RobotMap.LEFT_NEAR_SHOOTER);
		rFar = new WPI_TalonSRX(RobotMap.RIGHT_FAR_SHOOTER);
		rNear = new WPI_TalonSRX(RobotMap.RIGHT_NEAR_SHOOTER);

		// lNear.setInverted(true);
		// lFar.setInverted(true);

		shooterShifter = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.SIDNEY_SHIFTER_BACK,
				RobotMap.SIDNEY_SHIFTER_FRONT);

		lFar.setNeutralMode(NeutralMode.Brake);
		lNear.setNeutralMode(NeutralMode.Brake);
		rFar.setNeutralMode(NeutralMode.Brake);
		rNear.setNeutralMode(NeutralMode.Brake);

		lNear.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

		bottomLimit = new DigitalInput(RobotMap.BOTTOM_LIMIT);
		topLimit = new DigitalInput(RobotMap.TOP_LIMIT);

		freeSpin = DoubleSolenoid.Value.kForward;
		gear = DoubleSolenoid.Value.kReverse;
		
		searching = false;
		
	}

	public void testMotor(double speed) {
		// System.out.println("TOP: " + !topLimit.get() + "\tBOT: " +
		// !bottomLimit.get());
		shooterShifter.set(gear);
		lNear.set(speed);
		lFar.set(speed);
		rNear.set(speed);
		rFar.set(speed);
	}

	public double getHeight() {
		return (double) lNear.getSelectedSensorPosition(0);
	}

	public void zeroShooterEncoder() {
		lNear.setSelectedSensorPosition(0, 0, 10);
	}

	public void stopShooter() {
		shooterShifter.set(gear);
		lFar.set(-0.03);
		lNear.set(-0.03);
		rFar.set(-0.03);
		rNear.set(-0.03);
	}

	public void backDown() {
		searching = false;
		shooterShifter.set(gear);
		lFar.set(-0.43); // -0.33
		lNear.set(-0.43);
		rFar.set(-0.43);
		rNear.set(-0.43);
	}

	public void twitch() {
		lFar.set(0.1);
		lNear.set(0.1);
		rFar.set(0.1);
		rNear.set(0.1);
	}

	public void fire() {
		if (fired == false) {
			shooterShifter.set(freeSpin);
			twitch();
			fired = true;
		} else {
			twitch();
		}
	}

	public void nextFloor() {
		searching = true;
		shooterShifter.set(gear);
		lFar.set(0.4);
		lNear.set(0.4);
		rFar.set(0.4);
		rNear.set(0.4);
	}

	public boolean encoderTop() {
		return Math.abs(getHeight()) > 14500;
	}

	public void resetShooter() {
		if (!bottomLimit.get()) {
			// System.out.println(!bottomLimit.get());
			// System.out.println("STOP");
			lFar.set(0);
			lNear.set(0);
			rFar.set(0);
			rNear.set(0);
		} else {
			// System.out.println(!bottomLimit.get());
			// System.out.println("GO");
			backDown();
		}
	}

	public void eStop() {
		lFar.set(0);
		lNear.set(0);
		rFar.set(0);
		rNear.set(0);

		// lFar.set(-0.05);
		// lNear.set(-0.05);
		// rFar.set(-0.05);
		// rNear.set(-0.05);
	}

	public boolean hitBottom() {
		return !bottomLimit.get();
	}
	
	public boolean reachTop() {
		return !topLimit.get();
	}

	public boolean switchPos() {
		return Math.abs(getHeight()) > 14500 * 0.5;
	}

	public boolean scalePos() {
		return Math.abs(getHeight()) > 14500 * 0.2;
	}

	public boolean isGoing() {
		return lFar.getMotorOutputPercent() > 0.01;
	}
	
	public boolean isSearching() {
		return searching;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
