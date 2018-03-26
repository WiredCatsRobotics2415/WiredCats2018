package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class StopIntake extends InstantCommand {

    public StopIntake() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.intake);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.intake.stopGrab();
    	
    }

}
