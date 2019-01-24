/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.drive.TankDriveCommand;

import java.awt.Color;

import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorController;
import org.usfirst.frc.team3786.robot.subsystems.TankDriveSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.vision.Cameras;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.utils.LED;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

	/**
	 * DO NOT MODIFY
	 */
	public static Robot instance;

	public static final RobotMode mode = RobotMode.TANK;

	public Gyroscope gyro = null;

	private int driverStationNumber = 0;

	private static final Color visionColor = new Color(0, 255, 0);
	private static final Color idleColor = new Color(0, 0, 0);
	private static byte brightness = (byte) 255;

	@Override
	public void robotInit() {
		if (mode == RobotMode.TANK) {
			System.out.println("USING TANK DRIVE");
			Mappings.setupDefaultMappings();
		} else if (mode == RobotMode.DEBUG) {
			System.out.println("USING DEBUG DRIVE");
			Mappings.setupTestMappings();
		}
		driverStationNumber = DriverStation.getInstance().getLocation();
		Cameras.setup();
		gyro = Gyroscope.getInstance();
		LED.setup();
		LED.setColor(idleColor);
		SmartDashboard.putNumber("LED.BRIGHTNESS", 255);
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
		Cameras.run();
		byte bright = (byte) SmartDashboard.getNumber("LED.BRIGHTNESS", 0);
		if (brightness != bright) {
			brightness = bright;
			LED.setBrightness(bright);
		}
	}

	/**
	 * Disabled
	 */
	@Override
	public void disabledInit() {
		if (mode == RobotMode.TANK)
			TankDriveCommand.getInstance().cancel();
		else if (mode == RobotMode.DEBUG)
			DebugMotorController.getInstance().cancel();
			LED.setColor(idleColor);
	}

	@Override
	public void disabledPeriodic() {
	}

	/**
	 * Teleoperated
	 */
	@Override
	public void teleopInit() {
		if (mode == RobotMode.TANK)
			TankDriveCommand.getInstance().start();
		else if (mode == RobotMode.DEBUG)
			DebugMotorController.getInstance().start();
			LED.setColor(visionColor);
	}

	@Override
	public void teleopPeriodic() {
	}

	/**
	 * Autonomous
	 */
	@Override
	public void autonomousInit() {
		if (mode == RobotMode.TANK)
			TankDriveCommand.getInstance().cancel();
		else if (mode == RobotMode.DEBUG)
			DebugMotorController.getInstance().cancel();
			LED.setColor(visionColor);
	}

	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * Test
	 */
	@Override
	public void testInit() {
		if (mode == RobotMode.TANK)
			TankDriveCommand.getInstance().cancel();
		else if (mode == RobotMode.DEBUG)
			DebugMotorController.getInstance().cancel();
			LED.setColor(visionColor);
	}

	@Override
	public void testPeriodic() {
	}

	public int getDriveStationNumber() {
		return driverStationNumber;
	}

	public enum RobotMode {
		TANK, DEBUG;
	}

	/**
	 * DO NOT MODIFY
	 */
	private Robot() {
		Robot.instance = this;
	}

	/**
	 * DO NOT MODIFY
	 */
	public static void main(String... args) {
		RobotBase.startRobot(Robot::new);
	}

}
