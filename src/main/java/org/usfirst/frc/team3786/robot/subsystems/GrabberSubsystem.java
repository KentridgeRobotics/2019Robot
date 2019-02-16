package org.usfirst.frc.team3786.robot.subsystems;

import org.usfirst.frc.team3786.robot.Dashboard;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

public class GrabberSubsystem extends Subsystem {

	private static GrabberSubsystem instance;

	private WPI_TalonSRX grabber;
	private WPI_TalonSRX rightFlinger;
	private WPI_TalonSRX leftFlinger;
	private WPI_TalonSRX tilt;

	public static GrabberSubsystem getInstance() {
		if (instance == null)
			instance = new GrabberSubsystem();
		return instance;
	}

	public GrabberSubsystem() {
		grabber = new WPI_TalonSRX(Mappings.grabberMotor);
		grabber.setSafetyEnabled(false);
		grabber.configPeakCurrentLimit(20);

		rightFlinger = new WPI_TalonSRX(Mappings.rightFlinger);
		rightFlinger.setSafetyEnabled(false);
		rightFlinger.configPeakCurrentLimit(20);

		leftFlinger = new WPI_TalonSRX(Mappings.leftFlinger);
		leftFlinger.setSafetyEnabled(false);
		leftFlinger.configPeakCurrentLimit(20);

		tilt = new WPI_TalonSRX(Mappings.tilt);
		tilt.setSafetyEnabled(false);
		tilt.configPeakCurrentLimit(20);
	}

	public double getGrabberCurrent()
	{
		return grabber.getOutputCurrent();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setGrabberSpeed(double speed) {
		grabber.set(speed);
		Dashboard.getInstance().putNumber(false, "Grabber Speed", speed);
	}

	public void setFlingerSpeed(double speed) {
		rightFlinger.set(speed);
		leftFlinger.set(speed);
		Dashboard.getInstance().putNumber(true, "Flinger Speed", speed);
	}

	public void setTiltSpeed(double speed) {
		tilt.set(speed);
		Dashboard.getInstance().putNumber(false, "Tilter Speed", speed);
	}

	public void setBrake(boolean brake) {
		if(brake)
		{
			grabber.setNeutralMode(NeutralMode.Brake);
			rightFlinger.setNeutralMode(NeutralMode.Brake);
			leftFlinger.setNeutralMode(NeutralMode.Brake);
			tilt.setNeutralMode(NeutralMode.Brake);
		}
		else
		{
			grabber.setNeutralMode(NeutralMode.Coast);
			rightFlinger.setNeutralMode(NeutralMode.Coast);
			leftFlinger.setNeutralMode(NeutralMode.Coast);
			tilt.setNeutralMode(NeutralMode.Coast);
		}
	}

}
