package Subsystems;

import java.awt.Robot;

import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import Cheesy.DriveSignal;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArcadeDrive extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	// private static ArcadeDrive mInstance = new ArcadeDrive();

	private WPI_TalonSRX lFront, rFront, lBack, rBack;
	private DoubleSolenoid shifter;
	public AHRS ahrs;
	public boolean toggling;

	// private Solenoid pu, pupu;
	private final double WHEEL_CIRCUMFERENCE = 6 * Math.PI; // inches

	public double DEADBAND = 0.05;
	public double STRAIGHT_RESTRICTER = 1;
	public double TURN_SPEED_BOOST = 0.35;
	public double INTERPOLATION_FACTOR = 0.420;

	// if we win, be happy robot :)

	// public ArcadeDrive getInstance() {
	// return mInstance;
	// }

	public ArcadeDrive() {

		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus. */
			/*
			 * Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB
			 */
			/*
			 * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
			 * details.
			 */
			ahrs = new AHRS(I2C.Port.kMXP); // I2C works on competition SPI on practice
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}

		lFront = new WPI_TalonSRX(RobotMap.LEFT_TALON_FRONT);
		rFront = new WPI_TalonSRX(RobotMap.RIGHT_TALON_FRONT);
		lBack = new WPI_TalonSRX(RobotMap.LEFT_TALON_BACK);
		rBack = new WPI_TalonSRX(RobotMap.RIGHT_TALON_BACK);

		shifter = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.GEAR_SHIFTER_BACK, RobotMap.GEAR_SHIFTER_FRONT);

		// lBack.set(ControlMode.Follower, lFront.getDeviceID());
		// rBack.set(ControlMode.Follower, rFront.getDeviceID());

		lFront.configPeakCurrentLimit(30, 10); // 35
		lFront.configPeakCurrentDuration(200, 10);
		lFront.configContinuousCurrentLimit(25, 10); // 30
		lFront.enableCurrentLimit(true);

		lBack.configPeakCurrentLimit(30, 10); // 35
		lBack.configPeakCurrentDuration(200, 10);
		lBack.configContinuousCurrentLimit(25, 10); // 30
		lBack.enableCurrentLimit(true);

		// lFront.configVoltageCompSaturation(11.0, 10);
		// lFront.enableVoltageCompensation(true);
		// rFront.configVoltageCompSaturation(11.0, 10);
		// rFront.enableVoltageCompensation(true);

		rFront.configPeakCurrentLimit(30, 10);
		rFront.configPeakCurrentDuration(200, 10);
		rFront.configContinuousCurrentLimit(25, 10);
		rFront.enableCurrentLimit(true);

		rBack.configPeakCurrentLimit(30, 10);
		rBack.configPeakCurrentDuration(200, 10);
		rBack.configContinuousCurrentLimit(25, 10);
		rBack.enableCurrentLimit(true);

		lFront.setNeutralMode(NeutralMode.Brake);
		rFront.setNeutralMode(NeutralMode.Brake);
		lBack.setNeutralMode(NeutralMode.Brake);
		rBack.setNeutralMode(NeutralMode.Brake);

		lBack.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rBack.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

		// lBack.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 10);
		// lBack.configVelocityMeasurementWindow(128, 10);

	}

	public void setOne(double num) {
		lBack.set(num);
	}

	public void slaveRight(boolean slave) {
		if (slave)
			rFront.set(ControlMode.Follower, lFront.getDeviceID());
		else
			rFront.set(ControlMode.PercentOutput, 0);
	}

	public void drive(DriveSignal signal) {
		setMotors(-signal.getLeft(), signal.getRight());
	}
 
	public Value getShifter() {
		return shifter.get();
	}

	public void setHighGear(boolean gear) {
		if (gear) {
			shifter.set(DoubleSolenoid.Value.kForward);
		} else {
			shifter.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public boolean isHighGear() {
		if (shifter.get() == DoubleSolenoid.Value.kForward) {
			return true;
		} else {
			return false;
		}
	}

	public void toggleHighGear() {
		if (toggling == false) {
			if (shifter.get() == Value.kForward) {
				shifter.set(Value.kReverse);
				toggling = true;
			} else if (shifter.get() == Value.kReverse) {
				shifter.set(Value.kForward);
				toggling = true;
			} else {
				shifter.set(Value.kForward);
				toggling = true;
			}
		}
	}

	public double[] getVelocity() {
		return new double[] { lBack.getSelectedSensorVelocity(0), -rFront.getSelectedSensorVelocity(0) };
	}

	public double getVoltage() {
		double a = lFront.getMotorOutputVoltage() / lFront.getBusVoltage();
		double b = rFront.getMotorOutputVoltage() / rFront.getBusVoltage();
		return (a + b) / 2;
	}

	// public double getBattery() {
	// return Robot.pdp.getVoltage();
	// return 0;
	// }

	public void setMotors(double left, double right) {
		lFront.set(left);
		lBack.set(left);
		rFront.set(right);
		rBack.set(right);
	}

	public void setVelocity(double leftVel, double rightVel) {
		lFront.set(ControlMode.Velocity, leftVel);
		lBack.set(ControlMode.Velocity, leftVel);
		rFront.set(ControlMode.Velocity, rightVel);
		rBack.set(ControlMode.Velocity, rightVel);
	}

	public double[] getDistance() {
		return new double[] { (double) lBack.getSelectedSensorPosition(0) / 4096 * WHEEL_CIRCUMFERENCE,
				(double) rBack.getSelectedSensorPosition(0) / 4096 * WHEEL_CIRCUMFERENCE };
	}

	public void zeroEncoders() {
		lBack.setSelectedSensorPosition(0, 0, 10);
		rBack.setSelectedSensorPosition(0, 0, 10);
	}

	public double getPitch() {
		return ahrs.getPitch();
	}

	public double getYaw() {
		return ahrs.getYaw();
	}

	public void zeroYaw() {
		ahrs.zeroYaw();
	}

	public double getRoll() {
		return ahrs.getRoll();
	}

	public double getAngle() {
		return ahrs.getAngle();
	}

	public void setBrakeMode(boolean brake) {
		if (brake) {
			lFront.setNeutralMode(NeutralMode.Brake);
			rFront.setNeutralMode(NeutralMode.Brake);
		} else {
			lFront.setNeutralMode(NeutralMode.Coast);
			rFront.setNeutralMode(NeutralMode.Coast);
		}
	}

	public double getMotorOutput() {
		return lFront.getMotorOutputPercent();
	}

	public double fPS2RPM(double fps) {
		return fps / 10 * WHEEL_CIRCUMFERENCE / 12 * 4096;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}