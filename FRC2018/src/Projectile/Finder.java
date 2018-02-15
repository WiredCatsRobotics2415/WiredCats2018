package Projectile;

public class Finder {
	public final double ACCURACY = .01; //Accuracy of the x distance in meters
	public final double LAUNCH_ANGLE = (80/180.0)*Math.PI; //Launch angle in radians
	public final double VELOCITY_MULTIPLIER = 3.0; //multiplier used to find the starting velocity by taking the velocity without drag times the multiplier
	public final double MAX_VELOCITY = 999; //max velocity for the shooter in m/s
	public final int LOOP_LIMIT = 100; //limit for repeat of the loop
	
	private double min,max; //min and max velocities in m/s
	private double velocity; //current velocity being calculated in m/s
	private Projectile cube; //cube object
	
	public Finder(double targetX, double targetY) {
		this.min = Math.sqrt((4.9*Math.pow(targetX,2))/(Math.cos(LAUNCH_ANGLE)*((Math.sin(LAUNCH_ANGLE)*targetX)-(Math.cos(LAUNCH_ANGLE)*targetY))));
		//sets min velocity as velocity wihtout air resistance
		this.velocity = this.min*VELOCITY_MULTIPLIER; //staring velocity is min velocity times velocity multiplier
		this.max = MAX_VELOCITY; //set max velocity to max velocity constant
		this.cube = new Projectile(LAUNCH_ANGLE,this.velocity,targetX,targetY); //creates cube object
	}
	
	public void reset(double targetX, double targetY) {
		this.cube.setTarget(targetX, targetY); //set new cube target
	}
	
	public double run() { //runs the calculations
		double difference = 0.0; //curret difference between the acutal x distance and target x distance
		double lastDifference = 0.0; //last difference between the acutal x distance and target x distance
		int counter = 0; 
		while(counter < LOOP_LIMIT) { //limits loop repeat
			lastDifference = difference; 
			difference = this.cube.run(this.velocity); //set difference to difference for the current velocity
			if(Math.abs(difference) < ACCURACY) { //if the difference is less then 
				break;
			} else if(Math.abs(difference-lastDifference) < ACCURACY/2) {
				break;
			} else if(difference > 0) { //if it is overshooting
				this.max = this.velocity;
				this.velocity = (this.min+this.velocity)/2;
			} else { //if it is undershooting
				this.min = this.velocity;
				this.velocity = (this.max+this.velocity)/2;
			}
			counter++;
		}
		return this.velocity;
	}
}
