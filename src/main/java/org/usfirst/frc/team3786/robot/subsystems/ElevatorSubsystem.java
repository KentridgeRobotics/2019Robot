package org.usfirst.frc.team3786.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ElevatorSubsystem extends Subsystem {

	private static ElevatorSubsystem instance;

	private WPI_TalonSRX elevatorMotor;
	private WPI_TalonSRX tiltMotor;

	public static ElevatorSubsystem getInstance() {
		if (instance == null)
			instance = new ElevatorSubsystem();
		return instance;
	}

	public ElevatorSubsystem() {
		elevatorMotor = new WPI_TalonSRX(Mappings.elevatorMotor);
		elevatorMotor.setNeutralMode(NeutralMode.Brake);
		tiltMotor = new WPI_TalonSRX(Mappings.tiltMotor);
	}

	@Override
	public void initDefaultCommand() {
	}

	public void setElevatorSpeed(double speed) {
		elevatorMotor.set(speed);
	}

	public void setTiltSpeed(double speed) {
		tiltMotor.set(speed);
	}
}
