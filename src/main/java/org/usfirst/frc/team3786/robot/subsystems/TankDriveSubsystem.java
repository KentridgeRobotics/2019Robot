package org.usfirst.frc.team3786.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

import org.usfirst.frc.team3786.robot.Dashboard;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class TankDriveSubsystem extends Subsystem {

	private static TankDriveSubsystem instance;

	private WPI_TalonSRX left;
	private WPI_TalonSRX right;

	private DifferentialDrive differentialDrive;

	private boolean boost = false;
	private boolean brake = false;

	public static TankDriveSubsystem getInstance() {
		if (instance == null)
			instance = new TankDriveSubsystem();
		return instance;
	}

	public TankDriveSubsystem() {
		left = new WPI_TalonSRX(Mappings.leftMotor);
		right = new WPI_TalonSRX(Mappings.rightMotor);

		left.configOpenloopRamp(0.3, 0);
		right.configOpenloopRamp(0.3, 0);

		differentialDrive = new DifferentialDrive(left, right);
	}

	public void setMotorSpeeds(double leftSpeed, double rightSpeed) {
		left.set(leftSpeed);
		right.set(rightSpeed);
		Dashboard.getInstance().putNumber("Left Motor Speed", leftSpeed);
		Dashboard.getInstance().putNumber("Right Motor Speed", rightSpeed);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void arcadeDrive(double speed, double turnRate) {
		if(this.brake == true) {
			speed *= 0.0;
			turnRate *= 0.0;
		}

		if(this.boost == true) {
			speed *= 2.0;
			turnRate *= 2.0;
		} else {
			speed *= 1.0;
			turnRate *= 1.0;
		}

		differentialDrive.arcadeDrive(speed, turnRate);
	}

	public void setBrake(boolean brake) {
		this.brake = brake;
		if (this.brake) {
			left.setNeutralMode(NeutralMode.Brake);
			right.setNeutralMode(NeutralMode.Brake);
		} else {
			left.setNeutralMode(NeutralMode.Coast);
			right.setNeutralMode(NeutralMode.Coast);
		}
	}

	public void setBoost(boolean boost) {
		this.boost = boost;
	}

	public boolean getBoost() {
		return this.boost;
	}
}
