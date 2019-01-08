/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorController;
import org.usfirst.frc.team3786.robot.subsystems.drive.DriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

	public static Robot instance;

	private UsbCamera camera;
	public Gyroscope gyro;

	private DriveSubsystem driveSubsystem;

	private int driverStationNumber;
	
	@Override
	public void robotInit() {
		Mappings.setupDefaultMappings();
		driverStationNumber = DriverStation.getInstance().getLocation();

		camera = CameraServer.getInstance().startAutomaticCapture();
		if (camera != null) {
			camera.setResolution(320, 240);
			camera.setFPS(30);
			camera.setWhiteBalanceManual(5000);
			camera.setBrightness(50);
			camera.setExposureManual(50);
		}
		gyro = Gyroscope.getInstance();
	}

	@Override
	public void robotPeriodic() {
	}

	/**
	 * Disabled
	 */
	@Override
	public void disabledInit() {
		Mappings.setupTestMappings();
	}

	@Override
	public void disabledPeriodic() {
	}

	/**
	 * Teleoperated
	 */
	@Override
	public void teleopInit() {
		DebugMotorController.getInstance().execute();
	}

	@Override
	public void teleopPeriodic() {
	}

	/**
	 * Autonomous
	 */
	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * Test
	 */
	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
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
