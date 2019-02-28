
package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorSubsystem extends Subsystem {

	private static ElevatorSubsystem instance;

	private CANSparkMax rightElevator;
	private CANSparkMax leftElevator;

	public static ElevatorSubsystem getInstance() {
		if (instance == null)
			instance = new ElevatorSubsystem();
		return instance;
	}

	public ElevatorSubsystem() {
		rightElevator = new CANSparkMax(Mappings.rightElevator, MotorType.kBrushless);
		rightElevator.setIdleMode(IdleMode.kBrake);
		rightElevator.setSmartCurrentLimit(30);
		rightElevator.setOpenLoopRampRate(0.1);
		rightElevator.getEncoder().setPosition(0.0);

		leftElevator = new CANSparkMax(Mappings.leftElevator, MotorType.kBrushless);
		leftElevator.setIdleMode(IdleMode.kBrake);
		leftElevator.setSmartCurrentLimit(30);
		leftElevator.setOpenLoopRampRate(0.1);
		leftElevator.getEncoder().setPosition(0.0);

		leftElevator.follow(rightElevator);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void setElevatorSpeed(double speed) {
		rightElevator.set(speed);
		leftElevator.set(speed);
		Dashboard.getInstance().putNumber(false, "Elevator Speed", speed);
		System.err.println("[!] HERE'S THE SPEED: " + speed);
	}

	public void setElevatorPos(double position) {
		rightElevator.getEncoder().setPosition(position);
		leftElevator.getEncoder().setPosition(position);
	}

	public double getRotation() { // avg of right and left
		double right = rightElevator.getEncoder().getPosition();
		double left = leftElevator.getEncoder().getPosition();
		return right;
	}

	public double getHeight() {
		return getRotation();
	}

	public enum VerticalDirection {
		UP, DOWN, STOP;
	}

	public enum Levels {
		ZERO(0.0), ONE(5.0), TWO(10.0), THREE(15.0), FOUR(20.0), FIVE(25.0), SIX(30.0);

		private double rotations;

		Levels(double rotations) {
			this.rotations = rotations;
		}

		public double getRotations() {
			return rotations;
		}

		public Levels getLevels() {
			return values()[ordinal()];
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
