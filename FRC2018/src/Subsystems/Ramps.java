package Subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Ramps extends Subsystem {
/*
	public static DoubleSolenoid sideOne, sideTwo, sideThree, sideFour, sideFive, sideSix;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Ramps() {
		sideOne = new DoubleSolenoid(0, 1);
		sideTwo = new DoubleSolenoid(2, 3);
		sideThree = new DoubleSolenoid(4, 5);
		sideFour = new DoubleSolenoid(6, 7);
		sideFive= new DoubleSolenoid(8, 9);
		sideSix = new DoubleSolenoid(10, 11);
	}

	public void rampsOut(boolean on) {
		if (on) {
			sideOne.set(Value.kForward);
			sideTwo.set(Value.kForward);
			sideThree.set(Value.kForward);
			sideFour.set(Value.kForward);
			sideFive.set(Value.kForward);
			sideSix.set(Value.kForward);
		} else {
			sideOne.set(Value.kReverse);
			sideTwo.set(Value.kReverse);
			sideThree.set(Value.kReverse);
			sideFour.set(Value.kReverse);
			sideFive.set(Value.kReverse);
			sideSix.set(Value.kReverse);
		}
	}
*/
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
