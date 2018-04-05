package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class SimpleDriveBackward extends TimedCommand {
	
	double speed;

    public SimpleDriveBackward(double timeout, double speed) {
        super(timeout);
        this.speed = speed;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arcadeDrive.setMotors(-speed, speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arcadeDrive.setMotors(-speed, speed);
    }

    // Called once after timeout
    protected void end() {
    	Robot.arcadeDrive.setMotors(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
