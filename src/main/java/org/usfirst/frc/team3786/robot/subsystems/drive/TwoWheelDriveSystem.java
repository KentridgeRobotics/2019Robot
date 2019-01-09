package org.usfirst.frc.team3786.robot.subsystems.drive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class TwoWheelDriveSystem extends Subsystem implements DriveSubsystem {

	private WPI_TalonSRX left;
	private WPI_TalonSRX right;

	private DifferentialDrive differentialDrive;
	
	public TwoWheelDriveSystem() {
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

	public void gyroAssistedDrive(double x, double y, double turnRate) {
		differentialDrive.arcadeDrive(y, turnRate * 0.75);
	}
}
