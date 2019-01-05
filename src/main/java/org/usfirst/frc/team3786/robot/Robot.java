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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;

import org.usfirst.frc.team3786.robot.DriveSystem.TwoWheelDriveSystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

	private SwitchSide[] switchSides = new SwitchSide[3];

	public static Robot instance;

	private UsbCamera camera;
	public Gyroscope gyro;

	private TwoWheelDriveSystem driveSystem;

	private int driverStationNumber;
	private String gameSpecificMessage;
	
	@Override
	public void robotInit() {
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
		RobotMap.controllerMappings();
		LED.setRGB(60, 0, 0);
		gameSpecificMessage = null;
	}

	@Override
	public void disabledPeriodic() {
		// Set the LED RGB
		gameSpecificMessage = DriverStation.getInstance().getGameSpecificMessage();
		if(gameSpecificMessage != null) {
			if(gameSpecificMessage.length() == 3) {
				char[] splitMessage = gameSpecificMessage.toCharArray();
				if(splitMessage.length == 3) {
					for(int i = 0; i < 3; i++) {
						if(splitMessage[i] == 'L') {
							switchSides[i] = SwitchSide.LEFT;
						} else if(splitMessage[i] == 'R') {
							switchSides[i] = SwitchSide.RIGHT;
						} else {
							switchSides[i] = null;
							break;
						}
					}
				}
			}
		}
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
		String gameSpecificMessage = DriverStation.getInstance().getGameSpecificMessage();
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

	public String getGameSpecificMessage() {
		return gameSpecificMessage;
	}

	public SwitchSide[] getSwitchSides() {
		return this.switchSides;
	}

	public enum SwitchSide {
		LEFT(), RIGHT();
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
