package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Mappings;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.Dashboard;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class NeoDriveSubsystem extends Subsystem {

	private static NeoDriveSubsystem instance;

	private CANSparkMax left;
	private CANSparkMax right;
	private CANSparkMax leftSlave;
	private CANSparkMax rightSlave;

	private DifferentialDrive differentialDrive;

	private boolean boost = false;
	private boolean kkBrake = false;

	public static NeoDriveSubsystem getInstance() {
		if (instance == null)
			instance = new NeoDriveSubsystem();
		return instance;
	}

	public NeoDriveSubsystem() {
		left = new CANSparkMax(Mappings.left1Motor, MotorType.kBrushless);
		right = new CANSparkMax(Mappings.right1Motor, MotorType.kBrushless);
		leftSlave = new CANSparkMax(Mappings.leftSlaveMotor, MotorType.kBrushless);
		rightSlave = new CANSparkMax(Mappings.rightSlaveMotor, MotorType.kBrushless);
		left.setSmartCurrentLimit(40);
		right.setSmartCurrentLimit(40);
		leftSlave.setSmartCurrentLimit(40);
		rightSlave.setSmartCurrentLimit(40);
		left.setOpenLoopRampRate(0.1);
		right.setOpenLoopRampRate(0.1);
		leftSlave.setOpenLoopRampRate(0.1);
		rightSlave.setOpenLoopRampRate(0.1);

		leftSlave.follow(left);
		rightSlave.follow(right);

		differentialDrive = new DifferentialDrive(left, right);
	}

	public void setMotorSpeeds(double leftSpeed, double rightSpeed) {
		left.set(leftSpeed);
		right.set(-rightSpeed);
		Dashboard.getInstance().putNumber(false, "Left Motor Speed", leftSpeed);
		Dashboard.getInstance().putNumber(false, "Right Motor Speed", rightSpeed);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void arcadeDrive(double speed, double turnRate) {
		if (this.kkBrake) {
			speed *= 0.0;
			turnRate *= 0.0;
		} else if (!this.boost) {
			speed *= 0.75;
			turnRate *= 0.75;
		}
		Dashboard.getInstance().putBoolean(true, "Boost", boost);
		Dashboard.getInstance().putBoolean(true, "Break", kkBrake);
		Dashboard.getInstance().putNumber(false, "Driving Speed", speed);
		Dashboard.getInstance().putNumber(false, "TurnRate", turnRate);

		differentialDrive.arcadeDrive(-speed, turnRate);
	}

	public void setkkBrake(boolean kBrake) {
		this.kkBrake = kBrake;
		if (this.kkBrake) {
			left.setIdleMode(IdleMode.kBrake);
			right.setIdleMode(IdleMode.kBrake);
			leftSlave.setIdleMode(IdleMode.kBrake);
			rightSlave.setIdleMode(IdleMode.kBrake);
		} else {
			left.setIdleMode(IdleMode.kCoast);
			right.setIdleMode(IdleMode.kCoast);
			leftSlave.setIdleMode(IdleMode.kCoast);
			rightSlave.setIdleMode(IdleMode.kCoast);
		}
	}

	public void setBoost(boolean boost) {
		this.boost = boost;
	}

	public boolean getBoost() {
		return this.boost;
	}

	public void gyroStraight(double spd, double tgtHeading) {
		double currHeading = Gyroscope.getInstance().getHeadingContinuous();
		double error = tgtHeading - currHeading;
		double correction = error / 45;
		if (correction > 1.0) {
			correction = 1.0;
		}
		else if (correction < -1.0) {
			correction = -1.0;
		}
		arcadeDrive(spd, correction);
	}
}
