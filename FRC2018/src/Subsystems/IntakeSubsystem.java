package Subsystems;

import org.usfirst.frc.team2415.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {

	private CANTalon cantalonRight, cantalonLeft;
	private Solenoid solenoid1, solenoid2;
	private DigitalInput irSensor;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public IntakeSubsystem() {
    	irSensor = new DigitalInput(Robot.IR_SENSOR);
		solenoid1 = new Solenoid(Robot.INTAKE_SOLENOID_1);
		solenoid2 = new Solenoid(Robot.INTAKE_SOLENOID_2);
		cantalonRight = new CANTalon(Robot.INTAKE_TALON_RIGHT);
		cantalonLeft = new CANTalon(Robot.INTAKE_TALON_LEFT);
    }
    
    public void setSolenoid(boolean state) {
    	solenoid1.set(state);
    	solenoid2.set(state);
    }
    
    public void setTalon(double speed) {
    	cantalonRight.set(speed);
    	cantalonLeft.set(-speed);
    }
    
    public boolean getIR() {
    	return irSensor.get();
    }
}
