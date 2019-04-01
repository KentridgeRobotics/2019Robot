
package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.LevelType;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.Levels;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorPositionSubsystem extends Subsystem implements IElevatorSubsystem {

	private static ElevatorPositionSubsystem instance;

	private CANSparkMax rightElevator;
	private CANSparkMax leftElevator;
	private CANDigitalInput limitSwitch;

	private static final double upMultiplier = 0.8;
	private static final double downMultiplier = 0.55;
	private static final double downMultiplierLow = 0.25;
	private static final double lowSpeedThreshold = 30;

	private static final int rotationsAcceptableRange = 10;

	public static double kP = 0.75;
	public static double kI = 0;
	public static double kIMaxAccum = 0;
	public static double kIZone = 0;
	public static double kD = 0.25;
	public static double kDFilter = 0;
	public static double kFF = 0;

	private double elevatorSpeed = 0;

	private boolean autoDone = true;
	private double targetLevel = 0;

	private int pidCycleCount = 0;
	private double lastPidError = 0;

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
	}

	@Override
	public void initDefaultCommand() {
	}

	private void checkNetworkTables() {
		double dP = SmartDashboard.getNumber("Elevator.kP", -1);
		double dI = SmartDashboard.getNumber("Elevator.kI", 0.0);
		double dIMaxAccum = SmartDashboard.getNumber("Elevator.kIMaxAccum", 0.0);
		double dIZone = SmartDashboard.getNumber("Elevator.kIZone", 0.0);
		double dD = SmartDashboard.getNumber("Elevator.kD", 0.0);
		double dDFilter = SmartDashboard.getNumber("Elevator.kDFilter", 0.0);
		double dFF = SmartDashboard.getNumber("Elevator.kFF", 0.0);
		if (kP == -1) {
			SmartDashboard.putNumber("Elevator.kP", kP);
			SmartDashboard.putNumber("Elevator.kI", kI);
			SmartDashboard.putNumber("Elevator.kIMaxAccum", kIMaxAccum);
			SmartDashboard.putNumber("Elevator.kIZone", kIZone);
			SmartDashboard.putNumber("Elevator.kD", kD);
			SmartDashboard.putNumber("Elevator.kDFilter", kDFilter);
			SmartDashboard.putNumber("Elevator.kFF", kFF);
			kP = 0.0;
		}
		if (dP != kP) {
			kP = dP;
			rightElevator.getPIDController().setP(kP);
			System.out.println("P: " + kP);
		}
		if (dI != kI) {
			kI = dI;
			rightElevator.getPIDController().setI(kI);
			System.out.println("I: " + kI);
		}
		if (dIMaxAccum != kIMaxAccum) {
			kIMaxAccum = dIMaxAccum;
			rightElevator.getPIDController().setIMaxAccum(kIMaxAccum, 0);
			System.out.println("IMaxAccum: " + kIMaxAccum);
		}
		if (dIZone != kIZone) {
			kIZone = dIZone;
			rightElevator.getPIDController().setIZone(kIZone);
			System.out.println("IZone: " + kIZone);
		}
		if (dD != kD) {
			kD = dD;
			rightElevator.getPIDController().setD(kD);
			System.out.println("D: " + kD);
		}
		if (dDFilter != kDFilter) {
			kDFilter = dDFilter;
			rightElevator.getPIDController().setDFilter(kDFilter);
			System.out.println("DFilter: " + kDFilter);
		}
		if (dFF != kFF) {
			kFF = dFF;
			rightElevator.getPIDController().setFF(kFF);
			System.out.println("FF: " + kFF);
		}
	}

	public void safetyRun() {
		checkNetworkTables();
		Dashboard.getInstance().putNumber(false, "Elevator Position", getRotation());
		double error = targetLevel - getRotation();
		SmartDashboard.putNumber("Elevator.error", error);
		if (!autoDone) {
			if (Math.abs(error - lastPidError) < 0.05) {
				pidCycleCount++;
				if (pidCycleCount >= 10) {
					autoDone = true;
				}
			} else {
				pidCycleCount = 0;
				lastPidError = error;
			}
		}
		if (limitSwitch.get()) {
			rightElevator.getEncoder().setPosition(0.0);
			leftElevator.getEncoder().setPosition(0.0);
		}
		if (limitSwitch.get() && rightElevator.get() < 0) {
			rightElevator.set(0);
			leftElevator.set(0);
		} else if (getRotation() >= 390 && rightElevator.get() > 0) {
			rightElevator.set(0);
			leftElevator.set(0);
		} else {
			double speed = elevatorSpeed;
			if (speed > 0)
				speed *= upMultiplier;
			else
				speed *= downMultiplier;
			if (getRotation() < lowSpeedThreshold && speed < -downMultiplierLow) {
				speed = -downMultiplierLow;
			}
			if (getRotation() > (390 - lowSpeedThreshold) && speed > downMultiplierLow) {
				speed = downMultiplierLow;
			}
			if (autoDone) {
				rightElevator.set(speed);
				leftElevator.set(-speed);
			}
			Dashboard.getInstance().putNumber(false, "Elevator Speed", speed);
		}
	}

	public void gotoPosition(double pos) {
		targetLevel = pos;
		Dashboard.getInstance().putNumber(false, "Elevator PID Position", pos);
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
		SmartDashboard.putString("Elevator Level", targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		gotoPosition(this.targetLevel);
	}

	public void setLevel(double target) {
		this.targetLevel = target;
		gotoPosition(this.targetLevel);
		SmartDashboard.putString("Elevator Level", String.valueOf(targetLevel));
	}

	public Levels getCurrentLevel() {
		double currentMotorRotations = getRotation();
		for (Levels level : Levels.values()) {
			if (Math.abs(currentMotorRotations - level.getRotations()) < rotationsAcceptableRange) {
				return level;
			}
		}
		return null;
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
		if (targetLevel == Levels.ZERO)
			SmartDashboard.putString("Elevator Level", targetLevel.toString());
		else
			SmartDashboard.putString("Elevator Level", "HATCH " + targetLevel.toString());
		this.targetLevel = targetLevel.getRotations();
		autoDone = false;
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
		autoDone = false;
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
		autoDone = false;
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
		autoDone = false;
		gotoPosition(this.targetLevel);
	}
}
