package autonomous;

import org.usfirst.frc.team2415.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class AutoIntakeSet extends InstantCommand {
	
	boolean autoSuck, autoShoot;

    public AutoIntakeSet(boolean autoSuck, boolean autoShoot) {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.groundIntake);
        this.autoShoot = autoShoot;
        this.autoSuck = autoSuck;
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.groundIntake.setShootSuck(autoShoot, autoSuck);
    }

}
