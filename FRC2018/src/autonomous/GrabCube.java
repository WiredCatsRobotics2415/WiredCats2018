package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class GrabCube extends TimedCommand {

	public GrabCube(double timeout) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super(timeout);
		requires(Robot.groundIntake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (!Robot.groundIntake.hitBottom()) {
			Robot.groundIntake.autoCube();
		} else { 
			Robot.groundIntake.sideRoller(1.5);
		}

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!Robot.groundIntake.hitBottom()) {
			Robot.groundIntake.autoCube();
		} else { 
			Robot.groundIntake.sideRoller(1.5);
		}

	}

	// Called once after isFinished returns true
	protected void end() {
		// Robot.groundIntake.testUptake(0);
		Robot.groundIntake.stopGrab();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		// Robot.groundIntake.testUptake(0);
		Robot.groundIntake.stopGrab();
	}
}
