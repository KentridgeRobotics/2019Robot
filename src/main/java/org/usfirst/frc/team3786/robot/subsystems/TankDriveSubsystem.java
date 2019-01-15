package org.usfirst.frc.team3786.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class TankDriveSubsystem extends Subsystem {

	private static TankDriveSubsystem instance;

	private WPI_TalonSRX left;
	private WPI_TalonSRX right;

	private DifferentialDrive differentialDrive;

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
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void arcadeDrive(double speed, double turnRate) {
		differentialDrive.arcadeDrive(speed, turnRate);
	}

	public void setBrake(boolean brake) {
		if (brake) {
			left.setNeutralMode(NeutralMode.Brake);
			right.setNeutralMode(NeutralMode.Brake);
		} else {
			left.setNeutralMode(NeutralMode.Coast);
			right.setNeutralMode(NeutralMode.Coast);
		}
	}
}
