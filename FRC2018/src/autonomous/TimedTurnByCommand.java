package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class TimedTurnByCommand extends TimedCommand implements PIDOutput {

	PIDController turnController;
	double rotateToAngleRate;
	double angle;
	boolean finisher, checked = false;
	long finisherTime, startTime;
	
	double kP = 0.0487;
	double kI = 0.0000;
	double kD = 0.0340;
	double kF = 0;
	
	double kTolerance = 1;
	
	long zeroWaitTime;
	
    public TimedTurnByCommand(double timeout, double angle) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.arcadeDrive);
        
		this.angle = angle;
		startTime = System.currentTimeMillis();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arcadeDrive.zeroYaw();
//    	Robot.arcadeDrive.changeControlMode(TalonControlMode.PercentVbus);
    	
    	Robot.arcadeDrive.setMotors(0, 0);
    	Robot.arcadeDrive.setBrakeMode(true);
//    	Robot.arcadeDrive.slaveRight(true);
    	
    	turnController = new PIDController(kP, kI, kD, kF, Robot.arcadeDrive.ahrs, this);
    	turnController.setInputRange(-180.0f,  180.0f);
    	turnController.setOutputRange(-1.0, 1.0);
    	turnController.setAbsoluteTolerance(kTolerance);
    	turnController.setContinuous(true);
    	turnController.enable();
    	turnController.setSetpoint(angle);
    	
//    	System.out.println("SET: " + turnController.getSetpoint());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	System.out.println(Robot.arcadeDrive.getYaw());
    	Robot.arcadeDrive.setMotors(-rotateToAngleRate, -rotateToAngleRate);
    	if (turnController.onTarget()) Robot.arcadeDrive.setMotors(0, 0);
    }

    // Called once after timeout
    protected void end() {
//    	System.out.println("DONE");
    	Robot.arcadeDrive.setMotors(0, 0);
    	turnController.reset();
//    	Robot.arcadeDrive.slaveRight(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
//    	System.out.println("DONE");
    	Robot.arcadeDrive.setMotors(0, 0);
    	turnController.reset();
//    	Robot.arcadeDrive.slaveRight(false);
    }
    

	public void pidWrite(double output) {
		rotateToAngleRate = output;
	}
}
