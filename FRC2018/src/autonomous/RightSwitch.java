package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightSwitch extends CommandGroup {

    public RightSwitch() {
    	
    	addSequential(new ForwardGo(0.4, 30));
    	addParallel(new ResetShooter());
    	addSequential(new TimedTurnByCommand(0.5, 45));
    	addSequential(new ForwardGo(0.4, 67.5));
    	addSequential(new TimedTurnByCommand(0.5, -45));
    	addSequential(new DriveStraightToCommand(30, 0.4, 3));
    	addSequential(new AutoSwitch());
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
