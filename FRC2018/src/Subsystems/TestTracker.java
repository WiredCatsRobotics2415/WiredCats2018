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
	//double lastTheta, deltaTheta;
	public void updatePos(double[] thisTick, double angle) {
		//find change in left and right encoder values in inches
		this.deltaLeftEn = thisTick[0]-lastTick[0];
		this.deltaRightEn = thisTick[1]-lastTick[1];
		this.lastTick[0] = thisTick[0];
		this.lastTick[1] = thisTick[1];
		double meanRadius = this.calcCircle();
	}
	private double calcCircle() {
		double greaterArc, lesserArc; //lol that Halo reference only true Halo fans understand
		greaterArc = Math.max(deltaLeftEn, deltaRightEn);
		lesserArc = Math.min(deltaLeftEn, deltaRightEn);
		/*
		 * Solving:
		 * if x = degrees in arc and r = arc of lesser radius
		 * x/360 * 2 * pi * r = lesserArc
		 * x/360 * 2 * pi * (r+robotWidth) = greaterArc
		 * 
		 */
		double lesserRadius = (lesserArc * ROBOT_WIDTH)/(greaterArc-lesserArc);
		double centralAngle = 180*(greaterArc-lesserArc)/(Math.PI*ROBOT_WIDTH);
		double meanRadius = lesserRadius + (ROBOT_WIDTH/2);
		double netDistance = Math.sqrt((2*Math.pow(meanRadius, 2))-(2*meanRadius*Math.cos(centralAngle)));
		return 0.0;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

