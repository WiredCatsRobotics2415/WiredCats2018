package Subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Ramps extends Subsystem {
/*
//	public static DoubleSolenoid rampPushRight, rampPushLeft, rightLiftFar, rightLiftNear, leftLiftFar, leftLiftNear;
	public static DoubleSolenoid rampPush, leftLift;
	public static DoubleSolenoid test;

	public Ramps() {
		rampPush = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.RAMP_PUSH_FRONT, RobotMap.RAMP_PUSH_BACK);
//		rightLift = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.RAMP_RIGHT_FRONT, RobotMap.RAMP_RIGHT_BACK);
		leftLift= new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.RAMP_LEFT_FRONT, RobotMap.RAMP_LEFT_BACK);
//		test = new DoubleSolenoid(RobotMap.PCM_ID, 0, 7);
	}
	
	public void test(boolean go) {
		if (go) {
//			test.set(Value.kForward);
		} else {
//			test.set(Value.kReverse);
		}
	}

	public void rampsOut(boolean on) {
		if (on) {
			rampPush.set(Value.kForward);
		} else {
			rampPush.set(Value.kReverse);
		}
	}
	
	public void platformsDown(boolean down) {
		if (down) {
//			rightLift.set(Value.kReverse);
			leftLift.set(Value.kReverse);
		} else {
//			rightLift.set(Value.kForward);
			leftLift.set(Value.kForward);
		}
	}
	*/

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
