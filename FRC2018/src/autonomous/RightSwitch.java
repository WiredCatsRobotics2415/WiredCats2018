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
    	addSequential(new DriveStraightToCommand(22, 0.45, 1)); //27
    	addSequential(new AutoSwitch());
    	//BREAK
    	
    	addParallel(new ResetShooter());
    	addParallel(new ZeroEncoders());
//    	addSequential(new ResetShooter());
    	addSequential(new SimpleDriveBackward(1, 0.45, 24));
    	addSequential(new TimedTurnByCommand(0.5, 49.5));
    	addSequential(new SimpleDriveBackward(5, 0.6, 57.5)); //63
    	addSequential(new TimedTurnByCommand(0.5, -39.5)); //-49.5
    	addSequential(new AutoIntakeSet(true, false));
    	addSequential(new SimpleDriveBackward(1, 0.45, 3));
    	addSequential(new DriveStraightToCommand(13, 0.23, 2));
    	addParallel(new LiftCube(0.5));
    	addSequential(new SimpleDriveBackward(2, 0.3, 13));
    	addSequential(new TimedTurnByCommand(0.5, 49.5));
//    	addParallel(new ZeroEncoders());
    	addSequential(new DriveStraightToCommand(62, 0.5, 2.5));
    	addSequential(new TimedTurnByCommand(0.5, -49.5));
    	addSequential(new DriveStraightToCommand(17, 0.45, 1)); //27
    	addSequential(new AutoIntakeSet(false, true));
    	
//    	addSequential(new ShootCube());
    	
    	/*
    	addParallel(new ResetShooter());
    	addParallel(new ZeroEncoders());
    	addSequential(new SimpleDriveBackward(1, 0.45, 24));
    	addSequential(new TimedTurnByCommand(0.5, 49.5));
    	addSequential(new SimpleDriveBackward(5, 0.6, 61.5));
    	addSequential(new TimedTurnByCommand(0.5, -49.5));
    	addParallel(new GrabCube(2));
    	addSequential(new DriveStraightToCommand(12, 0.3, 2));
    	addParallel(new GrabCube(1));
    	addSequential(new SimpleDriveBackward(1, 0.45, 12));
    	addParallel(new GrabCube(0.5));
    	addSequential(new TimedTurnByCommand(0.5, 49.5));
//    	addParallel(new GrabCube(3));
    	addSequential(new ForwardGo(0.6, 63));
    	addParallel(new GrabCube(0.5));
    	addSequential(new TimedTurnByCommand(0.5, -49.5));
    	addParallel(new LiftCube(1));
    	addSequential(new DriveStraightToCommand(24, 0.45, 1)); //27
    	addSequential(new ShootCube());
    	
    	*/
    	
    	
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
