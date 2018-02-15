package Projectile;

public class Projectile {
	public static final double AREA = 0.09; //projected area in m^2
	public static final double AIR_DENSITY = 1.225; //air density in kg/m^3
	public static final double GRAVITY = -9.81;//gravity in m/s^2
	public static final double PERIOD = .01;//period of each step in seconds
	public static final double MASS = 1.5;//mass in kg
	public static final double DRAG_CO = .8;//Drag Coefficent
	
	private double angle, theta; //launch angle in radians
	private double targetY, targetX;//target location in x and y directions in meters
	private double xVelocity, yVelocity;//velocity in x and y directions in m/s
	private double x,y;//locations of projectile in meters
	
	public Projectile(double launchAngle, double initialVelocity, double targetY, double targetX) {
		this.angle = launchAngle;
		this.theta = launchAngle;
		this.targetX = targetX;
		this.targetY = targetY;
		this.xVelocity = Math.cos(this.angle)*initialVelocity;
		this.yVelocity = Math.sin(this.angle)*initialVelocity;
		this.x = 0;
		this.y = 0;
	}
	
	public void step() { //runs one step of hte projectile
		double dragForce = 0;
		double dVelocity = 0;
		this.x += this.xVelocity*PERIOD; //change x and y locations by the velocity
		this.y += this.yVelocity*PERIOD;
		this.angle = Math.atan(this.yVelocity/this.xVelocity); //direction of movement
		dragForce = DRAG_CO*AIR_DENSITY*(Math.pow(this.xVelocity,2)+Math.pow(this.yVelocity, 2))*AREA*0.5;
		//finds dragForce on the projectile
		dVelocity = dragForce/MASS*PERIOD;  //get the change in velocity due to drag
		this.xVelocity -= Math.cos(this.angle)*dVelocity; //subtracts the change in velocity due to drag
		this.yVelocity -= Math.sin(this.angle)*dVelocity;
		this.yVelocity += GRAVITY*PERIOD; //apply gravity
	}
	
	public double run(double initialVelocity) {
		this.x = 0;
		this.y = 0;
		setVelocity(initialVelocity); //set velocity to the given velocity
		while(this.y >= this.targetY || this.yVelocity > 0) {
			step(); //run until the projectile is below the target height and falling
		}
		return this.x - this.targetX; //return the differnce between the actual x distance minus the target x distance
	}
	
	public void setVelocity(double velocity) { //sets the x and y velocity
		this.xVelocity = Math.cos(this.theta)*velocity;
		this.yVelocity = Math.sin(this.theta)*velocity;
	}
	
	public void setTarget(double targetX,double targetY) { //set the targets
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	public void setLaunchAngle(double angle) { //set the launch angle
		this.theta = angle;
		this.angle = angle;
	}
}