package org.usfirst.frc.team3786.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

public class GrabberSubsystem extends Subsystem {

	private static GrabberSubsystem instance;

	private WPI_TalonSRX grabber;
	private WPI_TalonSRX flinger;

	public static GrabberSubsystem getInstance() {
		if (instance == null)
			instance = new GrabberSubsystem();
		return instance;
	}

	public GrabberSubsystem() {
		grabber = new WPI_TalonSRX(Mappings.grabberMotor);
		flinger = new WPI_TalonSRX(Mappings.flingerMotor);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setGrabberSpeed(double speed) {
		grabber.set(speed);
	}

	public void setFlingerSpeed(double speed) {
		flinger.set(speed);
	}

}
