package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSwitch extends Command {
	private final double TIME_LIMIT = 3000;
	private double time;

    public AutoSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.beast);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	Robot.beast.switchShot();
    	Robot.beast.scaleShot();
    	time = System.currentTimeMillis();
//    	Robot.beast.testMotor(0.4);
//    	System.out.println("SHOOT NOW OMG");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if (!Robot.beast.reachTop()) {
//			 Robot.beast.switchShot();
//			 System.out.println("SWITCHING");
			 
//		 } else if (Robot.beast.hitBottom()) {
//			 Robot.beast.eStop();
//		 } else if (Robot.beast.reachTop()) {
//			 Robot.beast.eStop();
//		 }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	System.out.println(Robot.beast.reachTop());
        return Robot.beast.reachTop() || System.currentTimeMillis() - time > TIME_LIMIT;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.beast.eStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.beast.eStop();
    }
}
