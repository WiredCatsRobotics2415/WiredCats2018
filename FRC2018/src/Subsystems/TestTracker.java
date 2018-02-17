package Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TestTracker extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	double[] pos = {0, 0}; //x and y
	double[] lastTick = {0, 0}; //left and right
	double deltaLeftEn, deltaRightEn;
	final double ROBOT_WIDTH = 26.0; //inches
	double deltaTheta;
	public void updatePos(double[] thisTick, double angle) {
		//find change in left and right encoder values in inches
		this.deltaLeftEn = thisTick[0]-lastTick[0];
		this.deltaRightEn = thisTick[1]-lastTick[1];
		this.lastTick[0] = thisTick[0];
		this.lastTick[1] = thisTick[1];
		
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

