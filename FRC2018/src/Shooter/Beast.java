package Shooter;

import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

import Projectile.*;

/**
 *
 */
public class Beast extends Subsystem {
	public static final double GEAR_DISTANCE = 0.05; //how far the belt travels in m for 1 motor rotation
	//public final byte SCALE = 0,
					  //SWITCH = 1;
	public static final double KP = 0.0, KI = 0.0, KD = 0.0, KF = 0.0;
	public static final int K_TIMEOUT_MS = 10, K_PID_LOOP_INDX = 0;
	
	public boolean shooting;
	
	private WPI_TalonSRX lFar, lNear, rFar, rNear;
	private DigitalInput limit; //ir_Sensor;
	//private Thread checker, shooter;
	
	public Beast() {
		lFar = new WPI_TalonSRX(RobotMap.LEFT_FAR_SHOOTER);
		lNear = new WPI_TalonSRX(RobotMap.LEFT_NEAR_SHOOTER);
		rFar = new WPI_TalonSRX(RobotMap.RIGHT_FAR_SHOOTER);
		rNear = new WPI_TalonSRX(RobotMap.RIGHT_NEAR_SHOOTER);
		
		limit = new DigitalInput(RobotMap.LIMIT_SWITCH);
		//ir_Sensor = new DigitalInput(RobotMap.IR_PORT);
		
		this.shooting = false;
		
		//PID config
		
		rFar.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, K_TIMEOUT_MS);
		
		rFar.setSensorPhase(true);
		
		rFar.configNominalOutputForward(0, K_TIMEOUT_MS); 
		rFar.configNominalOutputReverse(0, K_TIMEOUT_MS); 
		rFar.configPeakOutputForward(1, K_TIMEOUT_MS); 
		rFar.configPeakOutputReverse(-1, K_TIMEOUT_MS);
		
		rFar.config_kF(K_PID_LOOP_INDX, KF, K_TIMEOUT_MS);
		rFar.config_kP(K_PID_LOOP_INDX, KP, K_TIMEOUT_MS);
		rFar.config_kI(K_PID_LOOP_INDX, KI, K_TIMEOUT_MS);
		rFar.config_kD(K_PID_LOOP_INDX, KD, K_TIMEOUT_MS);
		/*checker = new Thread(new Runnable(){
			public void run() {
				System.out.println("CHECKING");
				if(limit.get()) {
					System.out.println("FOUND");
					shooter.interrupt();
				}
			}
		});
		
		shooter = new Thread(new Runnable(){
			public void run() {
				if (shooter.interrupted()) {
					System.out.println("STOPPING!");
					stopShooter();
				}
			}
		});*/
		
	}
	
	public void run() {
		if(shooting) {
			if(limit.get()) {
				stopShooter();
				shooting = false;
			}
		} /*else {
			if(!IR_Detector.get()) {
				backUp();
			} else {
				stopShooter();
			}
		}*/
	}
	
	public void shoot(double targetX, double targetY) {
		Finder finder = new Finder(targetX, targetY);
		double velocity = finder.run();
		shooting = true;
		setSpeed(velocity);
	}
	
	/*public void backUp() {
		lFar.set(-0.1);
		lNear.set(-0.1);
		rFar.set(-0.1);
		rNear.set(-0.1);
	}*/
	
	public void stopShooter() {
		lFar.set(0);
		lNear.set(0);
		rFar.set(0);
		rNear.set(0);
	}
	
	public void setSpeed(double velocity) { //speed in m/s
		double speed = (velocity/GEAR_DISTANCE)/10;
		rFar.set(ControlMode.Velocity, speed);
		rNear.set(ControlMode.Follower, RobotMap.RIGHT_FAR_SHOOTER);
		lFar.set(ControlMode.Follower, RobotMap.RIGHT_FAR_SHOOTER);
		lNear.set(ControlMode.Follower, RobotMap.RIGHT_FAR_SHOOTER);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

