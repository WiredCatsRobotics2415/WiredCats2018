package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class SimpleDriveForward extends TimedCommand {
	
	double speed;
	double distance;

    public SimpleDriveForward(double timeout, double speed, double distance) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.arcadeDrive);
        this.speed = speed;
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arcadeDrive.zeroEncoders();
    	Robot.arcadeDrive.setMotors(-speed, -speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.arcadeDrive.getDistance()[0] > distance) {
    		Robot.arcadeDrive.setMotors(0, 0);
    	}
    }

    // Called once after timeout
    protected void end() {
    	Robot.arcadeDrive.setMotors(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.arcadeDrive.setMotors(0, 0);
    }
}
