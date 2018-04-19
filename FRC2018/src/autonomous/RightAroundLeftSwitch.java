package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightAroundLeftSwitch extends CommandGroup {

    public RightAroundLeftSwitch() {
    	addParallel(new ResetShooter());
    	addSequential(new DriveStraightToCommand(195, 0.8, 8));
    	addSequential(new TimedTurnByCommand(1, -90));
//    	addSequential(new DriveStraightToCommand(300, 0.8, 6));
//    	addSequential(new TimedTurnByCommand(1, -90));
//    	addSequential(new DriveStraightToCommand(40, 0.4, 2));
//    	addSequential(new TimedTurnByCommand(1, -90));
//    	addSequential(new DriveStraightToCommand(20, 0.4, 2));
//    	addSequential(new AutoSwitch());
//    	addSequential(new ResetSwitch());
    	
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
