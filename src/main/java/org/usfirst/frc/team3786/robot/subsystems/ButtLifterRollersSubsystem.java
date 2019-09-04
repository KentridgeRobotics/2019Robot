/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ButtLifterRollersSubsystem extends Subsystem {
	private static ButtLifterRollersSubsystem instance;

	private WPI_TalonSRX rollers;

	public static ButtLifterRollersSubsystem getInstance() {
		if (instance == null)
			instance = new ButtLifterRollersSubsystem();
		return instance;
	}

	public ButtLifterRollersSubsystem() {
		rollers = new WPI_TalonSRX(Mappings.rollersMotor);
		rollers.setNeutralMode(NeutralMode.Brake);
		rollers.configOpenloopRamp(0.2);
	}

	/**
	 * Sets speed of butt lifter rollers
	 * 
	 * @param speed Roller speed
	 */
	public void setSpeed(double speed) {
		rollers.set(speed);
		Dashboard.getInstance().putNumber(false, "ButtLifter.RollerSpeed", speed);
	}

	/**
	 * Sets butt lifter roller brake mode
	 * 
	 * @param brake Brake mode
	 */
	public void setBrake(boolean brake) {
		if (brake) {
			rollers.setNeutralMode(NeutralMode.Brake);
		} else {
			rollers.setNeutralMode(NeutralMode.Coast);
		}
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
