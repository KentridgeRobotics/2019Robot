
package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorSubsystem extends Subsystem {

	private static ElevatorSubsystem instance;

	private CANSparkMax rightElevator;
	private CANSparkMax leftElevator;
	private DigitalInput dIn;

	public static final double upMultiplier = 0.6;
	public static final double downMultiplier = 0.45;
	private static final double downMultiplierLow = 0.2 / downMultiplier;

	public static ElevatorSubsystem getInstance() {
		if (instance == null)
			instance = new ElevatorSubsystem();
		return instance;
	}

	public ElevatorSubsystem() {
		rightElevator = new CANSparkMax(Mappings.rightElevator, MotorType.kBrushless);
		rightElevator.setIdleMode(IdleMode.kBrake);
		rightElevator.setSmartCurrentLimit(30);
		rightElevator.setOpenLoopRampRate(0.2);
		rightElevator.getEncoder().setPosition(0.0);

		leftElevator = new CANSparkMax(Mappings.leftElevator, MotorType.kBrushless);
		leftElevator.setIdleMode(IdleMode.kBrake);
		leftElevator.setSmartCurrentLimit(30);
		leftElevator.setOpenLoopRampRate(0.2);
		leftElevator.getEncoder().setPosition(0.0);

		dIn = new DigitalInput(5);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void safetyRun() {
		if (!dIn.get() && rightElevator.get() < 0) {
			rightElevator.set(0);
			leftElevator.set(0);
		}
		if (!dIn.get()) {
			rightElevator.getEncoder().setPosition(0.0);
			leftElevator.getEncoder().setPosition(0.0);
		}
		if (rightElevator.getEncoder().getPosition() < 50 && rightElevator.get() < 0) {
			rightElevator.set(rightElevator.get() * downMultiplierLow);
			leftElevator.set(leftElevator.get() * downMultiplierLow);
		}
	}

	public void setElevatorSpeed(double speed) {
		System.out.println("Right: " + rightElevator.getEncoder().getPosition());
		System.out.println("Left: " + leftElevator.getEncoder().getPosition());
		if (speed > 0)
			speed *= upMultiplier;
		else
			speed *= downMultiplier;
		rightElevator.set(speed);
		leftElevator.set(-speed);
		Dashboard.getInstance().putNumber(false, "Elevator Speed", speed);
	}

	public void setElevatorPos(double position) {
		if (position < 0)
			position = 0;
		rightElevator.getEncoder().setPosition(position);
		leftElevator.getEncoder().setPosition(-position);
	}

	public double getRotation() { // avg of right and left
		double right = rightElevator.getEncoder().getPosition();
		double left = -leftElevator.getEncoder().getPosition();
		return right;
	}

	public double getHeight() {
		return getRotation();
	}

	public enum VerticalDirection {
		UP, DOWN, STOP;
	}

	public enum Levels {
		ZERO(0.0), ONE(25.8), TWO(182.7), THREE(326.2), FOUR(78.3), FIVE(222.9), SIX(371.8);

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
