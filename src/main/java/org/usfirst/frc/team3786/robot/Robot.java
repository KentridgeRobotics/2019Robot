/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.utils.LED;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
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

	@Override
	public void robotInit() {
		camera = CameraServer.getInstance().startAutomaticCapture();
		if (camera != null) {
			camera.setResolution(320, 240);
			camera.setFPS(30);
			camera.setWhiteBalanceManual(5000);
			camera.setBrightness(50);
			camera.setExposureManual(50);
		}
		gyro = Gyroscope.getInstance();
		LED.setRGB(60, 0, 0);
	}

	@Override
	public void robotPeriodic() {
	}

	/**
	 * Disabled
	 */
	@Override
	public void disabledInit() {
		LED.setRGB(60, 0, 0);
	}

	@Override
	public void disabledPeriodic() {
	}

	/**
	 * Teleoperated
	 */
	@Override
	public void teleopInit() {
		LED.setBrightness(0.25f);
		LED.setHue(0);
	}

	@Override
	public void teleopPeriodic() {
		LED.colorCyclePeriodic();
	}

	/**
	 * Autonomous
	 */
	@Override
	public void autonomousInit() {
		LED.setRGB(0, 255, 0);
	}

	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * Test
	 */
	@Override
	public void testInit() {
		LED.setRGB(60, 0, 70);
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
