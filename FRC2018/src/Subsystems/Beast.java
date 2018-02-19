package Subsystems;

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
	
	public final byte SCALE = 0,
					  SWITCH = 1;
	
	public byte shootAt;
	
	private WPI_TalonSRX lFar, lNear, rFar, rNear;
	private DigitalInput topLimit, bottomLimit;
	private Thread checker, shooter, descend;
	
	long ascentStopTime;
	double kHeight = 4096 * 6;
	
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
		
		checker = new Thread(new Runnable(){
			public void run() {
				System.out.println("CHECKING");
				if(!topLimit.get() /* || getHeight() - kHeight <= 50 */) {
					System.out.println("REACHED TOP");
					shooter.interrupt();
					ascentStopTime = System.currentTimeMillis();
					System.out.println("ENCODER AT: " + getHeight());
					System.out.println("STOP TIME: " + ascentStopTime);
				}
				if(!bottomLimit.get()) {
					System.out.println("REACHED BOTTOM");
					descend.interrupt();
				}
			}
		});
		
		shooter = new Thread(new Runnable(){
			public void run() {
				if (shooter.interrupted()) {
					System.out.println("STOPPING!");
					stopShooter();
					beginDescent();
					return;
				} else if (shootAt == SCALE) {
					System.out.println("SHOOTING AT SCALE!");
					scaleShot();
				} else if (shootAt == SWITCH) {
					System.out.println("SHOOTING AT SWITCH!");
					switchShot();
				}
			}
		});
		
		descend = new Thread(new Runnable(){
			public void run() {
				if (descend.interrupted()) {
					System.out.println("STOPPED GOING DOWN");
					stopShooter();
					zeroShooterEncoder();
					return;
				} else {
					backDown();
				}
			}
		});
		
	}
	
	public void testMotor(double speed) {
//		lFar.set(speed);
		lNear.set(speed);
		rFar.set(speed);
//		rNear.set(speed);
	}
	
	public void checkLimits() {
		System.out.println("TOP: " + !topLimit.get());
	}
	
	public void beginDescent() {
		if(System.currentTimeMillis() >= ascentStopTime + 1000) {
			descend.start();
		}
	}
	
	public double getHeight() {
		return (double)lNear.getSelectedSensorPosition(0);
	}
	
	public void zeroShooterEncoder(){
    	lNear.setSelectedSensorPosition(0, 0, 10);
    }
	
	public void shoot(byte state) {
		shootAt = state;
		checker.start();
		shooter.start();
	}
	
	public void stopShooter() {
//		lFar.set(0);
		lNear.set(0);
		rFar.set(0);
//		rNear.set(0);
	}
	
	public void backDown() {
		zeroShooterEncoder();
//		lFar.set(-0.5);
		lNear.set(-0.45);
		rFar.set(-0.45);
//		rNear.set(-0.5);
	}
	
	public void scaleShot() {
//		lFar.set(0.25);
		lNear.set(0.6);
		rFar.set(0.6);
//		rNear.set(0.25);
	}
	
	public void switchShot() {
//		lFar.set(0.5);
		lNear.set(0.45);
		rFar.set(0.45);
//		rNear.set(0.5);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

