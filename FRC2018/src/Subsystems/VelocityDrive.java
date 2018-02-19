package Subsystems;

import org.usfirst.frc.team2415.robot.Robot;
import org.usfirst.frc.team2415.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import Cheesy.DriveSignal;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class VelocityDrive extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private WPI_TalonSRX lFront, rFront, lBack, rBack;
	private DoubleSolenoid shifter;
	public AHRS ahrs;

	// private Solenoid pu, pupu;
	private final double WHEEL_CIRCUMFERENCE = 5 * Math.PI; // inches

	private double STRAIGHT_INTERPOLATION_FACTOR = 0.60;
	private double TURNING_INTERPOLATION_FACTOR = .3; // needs to be decided on
														// by Nathan
	private double MAX_RPM = 500.0;

	private double DEADBAND = 0.1;
	private double FORWARD_STRAIGHT_RESTRICTER = 1;
	private double FORWARD_TURN_SPEED_BOOST = 0.4;
	private double BACKWARD_STRAIGHT_RESTRICTER = 1;
	private double BACKWARD_TURN_SPEED_BOOST = 0.4;
	private double overPower = .750; // .55
	private boolean pointTurn;
	private boolean brake;
	public double left, right;
	// if we win, be happy robot :)

	private double kP = 0;
	private double kI = 0;
	private double kD = 0;
	private double kF = 0;
	private int kTimeout = 10;

	public VelocityDrive() {

		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus. */
			/*
			 * Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or
			 * SerialPort.Port.kUSB
			 */
			/*
			 * See
			 * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/
			 * for details.
			 */
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		}

		lFront = new WPI_TalonSRX(RobotMap.LEFT_TALON_FRONT);
		rFront = new WPI_TalonSRX(RobotMap.RIGHT_TALON_FRONT);
		lBack = new WPI_TalonSRX(RobotMap.LEFT_TALON_BACK);
		rBack = new WPI_TalonSRX(RobotMap.RIGHT_TALON_BACK);

		// shifter, shifter1, shifter 2 -- dont work

		shifter = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.GEAR_SHIFTER_BACK, RobotMap.GEAR_SHIFTER_FRONT);

		lBack.set(ControlMode.Follower, lFront.getDeviceID());
		rBack.set(ControlMode.Follower, rFront.getDeviceID());

		lFront.config_kP(0, kP, kTimeout);
		lFront.config_kI(0, kI, kTimeout);
		lFront.config_kD(0, kD, kTimeout);
		lFront.config_kF(0, kF, kTimeout);

		lFront.configPeakCurrentLimit(35, 10);
		lFront.configPeakCurrentDuration(200, 10);
		lFront.configContinuousCurrentLimit(30, 10);
		lFront.enableCurrentLimit(true);

		rFront.configPeakCurrentLimit(35, 10);
		rFront.configPeakCurrentDuration(200, 10);
		rFront.configContinuousCurrentLimit(30, 10);
		rFront.enableCurrentLimit(true);

		lFront.setNeutralMode(NeutralMode.Brake);
		rFront.setNeutralMode(NeutralMode.Brake);

		lBack.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rBack.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

		// lBack.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms,
		// 10);
		// lBack.configVelocityMeasurementWindow(128, 10);

	}

	public void velDrive(double leftY, double rightX) {

		leftY = Robot.gamepad.getRawAxis(1);
		rightX = Robot.gamepad.getRawAxis(4);

		if (Math.abs(leftY) < Math.abs(DEADBAND))
			leftY = 0;
		if (Math.abs(rightX) < Math.abs(DEADBAND))
			rightX = 0;

		pointTurn = Math.abs(leftY) <= .1 && Math.abs(rightX) >= .1;

		if (!pointTurn) {
			setBrakeMode(true);

			if (Math.abs(leftY) < DEADBAND)
				leftY = 0;
			if (Math.abs(rightX) < DEADBAND)
				rightX = 0;

			leftY = STRAIGHT_INTERPOLATION_FACTOR * Math.pow(leftY, 3) + (1 - STRAIGHT_INTERPOLATION_FACTOR) * leftY;
			rightX = TURNING_INTERPOLATION_FACTOR * Math.pow(rightX, 3) + (1 - TURNING_INTERPOLATION_FACTOR) * rightX;

			if (leftY >= 0) {
				left = FORWARD_STRAIGHT_RESTRICTER * leftY - FORWARD_TURN_SPEED_BOOST * rightX;
				right = FORWARD_STRAIGHT_RESTRICTER * leftY + FORWARD_TURN_SPEED_BOOST * rightX;
			} else {
				left = BACKWARD_STRAIGHT_RESTRICTER * leftY - BACKWARD_TURN_SPEED_BOOST * rightX;
				right = BACKWARD_STRAIGHT_RESTRICTER * leftY + BACKWARD_TURN_SPEED_BOOST * rightX;
			}

			if (left > 1.0) {
				right -= overPower * (left - 1.0);
				left = 1.0;
			} else if (right > 1.0) {
				left -= overPower * (right - 1.0);
				right = 1.0;
			} else if (left < -1.0) {
				right += overPower * (-1.0 - left);
				left = -1.0;
			} else if (right < -1.0) {
				left += overPower * (-1.0 - right);
				right = -1.0;
			}

			setVelocity(left * 4096 * MAX_RPM / 600, right * 4096 * MAX_RPM / 600);

		} else {

			setVelocity(.87 * 1079 * rightX, -.87 * 1079 * rightX);
		}

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

	public double[] getVelocity() {
		return new double[] { lBack.getSelectedSensorVelocity(0), rFront.getSelectedSensorVelocity(0) };
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
		rFront.set(right);
	}

	public void setVelocity(double leftVel, double rightVel) {
		lFront.set(ControlMode.Velocity, leftVel);
		rFront.set(ControlMode.Velocity, rightVel);
	}

	public double[] getDistance() {
		return new double[] { (double) lBack.getSelectedSensorPosition(0) / 4096 * WHEEL_CIRCUMFERENCE,
				(double) rBack.getSelectedSensorPosition(0) / 4096 * WHEEL_CIRCUMFERENCE };
	}

	public double fPS2RPM(double fps) {
//		return (fps * 60) / (WHEEL_CIRCUMFERENCE);
		return fps / 10 * 12 / WHEEL_CIRCUMFERENCE * 4096;
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

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}