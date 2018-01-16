package org.usfirst.frc.team2415.robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorCommand extends Command {
	//goal height in inches
	private double goal;
	//true if the goal is above the current location, false if the goal is below the current location
	private boolean above;

    public ElevatorCommand(byte state) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevatorSubsytem);
    	switch(state) {
    		case 4:
    			goal = 0;
    			break;
    		case 0:
    			goal = 24;
    			break;
    		case 1:
    			goal = 48;
    			break;
    		case 2:
    			goal = 72;
    			break;
    		case 3:
    			goal = 96;
    			break;
    		default:
    			goal = 0;
    			break;
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(Robot.elevatorSubsytem.getDistance() > goal) {
    		above = false;
    	} else {
    		above = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(above) {
    		Robot.elevatorSubsytem.setTalon(1);
    	} else {
    		Robot.elevatorSubsytem.setTalon(-1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.gamepad.getYButton()) {
    		return true;
    	} if(above) {
        	if(Robot.elevatorSubsytem.getDistance() >= goal) {
        		return true;
        	} else {
        		return false;
        	}
        } else {
        	if(Robot.elevatorSubsytem.getDistance() <= goal) {
        		return true;
        	} else {
        		return false;
        	}
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevatorSubsytem.setTalon(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevatorSubsytem.setTalon(0);
    }
}
