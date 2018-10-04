package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ZeroEncoders extends InstantCommand {

    public ZeroEncoders() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super();
    	requires(Robot.arcadeDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arcadeDrive.zeroEncoders();
    	Robot.arcadeDrive.zeroYaw();
    }
}
