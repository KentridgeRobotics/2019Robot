package org.usfirst.frc.team3786.robot.subsystems;

import org.usfirst.frc.team3786.robot.Dashboard;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

public class GrabberTiltSubsystem extends Subsystem {

	private static GrabberTiltSubsystem instance;

	private WPI_TalonSRX tilt;

	public static GrabberTiltSubsystem getInstance() {
		if (instance == null)
			instance = new GrabberTiltSubsystem();
		return instance;
	}

	public GrabberTiltSubsystem() {
		tilt = new WPI_TalonSRX(Mappings.grabberTiltMotor);
		tilt.setSafetyEnabled(false);
		tilt.configPeakCurrentLimit(40);
		tilt.configContinuousCurrentLimit(30);
		tilt.setNeutralMode(NeutralMode.Coast);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setTiltSpeed(double speed) {
		tilt.set(-speed);
		Dashboard.getInstance().putNumber(false, "Grabber Tilt Speed", speed);
	}

}
