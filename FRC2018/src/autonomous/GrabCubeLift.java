package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GrabCubeLift extends Command {

    public GrabCubeLift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.groundIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.groundIntake.hasPrism()) {
    		Robot.groundIntake.testUptake(0.65);
    	} else {
    		Robot.groundIntake.grabPrism();
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.groundIntake.testUptake(0);
    	Robot.groundIntake.stopGrab();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.groundIntake.testUptake(0);
    	Robot.groundIntake.stopGrab();
    }
}
