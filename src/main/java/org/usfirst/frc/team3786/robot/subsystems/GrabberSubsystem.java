package org.usfirst.frc.team3786.robot.subsystems;

import org.usfirst.frc.team3786.robot.Dashboard;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

public class GrabberSubsystem extends Subsystem {

	private static GrabberSubsystem instance;

	private WPI_TalonSRX grabber;
	private WPI_TalonSRX gripper;
	private WPI_TalonSRX tilt;

	public static GrabberSubsystem getInstance() {
		if (instance == null)
			instance = new GrabberSubsystem();
		return instance;
	}

	public GrabberSubsystem() {
		grabber = new WPI_TalonSRX(Mappings.grabberMotor);
		grabber.setSafetyEnabled(false);
		grabber.configPeakCurrentLimit(30);
		grabber.setNeutralMode(NeutralMode.Brake);
		grabber.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
		grabber.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

		gripper = new WPI_TalonSRX(Mappings.gripper);
		gripper.setSafetyEnabled(false);
		gripper.configPeakCurrentLimit(20);

		tilt = new WPI_TalonSRX(Mappings.tilt);
		tilt.setSafetyEnabled(false);
		tilt.configPeakCurrentLimit(40);
		tilt.configContinuousCurrentLimit(30);
		tilt.setNeutralMode(NeutralMode.Brake);
	}

	public double getGrabberCurrent() {
		return grabber.getOutputCurrent();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setGrabberSpeed(double speed) {
		grabber.set(-speed);
		Dashboard.getInstance().putNumber(false, "Grabber Speed", speed);
	}

	public void setGripperSpeed(double speed) {
		gripper.set(-speed);
		Dashboard.getInstance().putNumber(true, "Gripper Speed", speed);
	}

	public void setTiltSpeed(double speed) {
		tilt.set(-speed);
		Dashboard.getInstance().putNumber(false, "Tilter Speed", speed);
	}

	public void setBrake(boolean brake) {
		if (brake) {
			gripper.setNeutralMode(NeutralMode.Brake);
		} else {
			gripper.setNeutralMode(NeutralMode.Coast);
		}
	}

}
