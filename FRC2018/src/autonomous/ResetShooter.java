package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetShooter extends Command {

    public ResetShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis)
    	requires(Robot.beast);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.beast.eStop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.beast.backDown();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.beast.hitBottom();
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
