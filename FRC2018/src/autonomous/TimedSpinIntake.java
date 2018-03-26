package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class TimedSpinIntake extends TimedCommand {

    public TimedSpinIntake(double timeout) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intake.simpleIntake();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.simpleIntake();
    }

    // Called once after timeout
    protected void end() {
    	Robot.intake.stopGrab();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stopGrab();
    }
}
