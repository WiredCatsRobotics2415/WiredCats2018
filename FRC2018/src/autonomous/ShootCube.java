package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootCube extends Command {

    public ShootCube() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.groundIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.groundIntake.testUptake(0.69);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.groundIntake.emptyPrism();
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
