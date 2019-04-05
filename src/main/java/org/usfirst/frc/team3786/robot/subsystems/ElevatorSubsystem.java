package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorSubsystem extends Subsystem {

	private static ElevatorSubsystem instance;

	private CANSparkMax rightElevator;
	private CANSparkMax leftElevator;
	private CANDigitalInput limitSwitch;

	// Max upward speed
	private static final double upLimit = 0.8;
	// Max downward speed
	private static final double downLimit = 0.55;
	// Slower speed for near top/bottom limits
	private static final double lowLimit = 0.45;
	// Range of rotations from top/bottom for lowLimit speed
	private static final double lowSpeedEpsilon = 30;
	// Max height of the elevator
	private static final double maxHeight = 390;
	// Acceptable deviation from target height used when incrementing/decrementing target level
	private static final int rotationsEpsilon = 3;

	// PID values
	public static double kP = 0.75;
	public static double kI = 0;
	public static double kIMaxAccum = 0;
	public static double kIZone = 0;
	public static double kD = 0.25;
	public static double kDFilter = 0;
	public static double kFF = 0;

	// Target elevator speed when driving manually
	private double elevatorSpeed = 0;

	// Is PID control finished
	private boolean autoDone = true;
	// Target PID level
	private double targetLevel = 0;

	// Robot cycles where PID error has not changed
	private int pidCycleCount = 0;
	// Previous PID error to compare against to tell when PID is finished
	private double lastPidError = 0;

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
		rightElevator.getPIDController().setOutputRange(-downLimit, upLimit);
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

	// UNUSED
	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * Checks network tables for PID value updates
	 */
	private void checkNetworkTables() {
		double dP = SmartDashboard.getNumber("Elevator.PID.P", -1);
		double dI = SmartDashboard.getNumber("Elevator.PID.I", 0.0);
		double dIMaxAccum = SmartDashboard.getNumber("Elevator.PID.IMaxAccum", 0.0);
		double dIZone = SmartDashboard.getNumber("Elevator.PID.IZone", 0.0);
		double dD = SmartDashboard.getNumber("Elevator.PID.D", 0.0);
		double dDFilter = SmartDashboard.getNumber("Elevator.PID.DFilter", 0.0);
		double dFF = SmartDashboard.getNumber("Elevator.PID.FF", 0.0);
		// Data is not present on network tables, push default values
		if (dP == -1) {
			SmartDashboard.putNumber("Elevator.PID.P", kP);
			SmartDashboard.putNumber("Elevator.PID.I", kI);
			SmartDashboard.putNumber("Elevator.PID.IMaxAccum", kIMaxAccum);
			SmartDashboard.putNumber("Elevator.PID.IZone", kIZone);
			SmartDashboard.putNumber("Elevator.PID.D", kD);
			SmartDashboard.putNumber("Elevator.PID.DFilter", kDFilter);
			SmartDashboard.putNumber("Elevator.PID.FF", kFF);
			dP = 0.0;
		}
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

	/**
	 * Run each robot cycle for vital control checks and data input/output
	 */
	public void runIteration() {
		// Check network tables for PID value updates
		checkNetworkTables();
		
		// Calculate PID error and end auto if error does not change
		double error = targetLevel - getRotation();
		SmartDashboard.putNumber("Elevator.PID.Error", error);
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
		
		// Resets encoder position to 0 when limit switch is tripped
		if (limitSwitch.get()) {
			rightElevator.getEncoder().setPosition(0.0);
			leftElevator.getEncoder().setPosition(0.0);
		}
		
		// Force stops elevator when driving upward and at position 390 to prevent damage
		if (getRotation() >= maxHeight && rightElevator.get() > 0) {
			rightElevator.set(0);
			leftElevator.set(0);
		}
		// Elevator is within safe operating range, drive normally
		else {
			double speed = elevatorSpeed;
			if (speed > 0)
				speed *= upLimit;
			else
				speed *= downLimit;
			// Lowers speed slightly when near bottom to prevent damage - May be unnecessary
			if (getRotation() < lowSpeedEpsilon && speed < -lowLimit) {
				speed = -lowLimit;
			}
			// Lowers speed slightly when near top to prevent damage - May be unnecessary
			if (getRotation() > (maxHeight - lowSpeedEpsilon) && speed > lowLimit) {
				speed = lowLimit;
			}
			// If PID control is complete, set motor speeds based on desired target speed
			if (autoDone) {
				rightElevator.set(speed);
				leftElevator.set(-speed);
			}
			
			// Output data
			Dashboard.getInstance().putNumber(false, "Elevator.Speed", speed);
			Dashboard.getInstance().putNumber(false, "Elevator.Position", getRotation());
		}
	}

	/**
	 * Use PID to go to target level
	 * 
	 * @param pos Target level
	 */
	public void gotoPosition(double pos) {
		autoDone = false;
		if (pos < 0)
			pos = 0;
		targetLevel = pos;
		Dashboard.getInstance().putNumber(false, "Elevator.PID.Target", targetLevel);
		rightElevator.getPIDController().setReference(targetLevel, ControlType.kPosition);
	}

	/**
	 * Manually set elevator speed for when not using PID
	 * 
	 * @param speed Elevator speed
	 */
	public void setElevatorSpeed(double speed) {
		elevatorSpeed = speed;
	}
	
	public double getLeftElevatorSpeed() {
		return leftElevator.get();
	}

	public double getRightElevatorSpeed() {
		return rightElevator.get();
	}

	/**
	 * Gets current elevator position in rotations
	 * 
	 * @return Current elevator position
	 */
	public double getRotation() {
		double right = rightElevator.getEncoder().getPosition();
		//double left = -leftElevator.getEncoder().getPosition();
		return right;
	}

	/**
	 * Use PID to go to target preset level
	 * 
	 * @param targetLevel Target preset level
	 */
	public void setLevel(Levels targetLevel) {
		if (targetLevel == null)
			return;
		SmartDashboard.putString("Elevator.Level", targetLevel.toString());
		gotoPosition(targetLevel.getRotations());
	}
	
	/**
	 * Gets whether or not autonomous PID control is finished
	 * 
	 * @return True if autonomous is finished
	 */
	public boolean isAutoDone() {
		return autoDone;
	}

	/**
	 * Gets current preset level if within acceptable range
	 * 
	 * @return Current preset level
	 */
	public Levels getCurrentLevel() {
		double currentMotorRotations = getRotation();
		for (Levels level : Levels.values()) {
			if (Math.abs(currentMotorRotations - level.getRotations()) < rotationsEpsilon) {
				return level;
			}
		}
		return null;
	}

	/**
	 * Gets next preset hatch level
	 * 
	 * @return Next preset hatch level
	 */
	public Levels getHatchLevelUp() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (Levels level : Levels.getHatchLevels()) {
			if ((currentMotorRotations + rotationsEpsilon) < level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	/**
	 * Gets last preset hatch level
	 * 
	 * @return Last preset hatch level
	 */
	public Levels getHatchLevelDown() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (int i = Levels.getHatchLevels().length - 1; i >= 0; i--) {
			Levels level = Levels.get(LevelType.HATCH, i);
			if ((currentMotorRotations - rotationsEpsilon) > level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	/**
	 * Gets next preset ball level
	 * 
	 * @return Next preset ball level
	 */
	public Levels getBallLevelUp() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (Levels level : Levels.getBallLevels()) {
			if ((currentMotorRotations + rotationsEpsilon) < level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	/**
	 * Gets last preset ball level
	 * 
	 * @return Last preset ball level
	 */
	public Levels getBallLevelDown() {
		double currentMotorRotations = getRotation();
		if (!autoDone)
			currentMotorRotations = targetLevel;
		for (int i = Levels.getBallLevels().length - 1; i >= 0; i--) {
			Levels level = Levels.get(LevelType.BALL, i);
			if ((currentMotorRotations - rotationsEpsilon) > level.getRotations()) {
				return level;
			}
		}
		return null;
	}

	/**
	 * Moves elevator to next hatch level
	 */
	public void incrementHatchLevel() {
		setLevel(getHatchLevelUp());
	}

	/**
	 * Moves elevator to last hatch level
	 */
	public void decrementHatchLevel() {
		setLevel(getHatchLevelDown());
	}

	/**
	 * Moves elevator to next ball level
	 */
	public void incrementBallLevel() {
		setLevel(getBallLevelUp());
	}

	/**
	 * Moves elevator to last ball level
	 */
	public void decrementBallLevel() {
		setLevel(getBallLevelDown());
	}

	public enum LevelType {
		HATCH, BALL, OTHER;
	}
	
	public enum Levels {
		// KEEP IN ORDER OF ROTATION COUNTS
		ZERO(0.0),
		BALL_INTAKE(LevelType.BALL, 28.3),
		HATCH_ONE(LevelType.HATCH, 98),
		BALL_ONE(LevelType.BALL, 138.55),
		CLIMB(150),
		BALL_CARGO_BAY(LevelType.BALL, 185.5),
		HATCH_TWO(LevelType.HATCH, 250),
		BALL_TWO(LevelType.BALL, 283.7),
		HATCH_THREE(LevelType.HATCH, 394),
		BALL_THREE(LevelType.BALL, 341);

		private LevelType type;
		private double rotations;

		// KEEP IN ORDER OF ROTATION COUNTS
		private static Levels[] hatchLevels = new Levels[] { ZERO, HATCH_ONE, HATCH_TWO, HATCH_THREE };
		private static Levels[] ballLevels = new Levels[] { ZERO, BALL_INTAKE, BALL_ONE, BALL_CARGO_BAY, BALL_TWO,
				BALL_THREE };
		private static Levels[] otherLevels = new Levels[] { ZERO, CLIMB };

		Levels(double rotations) {
			this.type = LevelType.OTHER;
			this.rotations = rotations;
		}

		Levels(LevelType type, double rotations) {
			this.type = type;
			this.rotations = rotations;
		}

		/**
		 * Gets preset level with specified {@link LevelType} and index
		 * 
		 * @param type Preset type
		 * @param level Index of preset type
		 * 
		 * @return Preset level
		 */
		public static Levels get(LevelType type, int level) {
			if (type == LevelType.HATCH)
				return hatchLevels[level];
			else if (type == LevelType.BALL)
				return ballLevels[level];
			else if (type == LevelType.OTHER)
				return otherLevels[level];
			return null;
		}

		/**
		 * Gets preset level with specified index
		 * 
		 * @param level Preset index
		 * 
		 * @return Preset level
		 */
		public static Levels get(int level) {
			return values()[level];
		}

		/**
		 * Gets all hatch levels
		 * 
		 * @return Array of hatch levels
		 */
		public static Levels[] getHatchLevels() {
			return hatchLevels;
		}

		/**
		 * Gets all ball levels
		 * 
		 * @return Array of ball levels
		 */
		public static Levels[] getBallLevels() {
			return ballLevels;
		}

		/**
		 * Gets all other levels
		 * 
		 * @return Array of other levels
		 */
		public static Levels[] getOtherLevels() {
			return otherLevels;
		}

		/**
		 * Gets rotation target of preset level
		 * 
		 * @return Rotation target
		 */
		public double getRotations() {
			return rotations;
		}

		/**
		 * Gets type of preset level
		 * 
		 * @return {@link LevelType} of preset
		 */
		public LevelType getType() {
			return type;
		}
	}
}
