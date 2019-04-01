/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ButtLifterSubsystem extends Subsystem {
	private static ButtLifterSubsystem instance;

	private WPI_TalonSRX lifter;

	// PID values
	public static double kP = 0.5;
	public static double kI = 0;
	public static int kIZone = 0;
	public static double kD = 0.1;
	public static double kF = 0;

	// Target butt lifter speed when driving manually
	private double lifterSpeed = 0;

	// Is PID control finished
	private boolean autoDone = true;
	// Target PID level
	private double targetLevel = 0;

	// Robot cycles where PID error has not changed
	private int pidCycleCount = 0;
	// Previous PID error to compare against to tell when PID is finished
	private double lastPidError = 0;

	public static ButtLifterSubsystem getInstance() {
		if (instance == null)
			instance = new ButtLifterSubsystem();
		return instance;
	}

	public ButtLifterSubsystem() {
		lifter = new WPI_TalonSRX(Mappings.buttLifterMotor);
		lifter.setNeutralMode(NeutralMode.Brake);
		lifter.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
		lifter.configClearPositionOnLimitR(true, 10);
	}

	/**
	 * Checks network tables for PID value updates
	 */
	private void checkNetworkTables() {
		double dP = SmartDashboard.getNumber("ButtLifter.PID.P", -1);
		double dI = SmartDashboard.getNumber("ButtLifter.PID.I", 0.0);
		int dIZone = (int) SmartDashboard.getNumber("ButtLifter.PID.IZone", 0.0);
		double dD = SmartDashboard.getNumber("ButtLifter.PID.D", 0.0);
		double dF = SmartDashboard.getNumber("ButtLifter.PID.FF", 0.0);
		// Data is not present on network tables, push default values
		if (dP == -1) {
			SmartDashboard.putNumber("ButtLifter.PID.P", kP);
			SmartDashboard.putNumber("ButtLifter.PID.I", kI);
			SmartDashboard.putNumber("ButtLifter.PID.IZone", kIZone);
			SmartDashboard.putNumber("ButtLifter.PID.D", kD);
			SmartDashboard.putNumber("ButtLifter.PID.F", kF);
			dP = 0.0;
		}
		if (dP != kP) {
			kP = dP;
			lifter.config_kP(0, kP);
		}
		if (dI != kI) {
			kI = dI;
			lifter.config_kI(0, kI);
		}
		if (dIZone != kIZone) {
			kIZone = dIZone;
			lifter.config_IntegralZone(0, kIZone);
		}
		if (dD != kD) {
			kD = dD;
			lifter.config_kD(0, kD);
		}
		if (dF != kF) {
			kF = dF;
			lifter.config_kF(0, kF);
		}
	}

	/**
	 * Run each robot cycle for vital control checks and data input/output
	 */
	public void runIteration() {
		// Check network tables for PID value updates
		checkNetworkTables();
		
		// Calculate PID error and end auto if error does not change
		double error = targetLevel - getRotation();
		SmartDashboard.putNumber("ButtLifter.PID.Error", error);
		if (!autoDone) {
			if (Math.abs(error - lastPidError) < 0.05) {
				pidCycleCount++;
				if (pidCycleCount >= 10) {
					autoDone = true;
				}
			} else {
				pidCycleCount = 0;
				lastPidError = error;
			}
		}
		
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		// !!! REMOVE THIS IF USING ENTIRELY PID CONTROL FOR LIFT !!!
		// VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
		
		// If PID control is complete, set motor speeds based on desired target speed
		double speed = lifterSpeed;
		if (autoDone)
			lifter.set(lifterSpeed);
		
		// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		// !!! REMOVE THIS IF USING ENTIRELY PID CONTROL FOR LIFT !!!
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		// Output data
		Dashboard.getInstance().putNumber(false, "ButtLifter.Speed", speed);
		Dashboard.getInstance().putNumber(false, "ButtLifter.Position", getRotation());
	}

	/**
	 * Use PID to go to target level
	 * 
	 * @param pos Target level
	 */
	public void gotoPosition(double pos) {
		lifter.set(ControlMode.Position, pos);
	}

	/**
	 * Use PID to go to target preset level
	 * 
	 * @param pos Target preset level
	 */
	public void setLevel(Levels level) {
		Dashboard.getInstance().putString(false, "ButtLifter.Level", level.toString());
		gotoPosition(level.getRotations());
	}

	/**
	 * Manually set butt lifter speed for when not using PID
	 * 
	 * @param speed Butt lifter speed
	 */
	public void setSpeed(double speed) {
		lifterSpeed = speed;
	}
	
	/**
	 * Gets whether or not autonomous PID control is finished
	 * 
	 * @return True if autonomous is finished
	 */
	public boolean isAutoDone() {
		return autoDone;
	}
	
	/**
	 * Gets current butt lifter position in rotations
	 * 
	 * @return Current butt lifter position
	 */
	public int getRotation() {
		return lifter.getSelectedSensorPosition();
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public enum Levels {
		STOWED(-120),
		LEVEL_2(-100),
		LEVEL_3(0);

		private double rotations;

		Levels(double rotations) {
			this.rotations = rotations;
		}

		/**
		 * Gets rotation target of preset level
		 * 
		 * @return Rotation target
		 */
		public double getRotations() {
			return rotations;
		}
	}
}
