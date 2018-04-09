package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightSwitch extends CommandGroup {

    public RightSwitch() {
    	
    	addParallel(new ResetShooter());
    	addSequential(new ForwardGo(0.6, 30));
    	addSequential(new TimedTurnByCommand(0.5, 49.5));
    	addSequential(new ForwardGo(0.6, 65));
    	addSequential(new TimedTurnByCommand(0.5, -49.5));
    	addSequential(new DriveStraightToCommand(27, 0.45, 2));
    	addSequential(new AutoSwitch());
    	//BREAK
//    	addSequential(new ResetShooter());
//    	addSequential(new SimpleDriveBackward(1, 0.3));
//    	addSequential(new TimedTurnByCommand(0.5, 90));
//    	addSequential(new DriveStraightToCommand(45, 0.5, 3));
//    	addSequential(new TimedTurnByCommand(0.5, -90));
//    	addSequential(new DriveStraightToCommand(50, 0.5, 3));
    	
    	
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
