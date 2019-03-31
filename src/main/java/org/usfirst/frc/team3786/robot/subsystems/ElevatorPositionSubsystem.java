
package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorPositionSubsystem extends Subsystem {

	private static ElevatorPositionSubsystem instance;

	private CANSparkMax rightElevator;
	private CANSparkMax leftElevator;
	private CANDigitalInput limitSwitch;

	public static final double upMultiplier = 0.8;
	public static final double downMultiplier = 1;
	private static final double downMultiplierLow = 1;

	public static final double upAutoMultiplier = 0.8;
	public static final double downAutoMultiplier = 1.0;

	public static double kP = 0.5;
	public static double kI = 0;
	public static double kIMaxAccum = 0;
	public static double kIZone = 0;
	public static double kD = 0;
	public static double kDFilter = 0;
	public static double kFF = 0;

	private static final int rotationsAcceptableRange = 10;

	private double elevatorSpeed = 0;

	private boolean autoDone = true;
	private double targetLevel;

	public static ElevatorPositionSubsystem getInstance() {
		if (instance == null)
			instance = new ElevatorPositionSubsystem();
		return instance;
	}

	public ElevatorPositionSubsystem() {
		rightElevator = new CANSparkMax(Mappings.rightElevatorMotor, MotorType.kBrushless);
		rightElevator.setIdleMode(IdleMode.kBrake);
		rightElevator.setSmartCurrentLimit(30);
		rightElevator.setOpenLoopRampRate(0.2);
		rightElevator.getPIDController().setOutputRange(-downMultiplier, upMultiplier);
		rightElevator.getPIDController().setP(kP);
		rightElevator.getPIDController().setI(kI);
		rightElevator.getPIDController().setIMaxAccum(kIMaxAccum, 0);
		rightElevator.getPIDController().setIZone(kIZone);
		rightElevator.getPIDController().setD(kD);
		rightElevator.getPIDController().setDFilter(kDFilter);
		rightElevator.getPIDController().setFF(kFF);
		limitSwitch = rightElevator.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
		limitSwitch.enableLimitSwitch(true);

		leftElevator = new CANSparkMax(Mappings.leftElevatorMotor, MotorType.kBrushless);
		leftElevator.setIdleMode(IdleMode.kBrake);
		leftElevator.setSmartCurrentLimit(30);
		leftElevator.setOpenLoopRampRate(0.2);
		leftElevator.follow(rightElevator, true);

		SmartDashboard.putNumber("Elevator.kP", kP);
		SmartDashboard.putNumber("Elevator.kI", kI);
		SmartDashboard.putNumber("Elevator.kIMaxAccum", kIMaxAccum);
		SmartDashboard.putNumber("Elevator.kIZone", kIZone);
		SmartDashboard.putNumber("Elevator.kD", kD);
		SmartDashboard.putNumber("Elevator.kDFilter", kDFilter);
		SmartDashboard.putNumber("Elevator.kFF", kFF);
	}

	@Override
	public void initDefaultCommand() {
	}

	private void checkNetworkTables() {
		double dP = SmartDashboard.getNumber("Elevator.kP", 0.0);
		double dI = SmartDashboard.getNumber("Elevator.kI", 0.0);
		double dIMaxAccum = SmartDashboard.getNumber("Elevator.kIMaxAccum", 0.0);
		double dIZone = SmartDashboard.getNumber("Elevator.kIZone", 0.0);
		double dD = SmartDashboard.getNumber("Elevator.kD", 0.0);
		double dDFilter = SmartDashboard.getNumber("Elevator.kDFilter", 0.0);
		double dFF = SmartDashboard.getNumber("Elevator.kFF", 0.0);
		if (dP != kP) {
			kP = dP;
			rightElevator.getPIDController().setP(kP);
		}
		if (dI != kI) {
			kI = dI;
			rightElevator.getPIDController().setI(kI);
		}
		if (dIMaxAccum != kIMaxAccum) {
			kIMaxAccum = dIMaxAccum;
			rightElevator.getPIDController().setIMaxAccum(kIMaxAccum, 0);
		}
		if (dIZone != kIZone) {
			kIZone = dIZone;
			rightElevator.getPIDController().setIZone(kIZone);
		}
		if (dD != kD) {
			kD = dD;
			rightElevator.getPIDController().setD(kD);
		}
		if (dDFilter != kDFilter) {
			kDFilter = dDFilter;
			rightElevator.getPIDController().setDFilter(kDFilter);
		}
		if (dFF != kFF) {
			kFF = dFF;
			rightElevator.getPIDController().setFF(kFF);
		}
	}

	public void safetyRun() {
		checkNetworkTables();
		Dashboard.getInstance().putNumber(false, "Elevator Position", getRotation());
		if (limitSwitch.get()) {
			rightElevator.getEncoder().setPosition(0.0);
			leftElevator.getEncoder().setPosition(0.0);
		}
		if (limitSwitch.get() && elevatorSpeed < 0) {
			rightElevator.set(0);
			leftElevator.set(0);
		} else {
			double speed = elevatorSpeed;
			if (speed > 0)
				speed *= upMultiplier;
			else
				speed *= downMultiplier;
			if (getRotation() < 30 && speed < 0) {
				speed = elevatorSpeed * downMultiplierLow;
			}
			Dashboard.getInstance().putNumber(false, "Elevator Speed", speed);
		}
	}

	public void gotoPosition(double pos) {
		rightElevator.getPIDController().setReference(pos, ControlType.kPosition);
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
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else if (targetLevel.getType() == LevelType.HATCH)
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		else if (targetLevel.getType() == LevelType.BALL)
			SmartDashboard.putString("Elevator Level", "BALL " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void setLevel(double target) {
		this.targetLevel = target;
		gotoPosition(this.targetLevel);
		SmartDashboard.putString("Elevator Level", String.valueOf(targetLevel));
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
		for (Levels level : Levels.getBallLevels()) {
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
		for (int i = Levels.getBallLevels().length - 1; i >= 0; i--) {
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
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void decrementHatchLevel() {
		Levels targetLevel = getHatchLevelDown();
		if (targetLevel == null)
			return;
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void incrementBallLevel() {
		Levels targetLevel = getBallLevelUp();
		if (targetLevel == null)
			return;
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "BALL " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void decrementBallLevel() {
		Levels targetLevel = getBallLevelDown();
		if (targetLevel == null)
			return;
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "BALL " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public enum VerticalDirection {
		UP, DOWN, STOP;
	}

	public enum LevelType {
		HATCH, BALL, OTHER;
	}

	public enum Levels {
		ZERO(0, 0.0), HATCH_ONE(LevelType.HATCH, 1, 80.09), BALL_ONE(LevelType.BALL, 1, 126.95),
		CLIMB(1, 150), HATCH_TWO(LevelType.HATCH, 2, 226.58), BALL_TWO(LevelType.BALL, 2, 222.9),
		HATCH_THREE(LevelType.HATCH, 3, 376.57), BALL_THREE(LevelType.BALL, 3, 371.8);

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
