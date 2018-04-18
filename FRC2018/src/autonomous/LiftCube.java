package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class LiftCube extends TimedCommand {

    public LiftCube(double timeout) {
        super(timeout);
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
    	Robot.groundIntake.testUptake(0.69);
    }

    // Called once after timeout
    protected void end() {
    	Robot.groundIntake.testUptake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.groundIntake.testUptake(0);
    }
}
