/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.drive.NeoDriveCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorRunCommand;

import java.awt.Color;

import org.usfirst.frc.team3786.robot.commands.climber.ButtLifterRunCommand;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorController;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.vision.Cameras;
import org.usfirst.frc.team3786.robot.subsystems.vision.RPiComs;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.utils.SharpIRSensor;
import org.usfirst.frc.team3786.robot.utils.UltrasonicSensor;
import org.usfirst.frc.team3786.robot.utils.HCSR04;
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

	public Gyroscope gyro = Gyroscope.getInstance();

	private int driverStationNumber = 0;

	private static final Color visionColor = new Color(0, 255, 0);
	private static final Color idleColor = new Color(0, 0, 0);
	private static byte brightness = (byte) 255;

	public static final ButtLifterRunCommand buttLifterRunCommand = new ButtLifterRunCommand();
	public static final ElevatorRunCommand elevatorRunCommand = new ElevatorRunCommand();

	@Override
	public void robotInit() {
		new UltrasonicSensor(UltrasonicSensor.Side.LEFT, new SharpIRSensor(Mappings.irSensorLeft),
				new HCSR04(Mappings.ultrasonicLeft.getKey(), Mappings.ultrasonicLeft.getValue()));
		new UltrasonicSensor(UltrasonicSensor.Side.RIGHT, new SharpIRSensor(Mappings.irSensorRight),
				new HCSR04(Mappings.ultrasonicRight.getKey(), Mappings.ultrasonicRight.getValue()));
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
		HCSR04.init();
		RPiComs.setup();
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
		gyro.run();
		byte bright = (byte) SmartDashboard.getNumber("LED.BRIGHTNESS", -1);
		if (bright == -1) {
			SmartDashboard.putNumber("LED.BRIGHTNESS", 255);
			bright = (byte) 255;
		}
		if (brightness != bright) {
			brightness = bright;
			LED.setBrightness(bright);
		}
		double hcsDist0 = HCSR04.getInstance(0).getDistanceCM();
		double hcsDist1 = HCSR04.getInstance(1).getDistanceCM();
		double sharpIRDist0 = SharpIRSensor.getInstance(0).getDistanceCM();
		double sharpIRDist1 = SharpIRSensor.getInstance(1).getDistanceCM();
		SmartDashboard.putNumber("Distance.HCSR04.0", hcsDist0);
		SmartDashboard.putNumber("Distance.HCSR04.1", hcsDist1);
		SmartDashboard.putNumber("Distance.SharpIR.0", sharpIRDist0);
		SmartDashboard.putNumber("Distance.SharpIR.1", sharpIRDist1);
		ElevatorSubsystem.getInstance().safetyRun();
	}

	/**
	 * Disabled
	 */
	@Override
	public void disabledInit() {
		LED.setColor(idleColor);
	}

	@Override
	public void disabledPeriodic() {
	}

	public void initDrive() {
		if (mode == RobotMode.TANK)
			NeoDriveCommand.getInstance().start();
		else if (mode == RobotMode.DEBUG)
			DebugMotorController.getInstance().start();
		LED.setColor(visionColor);
		// buttLifterRunCommand.start();
		elevatorRunCommand.start();
	}

	/**
	 * Teleoperated
	 */
	@Override
	public void teleopInit() {
		initDrive();
	}

	@Override
	public void teleopPeriodic() {
	}

	/**
	 * Autonomous
	 */
	@Override
	public void autonomousInit() {
		initDrive();
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
			NeoDriveCommand.getInstance().cancel();
		else if (mode == RobotMode.DEBUG)
			DebugMotorController.getInstance().cancel();
		LED.setColor(visionColor);
		buttLifterRunCommand.cancel();
		elevatorRunCommand.cancel();
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
