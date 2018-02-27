package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ForwardGo extends Command {

	double speed = 0;
	double distance;
	
    public ForwardGo(double speed, double distance) {
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
    	System.out.println("GOING");
    	if (Robot.arcadeDrive.getDistance()[0] > distance) {
    		Robot.arcadeDrive.setMotors(0, 0);
    	} else {
//    		Robot.arcadeDrive.setMotors(-0.5, 0.5);
    		Robot.arcadeDrive.setMotors(-speed, speed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.arcadeDrive.getDistance()[0] > distance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arcadeDrive.setMotors(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.arcadeDrive.setMotors(0, 0);
    }
}
