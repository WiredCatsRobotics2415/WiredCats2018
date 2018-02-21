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

		checker = new Thread(new Runnable() {
			public void run() {
				while (!Thread.interrupted()) {
					if (!topLimit.get() /* || getHeight() - kHeight <= 50 */) {
						System.out.println("REACHED TOP");
						ascentStopTime = System.currentTimeMillis();
						shooter.interrupt();
						System.out.println("STOP TIME: " + ascentStopTime);
						hitTop = true;
					} else if (!bottomLimit.get() && hitTop) {
						System.out.println("REACHED BOTTOM");
						descend.interrupt();
						checkerGoing = false;
						hitTop = false;
						return;
					} else {
						// System.out.println("CHECKING");
					}
				}
			}
		});

		shooter = new Thread(new Runnable() {
			public void run() {
				while (!Thread.interrupted()) {
					if (shootAt == SCALE) {
						System.out.println("SHOOTING AT SCALE!");
						thread = true;
						// scaleShot();
					} else if (shootAt == SWITCH) {
						System.out.println("SHOOTING AT SWITCH!");
						thread = true;
//						 switchShot();
					}
				}
				System.out.println("STOPPING!");
				stopShooter();
				beginDescent();
				return;
			}
		});

		descend = new Thread(new Runnable() {
			public void run() {
				while (checkerGoing) {
					if (descend.interrupted()) {
						System.out.println("STOPPED GOING DOWN");
						stopShooter();
						// zeroShooterEncoder();
						thread = false;
						return;
					}
					while (!descend.isInterrupted()) {
						backDown();
						System.out.println("GOING DOWN NOW");
					}
				}
			}
		});

	}

	public void testMotor(double speed) {
			System.out.println(!topLimit.get());
			lNear.set(speed);
			rFar.set(speed);
	}

	public void resetBools() {
		shooting = true;
		dropping = false;
		reachedBot = false;
	}

	public void testShoot(byte state) {
		checkLimits();
		if (reachedBot) {
			shooting = (stopTime + 2000 <= System.currentTimeMillis());
		}

		if (!topLimit.get() && !dropping) {
//			System.out.println("REACHED TOP");
			stopShooter();
//			System.out.println("ENCODER AT: " + getHeight());
//			System.out.println("STOP TIME: " + ascentStopTime);
			dropping = true;
			shooting = false;
		} else if (!bottomLimit.get() == dropping && dropping && !shooting) {
//			System.out.println("REACHED BOTTOM");
			stopShooter();
			dropping = false;
			reachedBot = true;
			// zeroShooterEncoder();
		} else if (dropping && System.currentTimeMillis() > ascentStopTime + 1000) {
//			System.out.println("GOING DOWN");
			stopTime = System.currentTimeMillis();
			// System.out.println(dropping + "\t" + !bottomLimit.get());
			// System.out.println(!bottomLimit.get() == dropping);
			backDown();
		} else {
			if (state == 0 && shooting && !dropping) {
//				System.out.println("SHOOTING AT SCALE!");
				shooting = true;
				reachedBot = false;
				ascentStopTime = System.currentTimeMillis();
				scaleShot();
			} else if (state == 1 && shooting && !dropping) {
//				System.out.println("SHOOTING AT SWITCH!");
				shooting = true;
				reachedBot = false;
				ascentStopTime = System.currentTimeMillis();
				switchShot();
			}
		}
	}

	public boolean isShooting() {
		return thread;
	}

	public void checkLimits() {
//		System.out.println("TOP: " + !topLimit.get());
//		System.out.println("\t BOT: " + !bottomLimit.get());
		if (!topLimit.get() != prevStateTop && !topLimit.get()) {
			System.out.println("TOP: " + !topLimit.get());
//			System.out.println("STOP ME");
		} else if (!bottomLimit.get() != prevStateBot && !bottomLimit.get()) {
			System.out.println("BOT: " + !bottomLimit.get());
//			System.out.println(".");
		}
		prevStateTop = !topLimit.get();
		prevStateBot = !bottomLimit.get();
	}

	public void beginDescent() {
		System.out.println("WAITING");
		if (System.currentTimeMillis() >= ascentStopTime + 1000) {
			descend.start();
		}
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
		 lFar.set(0);
		lNear.set(0);
		rFar.set(0);
		 rNear.set(0);
	}

	public void backDown() {
		// zeroShooterEncoder();
//		 lFar.set(-0.25);
		lNear.set(-0.4);
		rFar.set(-0.4);
//		 rNear.set(-0.25);
	}

	public void scaleShot() {
		 lFar.set(0.5);
		lNear.set(0.5);
		rFar.set(0.5);
		 rNear.set(0.5);
	}

	public void switchShot() {
		 lFar.set(1);
		lNear.set(1);
		rFar.set(1);
		 rNear.set(1);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
