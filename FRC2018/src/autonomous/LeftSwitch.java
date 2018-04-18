package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSwitch extends CommandGroup {

    public LeftSwitch() {
    	
    	addParallel(new ResetShooter());
    	addParallel(new KeepIntakeUp());
    	addSequential(new ForwardGo(0.6, 30));
    	addParallel(new KeepIntakeUp());
    	addSequential(new TimedTurnByCommand(0.5, -49.5));
    	addParallel(new KeepIntakeUp());
    	addSequential(new ForwardGo(0.6, 63));
    	addParallel(new KeepIntakeUp());
    	addSequential(new TimedTurnByCommand(0.5, 49.5));
    	addParallel(new KeepIntakeUp());
    	addSequential(new DriveStraightToCommand(22, 0.45, 2)); //27
    	addParallel(new KeepIntakeUp());
    	addSequential(new AutoSwitch());
    	//BREAK
    	addParallel(new ResetShooter());
    	addParallel(new KeepIntakeUp());
    	addSequential(new SimpleDriveBackward(0.7, 0.5));
    	addParallel(new KeepIntakeUp());
    	addSequential(new TimedTurnByCommand(0.5, 49.5));
    	addParallel(new KeepIntakeUp());
    	addSequential(new SimpleDriveBackward(2, 0.6));
    	addParallel(new KeepIntakeUp());
//    	addSequential(new DriveStraightToCommand(45, 0.5, 3));
    	addSequential(new TimedTurnByCommand(0.5, -49.5));
    	addParallel(new GrabCubeLift());
    	addSequential(new DriveStraightToCommand(20, 0.3, 2));
    	addParallel(new GrabCubeLift());
    	addSequential(new TimedTurnByCommand(0.5, 49.5));
    	addParallel(new GrabCubeLift());
    	addSequential(new ForwardGo(0.6, 65));
    	addParallel(new GrabCubeLift());
    	addSequential(new TimedTurnByCommand(0.5, -49.5));
    	addParallel(new GrabCubeLift());
    	addSequential(new DriveStraightToCommand(22, 0.45, 2)); //27
    	addSequential(new ShootCube());
    	
    	
//    	addSequential(new ForwardGo(-0.4, 10));
//    	addSequential(new TimedTurnByCommand(0.5, -90));
    	
    	
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
