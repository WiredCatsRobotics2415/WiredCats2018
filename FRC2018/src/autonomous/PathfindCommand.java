package autonomous;

import java.util.LinkedList;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import utilities.FalconPathPlanner;

/**
 *
 */
public class PathfindCommand extends Command {

	double[][] waypoints;
	double totalTime = 5; // max seconds we want to drive the path
	double timeStep = 0.01; // period of control loop on Rio, seconds
	double robotTrackWidth = 2.165; // distance between left and right wheels,
									// feet
	FalconPathPlanner path;
	LinkedList<Double> left = new LinkedList<Double>();
	LinkedList<Double> right = new LinkedList<Double>();

	public PathfindCommand(double[][] path) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		// requires(Robot.velocityDrive);
		requires(Robot.arcadeDrive);
		waypoints = path;
	}

	// Called just before this Command runs the first time
	protected void initialize() {

		path = new FalconPathPlanner(waypoints);
		path.calculate(totalTime, timeStep, robotTrackWidth);
		for (double step[] : path.smoothLeftVelocity) {
			// left.add(Robot.velocityDrive.fPS2RPM(step[1]));
			left.add(Robot.arcadeDrive.fPS2RPM(step[1]));
		}
		for (double step[] : path.smoothRightVelocity) {
			// right.add(Robot.velocityDrive.fPS2RPM(step[1]));
			right.add(Robot.arcadeDrive.fPS2RPM(step[1]));
		}

		// Robot.velocityDrive.changeControlMode(TalonControlMode.Speed);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// Robot.velocityDrive.setVelocity(left.removeFirst(),
		// right.removeFirst());
		try {
			Robot.arcadeDrive.setVelocity(left.removeFirst(), right.removeFirst());
			System.out.println("L: " + left.removeFirst() + "\tR: " + right.removeFirst());
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error", true);
		}

	}

	private double fPS2RPM(Double removeFirst) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return left.size() == 0 && right.size() == 0;
	}

	// Called once after isFinished returns true
	protected void end() {
		// Robot.velocityDrive.setMotors(0, 0);
		Robot.arcadeDrive.setMotors(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		// Robot.velocityDrive.setMotors(0, 0);
		Robot.arcadeDrive.setMotors(0, 0);
	}
}