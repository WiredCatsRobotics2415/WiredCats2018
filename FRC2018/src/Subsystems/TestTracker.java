package Subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TestTracker extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private double[] pos = {0, 0}; //x and y
	private double[] lastTick = {0, 0}; //left and right
	private double deltaLeftEn, deltaRightEn; //change in left and right encoders
	private final double ROBOT_WIDTH = 26.0; //width of robot in inches, used for calculating arcs
	private double centralAngle; //central angle of projected arc
	private double lastAngle = 0; //previous angle to calculate vector
	public void updatePos(double[] thisTick, double angle) {
		//find change in left and right encoder values in inches
		this.deltaLeftEn = thisTick[0]-lastTick[0];
		this.deltaRightEn = thisTick[1]-lastTick[1];
		this.lastTick[0] = thisTick[0];
		this.lastTick[1] = thisTick[1];
		this.calcNet(this.calcCircle(), angle);
	}
	private double calcCircle() {
		double greaterArc, lesserArc; //a Halo reference only true Halo fans understand
		greaterArc = Math.max(deltaLeftEn, deltaRightEn); //determines which is greater and lesser
		lesserArc = Math.min(deltaLeftEn, deltaRightEn);
		/*
		 * Solving:
		 * if x = degrees in arc and r = arc of lesser radius
		 * x/360 * 2 * pi * r = lesserArc
		 * x/360 * 2 * pi * (r+robotWidth) = greaterArc
		 * 
		 */
		double lesserRadius = (lesserArc * ROBOT_WIDTH)/(greaterArc-lesserArc); //derived
		this.centralAngle = 180*(greaterArc-lesserArc)/(Math.PI*ROBOT_WIDTH); //derived
		double meanRadius = lesserRadius + (ROBOT_WIDTH/2); //distance to middle of robot
		/*
		 * Law of Cosines c^2 = a^2 + b^2 - 2ab*cos(theta)
		 * When a = b: (isosceles triangle)
		 * c^2 = a^2+a^2 - 2*a*a*cos(theta)
		 * c^2 = 2*a^2 - 2*a^2*cos(theta)
		 * c = √2*a^2 - (2*a^2*cos(theta))
		 */
		double netDistance = Math.sqrt((2*Math.pow(meanRadius, 2))-(2*Math.pow(meanRadius, 2)*Math.cos(centralAngle)));
		return netDistance; //returns netDistance for calcNet
	}
	private void calcNet(double netDistance, double thisAngle) {
		double vectorAngle = (180-this.centralAngle)/2; //angle of isosceles triangle (vector direction)
		if (this.deltaLeftEn < this.deltaRightEn) { //if turning left, consider the angle to be negative
			vectorAngle *= -1;
		}
		double netAngle = lastAngle + vectorAngle;
		this.pos[0] += Math.sin(Math.toRadians(netAngle))*netDistance; //x value
		this.pos[1] += Math.cos(Math.toRadians(netAngle))*netDistance; //y value
		this.lastAngle = thisAngle; //sets lastAngle to this
	}
	public double[] getPos() {
		return this.pos;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

