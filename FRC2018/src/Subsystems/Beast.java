package Subsystems;

import java.awt.Robot;

import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Beast extends Subsystem {

	public final byte SCALE = 0, SWITCH = 1;

	public byte shootAt;

	private WPI_TalonSRX lFar, lNear, rFar, rNear;
	private DigitalInput topLimit, bottomLimit;
	public Thread checker, shooter, descend;
	public boolean shooting = true, dropping = false, reachedBot = false;
	public volatile boolean thread = false, hitTop = false;
	public boolean checkerGoing = true;

	public boolean prevStateTop = false, prevStateBot = false;

	long ascentStopTime, stopTime;
	double kHeight = -13000;

	public Beast() {
		lFar = new WPI_TalonSRX(RobotMap.LEFT_FAR_SHOOTER);
		lNear = new WPI_TalonSRX(RobotMap.LEFT_NEAR_SHOOTER);
		rFar = new WPI_TalonSRX(RobotMap.RIGHT_FAR_SHOOTER);
		rNear = new WPI_TalonSRX(RobotMap.RIGHT_NEAR_SHOOTER);

		lNear.setInverted(true);
		lFar.setInverted(true);

		lFar.setNeutralMode(NeutralMode.Brake);
		lNear.setNeutralMode(NeutralMode.Brake);
		rFar.setNeutralMode(NeutralMode.Brake);
		rNear.setNeutralMode(NeutralMode.Brake);

		lNear.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

		topLimit = new DigitalInput(RobotMap.TOP_LIMIT);
		bottomLimit = new DigitalInput(RobotMap.BOTTOM_LIMIT);

	}

	public void testMotor(double speed) {
//		System.out.println("TOP: " + !topLimit.get() + "\tBOT: " + !bottomLimit.get());
		lNear.set(speed);
		lFar.set(speed);
		rNear.set(speed);
		rFar.set(speed);
	}

	public void resetBools() {
		shooting = true;
		dropping = false;
		reachedBot = false;
	}

	public void checkLimits() {
		// System.out.println("TOP: " + !topLimit.get());
		// System.out.println("\t BOT: " + !bottomLimit.get());
		if (!topLimit.get() != prevStateTop && !topLimit.get()) {
//			System.out.println("TOP: " + !topLimit.get());
			// System.out.println("STOP ME");
		} else if (!bottomLimit.get() != prevStateBot && !bottomLimit.get()) {
//			System.out.println("BOT: " + !bottomLimit.get());
			// System.out.println(".");
		}
		prevStateTop = !topLimit.get();
		prevStateBot = !bottomLimit.get();
	}

	public double getHeight() {
		return (double) lNear.getSelectedSensorPosition(0);
	}

	public void zeroShooterEncoder() {
		lNear.setSelectedSensorPosition(0, 0, 10);
	}

	public void shoot(byte state) {
		shootAt = state;
		checker.start();
		shooter.start();
	}

	public void stopShooter() {
		lFar.set(-0.05);
		lNear.set(-0.05);
		rFar.set(-0.05);
		rNear.set(-0.05);
	}

	public void backDown() {
		// zeroShooterEncoder();
		 lFar.set(-0.29); //-0.13
		lNear.set(-0.29);
		rFar.set(-0.29);
		 rNear.set(-0.29);
	}

	public void scaleShot() {
		lFar.set(1);
		lNear.set(1);
		rFar.set(1);
		rNear.set(1);
		
	}

	public void switchShot() {
		lFar.set(0.7);
		lNear.set(0.7);
		rFar.set(0.7);
		rNear.set(0.7);
	}
	
	public boolean encoderTop() {
		return getHeight() > 10000;
	}

	public void resetShooter() {
		if (!bottomLimit.get()) {
//			System.out.println(!bottomLimit.get());
//			System.out.println("STOP");
			lFar.set(0);
			lNear.set(0);
			rFar.set(0);
			rNear.set(0);
			resetBools();
		} else {
//			System.out.println(!bottomLimit.get());
//			System.out.println("GO");
			backDown();
		}
	}
	
	public void eStop() {
		lFar.set(0);
		lNear.set(0);
		rFar.set(0);
		rNear.set(0);
		
//		lFar.set(-0.05);
//		lNear.set(-0.05);
//		rFar.set(-0.05);
//		rNear.set(-0.05);
	}
	
	public boolean hitBottom() {
		return !bottomLimit.get();
	}
	
	public boolean reachTop() {
		return !topLimit.get();
	}
	
	public boolean isGoing() {
		return lFar.getMotorOutputPercent() > 0.01;
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
