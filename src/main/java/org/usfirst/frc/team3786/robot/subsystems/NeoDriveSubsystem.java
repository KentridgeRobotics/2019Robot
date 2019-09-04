package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;
import org.usfirst.frc.team3786.robot.NumConstants;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class NeoDriveSubsystem extends Subsystem {

	private static NeoDriveSubsystem instance;

	private CANSparkMax left;
	private CANSparkMax right;
	private CANSparkMax leftFollow;
	private CANSparkMax rightFollow;

	private DifferentialDrive differentialDrive;

	private boolean boost = false;
	private boolean kkBrake = false;
	private boolean slowTurn = false;

	public static NeoDriveSubsystem getInstance() {
		if (instance == null)
			instance = new NeoDriveSubsystem();
		return instance;
	}

	public NeoDriveSubsystem() {
		left = new CANSparkMax(Mappings.left1Motor, MotorType.kBrushless);
		left.setIdleMode(IdleMode.kCoast);
		right = new CANSparkMax(Mappings.right1Motor, MotorType.kBrushless);
		right.setIdleMode(IdleMode.kCoast);
		leftFollow = new CANSparkMax(Mappings.leftFollowMotor, MotorType.kBrushless);
		leftFollow.setIdleMode(IdleMode.kCoast);
		rightFollow = new CANSparkMax(Mappings.rightFollowMotor, MotorType.kBrushless);
		rightFollow.setIdleMode(IdleMode.kCoast);
		left.setSmartCurrentLimit(40);
		right.setSmartCurrentLimit(40);
		leftFollow.setSmartCurrentLimit(40);
		rightFollow.setSmartCurrentLimit(40);
		left.setOpenLoopRampRate(NumConstants.DEFAULT_DRIVE_RAMP_RATE);
		right.setOpenLoopRampRate(NumConstants.DEFAULT_DRIVE_RAMP_RATE);
		leftFollow.setOpenLoopRampRate(NumConstants.DEFAULT_DRIVE_RAMP_RATE);
		rightFollow.setOpenLoopRampRate(NumConstants.DEFAULT_DRIVE_RAMP_RATE);

		leftFollow.follow(left);
		rightFollow.follow(right);

		differentialDrive = new DifferentialDrive(left, right);
	}

	public void setMotorSpeeds(double leftSpeed, double rightSpeed) { //used for autonomous
		left.set(leftSpeed);
		right.set(-rightSpeed);
		Dashboard.getInstance().putNumber(false, "Left Motor Speed", leftSpeed);
		Dashboard.getInstance().putNumber(false, "Right Motor Speed", rightSpeed);
	}

	public void changeRampRate(double rate) {
		right.setOpenLoopRampRate(rate);
		rightFollow.setOpenLoopRampRate(rate);
		left.setOpenLoopRampRate(rate);
		leftFollow.setOpenLoopRampRate(rate);
	}

	public void initDefaultCommand() {
	}

	public void arcadeDrive(double speed, double turnRate) {
		if (this.kkBrake) {
			speed *= 0.0;
			turnRate *= 0.0;
		} else if (!this.boost) {
			if (this.slowTurn) {
				speed *= 0.4;
				turnRate *= 0.3;
			}
			else {
				speed *= 0.6;
				turnRate *= 0.7;
			}
		}
		Dashboard.getInstance().putBoolean(true, "Boost", boost);
		Dashboard.getInstance().putBoolean(true, "Break", kkBrake);
		Dashboard.getInstance().putNumber(false, "Driving Speed", speed);
		Dashboard.getInstance().putNumber(false, "TurnRate", turnRate);

		differentialDrive.arcadeDrive(-speed, turnRate);
	}

	public void tankDrive(double leftPower, double rightPower) {
		if (this.kkBrake) {
			leftPower *= 0.0;
			rightPower *= 0.0;
		} else if (!this.boost) {
			if (this.slowTurn) {
				leftPower *= 0.3;
				rightPower *= 0.3;
			}
			else {
				leftPower *= 0.6;
				rightPower *= 0.6; //throttle 0.6, turn, 0.7
			}
		}
		tankDrive(leftPower, rightPower);
	}

	public void setkkBrake(boolean kBrake) {
		this.kkBrake = kBrake;
		if (this.kkBrake) {
			left.setIdleMode(IdleMode.kBrake);
			right.setIdleMode(IdleMode.kBrake);
			leftFollow.setIdleMode(IdleMode.kBrake);
			rightFollow.setIdleMode(IdleMode.kBrake);
		} else {
			left.setIdleMode(IdleMode.kCoast);
			right.setIdleMode(IdleMode.kCoast);
			leftFollow.setIdleMode(IdleMode.kCoast);
			rightFollow.setIdleMode(IdleMode.kCoast);
		}
	}

	public void setBoost(boolean boost) {
		this.boost = boost;
	}

	public boolean getBoost() {
		return this.boost;
	}

	public void setSlowTurn(boolean slowTurn) {
		this.slowTurn = slowTurn;
	}

	public boolean getSlowTurn() {
		return this.slowTurn;
	}

	public void gyroStraight(double spd, double tgtHeading) {
		double currHeading = Gyroscope.getInstance().getHeadingContinuous();
		double error = tgtHeading - currHeading;
		double correction = error / 30;
		if(correction > 1.0) {
			correction = 1.0;
		}
		else if(correction < -1.0) {
			correction = -1.0;
		}
		arcadeDrive(spd, correction);
	}
}
