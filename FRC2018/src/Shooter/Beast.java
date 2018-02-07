package Shooter;

import org.usfirst.frc.team2415.robot.RobotMap;

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
	private DigitalInput limit;
	private Thread checker, shooter;
	
	public Beast() {
		lFar = new WPI_TalonSRX(RobotMap.LEFT_FAR_SHOOTER);
		lNear = new WPI_TalonSRX(RobotMap.LEFT_NEAR_SHOOTER);
		rFar = new WPI_TalonSRX(RobotMap.RIGHT_FAR_SHOOTER);
		rNear = new WPI_TalonSRX(RobotMap.RIGHT_NEAR_SHOOTER);
		
		limit = new DigitalInput(RobotMap.LIMIT_SWITCH);
		
		checker = new Thread(new Runnable(){
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
				} else if (shootAt == SCALE) {
					System.out.println("SHOOTING AT SCALE!");
					scaleShot();
				} else if (shootAt == SWITCH) {
					System.out.println("SHOOTING AT SWITCH!");
					switchShot();
				}
			}
		});
		
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
        //setDefaultCommand(new MySpecialCommand());
    }
}

