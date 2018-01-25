package Subsystems;

import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import Cheesy.DriveSignal;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArcadeDrive extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
//	private static ArcadeDrive mInstance = new ArcadeDrive();
	
	private WPI_TalonSRX lFront, rFront, lBack, rBack;
    private DoubleSolenoid isabella;
//    private Solenoid pu, pupu;
    private final int WHEEL_CIRCUMFERENCE = 5; //inches

    public double DEADBAND = 0.05;
	public double STRAIGHT_RESTRICTER = 1; 
	public double TURN_SPEED_BOOST = 0.35;
	public double INTERPOLATION_FACTOR = 0.420;
    
	//if we win, be happy robot :)
	
//	public ArcadeDrive getInstance() {
//		return mInstance;
//	}
	
	public ArcadeDrive() {
	
		lFront = new WPI_TalonSRX(RobotMap.LEFT_TALON_FRONT);
		rFront = new WPI_TalonSRX(RobotMap.RIGHT_TALON_BACK);
		lBack = new WPI_TalonSRX(RobotMap.LEFT_TALON_BACK);
		rBack = new WPI_TalonSRX(RobotMap.RIGHT_TALON_BACK);
		
		//shifter, shifter1, shifter 2 -- dont work
		
		isabella = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.GEAR_SHIFTER_BACK, RobotMap.GEAR_SHIFTER_FRONT);
		
		lBack.set(ControlMode.Follower, lFront.getDeviceID());
		rBack.set(ControlMode.Follower, rFront.getDeviceID());
		
		lFront.setNeutralMode(NeutralMode.Coast);
		rFront.setNeutralMode(NeutralMode.Coast);
		
		lFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		rFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		
		lFront.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 0);
		lFront.configVelocityMeasurementWindow(64, 0);
	
	}
	
	public void drive(DriveSignal signal) {
		setMotors(-signal.getLeft(), signal.getRight());
	}
	
	public Value getShifter() {
		return isabella.get();
	}
	
	public void switchGear(boolean gear) {
		if(gear) {
			isabella.set(DoubleSolenoid.Value.kForward);
		} else {
			isabella.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
	public double getVelocity() {
		return (lFront.getSelectedSensorVelocity(0) + rFront.getSelectedSensorVelocity(0)) / 2;
	}
	
	public double getVoltage() {
		double a = lFront.getMotorOutputVoltage()/lFront.getBusVoltage();
		double b = rFront.getMotorOutputVoltage()/rFront.getBusVoltage();
		return (a + b)/2; 
	}
	
	public void setMotors(double left, double right) {
		lFront.set(left);
		rFront.set(right);
	}
	
	public double[] getDistance() {
//		return new double[]{lFront.getPosition()*WHEEL_CIRCUMFERENCE,
//				rFront.getPosition()*WHEEL_CIRCUMFERENCE};
		return null;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
}