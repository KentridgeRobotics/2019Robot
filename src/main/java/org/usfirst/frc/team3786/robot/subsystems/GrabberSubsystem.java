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
		grabber.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.Disabled);
		//grabber.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
		//grabber.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
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

}
