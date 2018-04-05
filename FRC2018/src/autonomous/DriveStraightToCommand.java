package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightToCommand extends Command implements PIDOutput {


	PIDController turnController;
	double rotateToAngleRate;
	double angle;
	
	double kP = 0.0487;
	double kI = 0;
	double kD = 0.0640;
	double kF = 0;
	
	double kTolerance = 2.0;
	
	long zeroWaitTime;
	
	long startTime;
	boolean finisher;
	
	double startPos;
	double distance;
	double speed;
	double time;
//	double setpoint;
	
	
	public DriveStraightToCommand(double distance, double speed, double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.arcadeDrive);
		this.distance = distance;
		this.speed = speed;
		this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {	
    	startTime = System.currentTimeMillis()/1000;
//    	System.out.println("start TIME: " + startTime);
//    	Robot.arcadeDrive.set(ControlMode.PercentOutput);
//    	Robot.arcadeDrive.zeroEncoders();
//    	Robot.arcadeDrive.zeroYaw();
//    	startPos = (Math.abs(Robot.arcadeDrive.getDistance()[1]) + Math.abs(Robot.arcadeDrive.getDistance()[0]))/2;
    	Robot.arcadeDrive.setMotors(0, 0);
//    	turnController = new PIDController(kP, kI, kD, kF, Robot.arcadeDrive.ahrs, this);
//    	turnController.setInputRange(-180.0f,  180.0f);
//    	turnController.setOutputRange(-1.0, 1.0);
//    	turnController.setAbsoluteTolerance(kTolerance);
//    	turnController.setContinuous(true);
//    	turnController.setSetpoint(Robot.arcadeDrive.ahrs.getYaw());
//    	turnController.setSetpoint(0);
//    	int count = 0;
//    	for(int i = 0; i < 30; i++){	
//    		if(turnController.getError() > .5 && count < 30){
//	        	turnController.setSetpoint(Robot.driveSubsystem.ahrs.getYaw());
//	        	count++;
//    		}
//    	}
//    	Robot.arcadeDrive.zeroEncoders();
//    	turnController.enable();
    	Robot.arcadeDrive.setBrakeMode(true);
//    	Robot.arcadeDrive.zeroEncoders();
    	
//    	Robot.driveSubsystem.setLeftRampRate(27.4285714286/2);
//    	Robot.driveSubsystem.setRightRampRate(27.4285714286/2);
    	startPos = (Math.abs(Robot.arcadeDrive.getDistance()[0]));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	System.out.println("Yaw: " + Robot.driveSubsystem.ahrs.getYaw() + "/t TurnSetpoint: " + turnController.getSetpoint());
//    	System.out.println("Left: " + Robot.arcadeDrive.getDistance()[0] + "\tRight: " + Robot.arcadeDrive.getDistance()[1]);
//    	System.out.println("AVG Dist: " + Math.abs(Robot.arcadeDrive.getDistance()[0]) + Math.abs(Robot.arcadeDrive.getDistance()[1])/2 + "\t DriveSetpoint: " + distance);
//    	if(distance > 0) Robot.arcadeDrive.setMotors(-rotateToAngleRate + speed, -rotateToAngleRate + speed);
//    	else Robot.arcadeDrive.setMotors(-(rotateToAngleRate + speed), -(rotateToAngleRate + speed));
//    	System.out.println(System.currentTimeMillis()/1000 - startTime);
    	
    	if(distance > 0) {
    		Robot.arcadeDrive.setMotors(-speed, speed);
//    		System.out.println(speed);
    	}
    	else Robot.arcadeDrive.setMotors(speed, -speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return System.currentTimeMillis()/1000 - startTime > time ||
    			(Math.abs(distance) + startPos) <= (Math.abs(Robot.arcadeDrive.getDistance()[0]));
    }

    // Called once after isFinished returns true
    protected void end() {
//    	Robot.driveSubsystem.setLeftRampRate(0);
//    	Robot.driveSubsystem.setRightRampRate(0);
    	Robot.arcadeDrive.setMotors(0, 0);
//    	Robot.arcadeDrive.zeroEncoders();
//    	turnController.reset();
//    	System.out.println("ENDING");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
//    	Robot.driveSubsystem.setLeftRampRate(0);
//    	Robot.driveSubsystem.setRightRampRate(0);
    	Robot.arcadeDrive.setMotors(0, 0);
//    	Robot.arcadeDrive.zeroEncoders();
//    	turnController.reset();
    }

	@Override
	public void pidWrite(double output) {
		rotateToAngleRate = output;
	}
}
