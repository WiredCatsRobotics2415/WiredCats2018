package org.usfirst.frc.team2415.robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {
	//set to true if robot is picking up cube, set to false if robot is pushing out a cube
	private boolean intake;

    public IntakeCommand(boolean intake) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intakeSubsystem);
    	this.intake = intake;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(intake) {
    		Robot.intakeSubsystem.setSolenoid(false);
    		Robot.intakeSubsystem.setTalon(1);
    	} else {
    		Robot.intakeSubsystem.setSolenoid(false);
    		Robot.intakeSubsystem.setTalon(-1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(intake) {
        	return Robot.intakeSubsystem.getIR() || Robot.gamepad.getYButton(); //YButton is kill switch
        } else {
        	return !Robot.gamepad.getBButton() || Robot.gamepad.getYButton();
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(intake) {
    		Robot.intakeSubsystem.setSolenoid(true);
    		Robot.intakeSubsystem.setTalon(0);
    	} else {
    		Robot.intakeSubsystem.setSolenoid(false);
    		Robot.intakeSubsystem.setTalon(0);
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intakeSubsystem.setSolenoid(false);
    	Robot.intakeSubsystem.setTalon(0);
    }
}
