
package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorSubsystem extends Subsystem {

	private static ElevatorSubsystem instance;

	private CANSparkMax elevatorMotor;
	private CANSparkMax tiltMotor;

	public static ElevatorSubsystem getInstance() {
		if (instance == null)
			instance = new ElevatorSubsystem();
		return instance;
	}

	public ElevatorSubsystem() {
		elevatorMotor = new CANSparkMax(Mappings.elevatorMotor, MotorType.kBrushless);
		elevatorMotor.setIdleMode(IdleMode.kBrake);
		//tiltMotor = new CANSparkMax(Mappings.tiltMotor, MotorType.kBrushless);
		
	}

	@Override
	public void initDefaultCommand() {
	}

	public void setElevatorSpeed(double speed) {
		elevatorMotor.set(speed);
		System.err.println("[!] HERE'S THE SPEED: " + speed);
	}

	public void setTiltSpeed(double speed) {
		tiltMotor.set(speed);
	}

	public double getRotation() {
		return elevatorMotor.getEncoder().getPosition();
	}

	public double getHeight() {
		return getRotation();
	}
	
	public enum Levels {
		ZERO,
		ONE,
		TWO,
		THREE;
	}
}
