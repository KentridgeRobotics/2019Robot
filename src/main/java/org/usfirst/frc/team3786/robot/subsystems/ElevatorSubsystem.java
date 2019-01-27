
package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;
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
		elevatorMotor.setSmartCurrentLimit(30);
		elevatorMotor.setRampRate(0.5);
		//tiltMotor = new CANSparkMax(Mappings.tiltMotor, MotorType.kBrushless);
		
	}

	@Override
	public void initDefaultCommand() {
	}

	public void setElevatorSpeed(double speed) {
		elevatorMotor.set(speed);
		Dashboard.getInstance().putNumber(false,"Elevator Speed", speed);
		System.err.println("[!] HERE'S THE SPEED: " + speed);
	}

	public void setTiltSpeed(double speed) {
		Dashboard.getInstance().putNumber(false, "Tilt Speed", speed);
		tiltMotor.set(speed);
	}

	public double getRotation() {
		return elevatorMotor.getEncoder().getPosition();
	}

	public double getHeight() {
		return getRotation();
	}
	
	public enum VerticalDirection {
		UP,
		DOWN,
		STOP;
	}

	public enum Levels {
		ZERO(0.0),
		ONE(1.0),
		TWO(2.0),
		THREE(3.0);

		private double rotations;

		Levels(double rotations) {
			this.rotations = rotations;
		}
		public double getRotations() {
			return rotations;
		}

		public Levels up() {
			return values()[ordinal() + 1];
		}

		public Levels down() {
			return values()[ordinal() - 1];
		}

		public Levels stop() {
			return values()[ordinal() + 0];
		}
	}
}
