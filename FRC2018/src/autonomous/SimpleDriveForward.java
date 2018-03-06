package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class SimpleDriveForward extends TimedCommand {
	
	double speed = 0;
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
//    	System.out.println("GOING");
    	if (Robot.arcadeDrive.getDistance()[0] > distance) {
    		Robot.arcadeDrive.setMotors(0, 0);
    	} else {
//    		Robot.arcadeDrive.setMotors(-0.5, 0.5);
    		Robot.arcadeDrive.setMotors(-speed, speed);
    	}
    }

    // Called once after timeout
    protected void end() {
//    	System.out.println("END");
    	Robot.arcadeDrive.setMotors(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.arcadeDrive.setMotors(0, 0);
    }
}
