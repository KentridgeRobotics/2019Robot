
package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;
import org.usfirst.frc.team3786.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorSubsystem extends Subsystem {

	private static ElevatorSubsystem instance;

	private CANSparkMax rightElevator;
	private CANSparkMax leftElevator;
	private DigitalInput dIn;

	public static final double upMultiplier = 0.8;
	public static final double downMultiplier = 0.55;
	private static final double downMultiplierLow = 0.25;

	public static final double upAutoMultiplier = 0.8;
	public static final double downAutoMultiplier = 1.0;

	private static final int rotationsAcceptableRange = 10;

	private double elevatorSpeed = 0;

	private boolean autoDone = true;
	private double targetLevel;
	private ElevatorSubsystem.VerticalDirection direction;

	public static ElevatorSubsystem getInstance() {
		if (instance == null)
			instance = new ElevatorSubsystem();
		return instance;
	}

	public ElevatorSubsystem() {
		rightElevator = new CANSparkMax(Mappings.rightElevatorMotor, MotorType.kBrushless);
		rightElevator.setIdleMode(IdleMode.kBrake);
		rightElevator.setSmartCurrentLimit(30);
		rightElevator.setOpenLoopRampRate(0.2);
		rightElevator.getEncoder().setPosition(0.0);

		leftElevator = new CANSparkMax(Mappings.leftElevatorMotor, MotorType.kBrushless);
		leftElevator.setIdleMode(IdleMode.kBrake);
		leftElevator.setSmartCurrentLimit(30);
		leftElevator.setOpenLoopRampRate(0.2);
		leftElevator.getEncoder().setPosition(0.0);

		dIn = new DigitalInput(Mappings.elevatorLimitSwitch);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void safetyRun() {
		Dashboard.getInstance().putNumber(false, "Elevator Position", getRotation());
		if (!autoDone)
			runAuto();
		if (!dIn.get()) {
			rightElevator.getEncoder().setPosition(0.0);
			leftElevator.getEncoder().setPosition(0.0);
		}
		if (!dIn.get() && elevatorSpeed < 0) {
			rightElevator.set(0);
			leftElevator.set(0);
		} else {
			double speed = elevatorSpeed;
			if (speed > 0)
				speed *= upMultiplier;
			else
				speed *= downMultiplier;
			if (getRotation() < 30 && speed < 0) {
				speed = elevatorSpeed;
			}
			rightElevator.set(speed);
			leftElevator.set(-speed);
			Dashboard.getInstance().putNumber(false, "Elevator Speed", speed);
		}
	}

	public void setElevatorSpeed(double speed) {
		elevatorSpeed = speed;
	}

	public double getRotation() { // avg of right and left
		double right = rightElevator.getEncoder().getPosition();
		double left = -leftElevator.getEncoder().getPosition();
		return right;
	}

	public double getHeight() {
		return getRotation();
	}

	public void setLevel(Levels targetLevel) {
		if (targetLevel == null)
			return;
		double currentMotorRotations = getRotation();
		if (Math.abs(currentMotorRotations - targetLevel.getRotations()) < rotationsAcceptableRange) {
			return;
		} else if (currentMotorRotations > targetLevel.getRotations()) {
			direction = ElevatorSubsystem.VerticalDirection.DOWN;
		} else if (currentMotorRotations < targetLevel.getRotations()) {
			direction = ElevatorSubsystem.VerticalDirection.UP;
		}
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else if (targetLevel.getType() == LevelType.HATCH)
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		else if (targetLevel.getType() == LevelType.BALL)
			SmartDashboard.putString("Elevator Level", "BALL " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		if (autoDone) {
			autoDone = false;
			Robot.elevatorRunCommand.cancel();
		}
	}

	public void setLevel(double target) {
		double currentMotorRotations = getRotation();
		if (Math.abs(currentMotorRotations - target) < rotationsAcceptableRange) {
			return;
		} else if (currentMotorRotations > target) {
			direction = ElevatorSubsystem.VerticalDirection.DOWN;
		} else if (currentMotorRotations < target) {
			direction = ElevatorSubsystem.VerticalDirection.UP;
		}
		this.targetLevel = target;
		SmartDashboard.putString("Elevator Level", String.valueOf(targetLevel));
		if (autoDone) {
			autoDone = false;
			Robot.elevatorRunCommand.cancel();
		}
	}

	/**
	 * Gets the rotation value of the current level if within an acceptable range,
	 * otherwise returns -1;
	 * 
	 * @return Height of current elevator level, or -1 if not at a preset height
	 */
	public double getCurrentLevel() {
		double currentMotorRotations = getRotation();
		for (Levels level : Levels.values()) {
			if (Math.abs(currentMotorRotations - level.getRotations()) < rotationsAcceptableRange) {
				return level.getRotations();
			}
		}
		return -1;
	}

	public Levels getHatchLevelUp() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (Levels level : Levels.getHatchLevels()) {
			if ((currentMotorRotations + rotationsAcceptableRange) < level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	public Levels getHatchLevelDown() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (int i = Levels.getHatchLevels().length - 1; i >= 0; i--) {
			Levels level = Levels.get(LevelType.HATCH, i);
			if ((currentMotorRotations - rotationsAcceptableRange) > level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	public Levels getBallLevelUp() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (Levels level : Levels.getBallLevels()) {
			if ((currentMotorRotations + rotationsAcceptableRange) < level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	public Levels getBallLevelDown() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (int i = Levels.getBallLevels().length - 1; i >= 0; i--) {
			Levels level = Levels.get(LevelType.BALL, i);
			if ((currentMotorRotations - rotationsAcceptableRange) > level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	public void incrementHatchLevel() {
		Levels targetLevel = getHatchLevelUp();
		if (targetLevel == null)
			return;
		direction = ElevatorSubsystem.VerticalDirection.UP;
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		if (autoDone) {
			autoDone = false;
			Robot.elevatorRunCommand.cancel();
		}
	}

	public void decrementHatchLevel() {
		Levels targetLevel = getHatchLevelDown();
		if (targetLevel == null)
			return;
		direction = ElevatorSubsystem.VerticalDirection.DOWN;
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		if (autoDone) {
			autoDone = false;
			Robot.elevatorRunCommand.cancel();
		}
	}

	public void incrementBallLevel() {
		Levels targetLevel = getBallLevelUp();
		if (targetLevel == null)
			return;
		direction = ElevatorSubsystem.VerticalDirection.UP;
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "BALL " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		if (autoDone) {
			autoDone = false;
			Robot.elevatorRunCommand.cancel();
		}
	}

	public void decrementBallLevel() {
		Levels targetLevel = getBallLevelDown();
		if (targetLevel == null)
			return;
		direction = ElevatorSubsystem.VerticalDirection.DOWN;
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "BALL " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		if (autoDone) {
			autoDone = false;
			Robot.elevatorRunCommand.cancel();
		}
	}

	public void runAuto() {
		if (!autoDone) {
			double currentMotorRotations = ElevatorSubsystem.getInstance().getRotation();
			if (currentMotorRotations < targetLevel && direction == ElevatorSubsystem.VerticalDirection.UP) {
				ElevatorSubsystem.getInstance().setElevatorSpeed(1);
			} else if (currentMotorRotations > targetLevel && direction == ElevatorSubsystem.VerticalDirection.DOWN) {
				ElevatorSubsystem.getInstance().setElevatorSpeed(-1);
			} else {
				autoDone = true;
				ElevatorSubsystem.getInstance().setElevatorSpeed(0);
				Robot.elevatorRunCommand.start();
			}
		}
	}

	public enum VerticalDirection {
		UP, DOWN, STOP;
	}

	public enum LevelType {
		HATCH, BALL, OTHER;
	}

	public enum Levels {
		ZERO(0, 0.0), HATCH_ONE(LevelType.HATCH, 1, 25.8), BALL_ONE(LevelType.BALL, 1, 78.3),
		CLIMB(1, 150), HATCH_TWO(LevelType.HATCH, 2, 182.7), BALL_TWO(LevelType.BALL, 2, 222.9),
		HATCH_THREE(LevelType.HATCH, 3, 326.2), BALL_THREE(LevelType.BALL, 3, 371.8);

		private LevelType type;
		private int level;
		private double rotations;

		private static Levels[] hatchLevels = new Levels[]{ZERO, HATCH_ONE, HATCH_TWO, HATCH_THREE};
		private static Levels[] ballLevels = new Levels[]{ZERO, BALL_ONE, BALL_TWO, BALL_THREE};
		private static Levels[] otherLevels = new Levels[]{ZERO, CLIMB};

		Levels(int level, double rotations) {
			this.type = LevelType.OTHER;
			this.level = level;
			this.rotations = rotations;
		}

		Levels(LevelType type, int level, double rotations) {
			this.type = type;
			this.level = level;
			this.rotations = rotations;
		}

		public static Levels get(LevelType type, int level) {
			for (Levels levels : values()) {
				if (levels.getLevel() == level && levels.getType() == type)
					return levels;
			}
			return null;
		}

		public static Levels get(int level) {
			for (Levels levels : values()) {
				if (levels.getLevel() == level)
					return levels;
			}
			return null;
		}

		public int getLevel() {
			return level;
		}

		public static Levels[] getHatchLevels() {
			return hatchLevels;
		}

		public static Levels[] getBallLevels() {
			return ballLevels;
		}

		public static Levels[] getOtherLevels() {
			return otherLevels;
		}

		public double getRotations() {
			return rotations;
		}

		public LevelType getType() {
			return type;
		}
	}
}
