package org.usfirst.frc.team3786.robot.subsystems;

import org.usfirst.frc.team3786.robot.Dashboard;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;
import org.usfirst.frc.team3786.robot.NumConstants;

public class GrabberGripperSubsystem extends Subsystem {

	private static GrabberGripperSubsystem instance;

	private WPI_TalonSRX gripper;

	public static GrabberGripperSubsystem getInstance() {
		if (instance == null)
			instance = new GrabberGripperSubsystem();
		return instance;
	}

	public GrabberGripperSubsystem() {
		gripper = new WPI_TalonSRX(Mappings.grabberGripperMotor);
		gripper.setSafetyEnabled(false);
		gripper.setNeutralMode(NeutralMode.Brake);
		gripper.configPeakCurrentLimit(NumConstants.GRIPPER_AMP_LIMIT);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setGripperSpeed(double speed) {
		gripper.set(-speed);
		Dashboard.getInstance().putNumber(true, "Grabber Gripper Speed", speed);
	}

	public double getGripperAmps() {
		double gripperAmps = gripper.getOutputCurrent();
		Dashboard.getInstance().putNumber(false, "Gripper Current", gripperAmps);
		return gripperAmps;
	}

}
