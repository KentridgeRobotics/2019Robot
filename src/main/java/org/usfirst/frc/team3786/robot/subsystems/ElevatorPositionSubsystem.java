
package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;
import org.usfirst.frc.team3786.robot.Robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorPositionSubsystem extends Subsystem {

	private static ElevatorPositionSubsystem instance;

	private CANSparkMax rightElevator;
	private CANSparkMax leftElevator;
	private DigitalInput dIn;

	public static final double upMultiplier = 0.8;
	public static final double downMultiplier = 0.55;
	private static final double downMultiplierLow = 0.25;

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
		rightElevator = new CANSparkMax(Mappings.rightElevator, MotorType.kBrushless);
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

		leftElevator = new CANSparkMax(Mappings.leftElevator, MotorType.kBrushless);
		leftElevator.setIdleMode(IdleMode.kBrake);
		leftElevator.setSmartCurrentLimit(30);
		leftElevator.setOpenLoopRampRate(0.2);
		leftElevator.follow(rightElevator, true);

		dIn = new DigitalInput(Mappings.elevatorLimitSwitch);

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
				speed = elevatorSpeed * downMultiplierLow;
			}
			Dashboard.getInstance().putNumber(false, "Elevator Speed", speed);
		}
	}

	private void gotoPosition(double pos) {
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

	public void setLevel(HatchLevels targetLevel) {
		if (targetLevel == null)
			return;
		if (targetLevel == HatchLevels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void setLevel(BallLevels targetLevel) {
		if (targetLevel == null)
			return;
		if (targetLevel == BallLevels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "BALL " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void setLevel(double target) {
		this.targetLevel = target;
		gotoPosition(this.targetLevel);
		SmartDashboard.putString("Elevator Level", String.valueOf(targetLevel));
	}

	public HatchLevels getHatchLevelUp() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (HatchLevels level : HatchLevels.values()) {
			if ((currentMotorRotations + rotationsAcceptableRange) < level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	public HatchLevels getHatchLevelDown() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (int i = HatchLevels.values().length - 1; i >= 0; i--) {
			HatchLevels level = HatchLevels.get(i);
			if ((currentMotorRotations - rotationsAcceptableRange) > level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	public BallLevels getBallLevelUp() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (BallLevels level : BallLevels.values()) {
			if ((currentMotorRotations + rotationsAcceptableRange) < level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	public BallLevels getBallLevelDown() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (int i = BallLevels.values().length - 1; i >= 0; i--) {
			BallLevels level = BallLevels.get(i);
			if ((currentMotorRotations - rotationsAcceptableRange) > level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	public void incrementHatchLevel() {
		HatchLevels targetLevel = getHatchLevelUp();
		if (targetLevel == null)
			return;
		if (targetLevel == HatchLevels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void decrementHatchLevel() {
		HatchLevels targetLevel = getHatchLevelDown();
		if (targetLevel == null)
			return;
		if (targetLevel == HatchLevels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void incrementBallLevel() {
		BallLevels targetLevel = getBallLevelUp();
		if (targetLevel == null)
			return;
		if (targetLevel == BallLevels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "BALL " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void decrementBallLevel() {
		BallLevels targetLevel = getBallLevelDown();
		if (targetLevel == null)
			return;
		if (targetLevel == BallLevels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "BALL " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public enum VerticalDirection {
		UP, DOWN, STOP;
	}

	public enum HatchLevels {
		// ZERO(0.0), ONE(52.7), TWO(202.7), THREE(343.6);
		ZERO(0, 0.0), ONE(1, 25.8), TWO(2, 182.7), THREE(3, 326.2);

		private int level;
		private double rotations;

		HatchLevels(int level, double rotations) {
			this.level = level;
			this.rotations = rotations;
		}

		public int getLevel() {
			return level;
		}

		public static HatchLevels get(int level) {
			for (HatchLevels levels : values()) {
				if (levels.getLevel() == level)
					return levels;
			}
			return null;
		}

		public double getRotations() {
			return rotations;
		}

		public HatchLevels getLevels() {
			return values()[ordinal()];
		}

		public HatchLevels up() {
			return values()[ordinal() + 1];
		}

		public HatchLevels down() {
			return values()[ordinal() - 1];
		}

		public HatchLevels stop() {
			return values()[ordinal() + 0];
		}
	}

	public enum BallLevels {
		ZERO(0, 0.0), ONE(1, 78.3), TWO(2, 222.9), THREE(3, 371.8);

		private int level;
		private double rotations;

		BallLevels(int level, double rotations) {
			this.level = level;
			this.rotations = rotations;
		}

		public int getLevel() {
			return level;
		}

		public static BallLevels get(int level) {
			for (BallLevels levels : values()) {
				if (levels.getLevel() == level)
					return levels;
			}
			return null;
		}

		public double getRotations() {
			return rotations;
		}

		public BallLevels getLevels() {
			return values()[ordinal()];
		}

		public BallLevels up() {
			return values()[ordinal() + 1];
		}

		public BallLevels down() {
			return values()[ordinal() - 1];
		}

		public BallLevels stop() {
			return values()[ordinal() + 0];
		}
	}
}
