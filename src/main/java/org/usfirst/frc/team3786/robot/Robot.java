/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.drive.NeoDriveCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorRunCommand;

import org.usfirst.frc.team3786.robot.commands.climber.ButtLifterRunCommand;
import org.usfirst.frc.team3786.robot.subsystems.ButtLifterSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.vision.Cameras;
import org.usfirst.frc.team3786.robot.subsystems.vision.RPiComs;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.utils.SharpIRSensor;
import org.usfirst.frc.team3786.robot.utils.UltrasonicSensor;
import org.usfirst.frc.team3786.robot.utils.HCSR04;
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

	public Gyroscope gyro = Gyroscope.getInstance();

	private int driverStationNumber = 0;

	public static final ButtLifterRunCommand buttLifterRunCommand = new ButtLifterRunCommand();
	public static final ElevatorRunCommand elevatorRunCommand = new ElevatorRunCommand();

	@Override
	public void robotInit() {
		new UltrasonicSensor(UltrasonicSensor.Side.LEFT, new SharpIRSensor(Mappings.irSensorLeft),
				new HCSR04(Mappings.ultrasonicLeft.getKey(), Mappings.ultrasonicLeft.getValue()));
		new UltrasonicSensor(UltrasonicSensor.Side.RIGHT, new SharpIRSensor(Mappings.irSensorRight),
				new HCSR04(Mappings.ultrasonicRight.getKey(), Mappings.ultrasonicRight.getValue()));
		Mappings.setupDefaultMappings();
		driverStationNumber = DriverStation.getInstance().getLocation();
		Cameras.setup();
		gyro = Gyroscope.getInstance();
		HCSR04.init();
		RPiComs.setup();
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
		gyro.run();
		double hcsDist0 = HCSR04.getInstance(0).getDistanceCM();
		double hcsDist1 = HCSR04.getInstance(1).getDistanceCM();
		double sharpIRDist0 = SharpIRSensor.getInstance(0).getDistanceCM();
		double sharpIRDist1 = SharpIRSensor.getInstance(1).getDistanceCM();
		SmartDashboard.putNumber("Distance.HCSR04L", hcsDist0);
		SmartDashboard.putNumber("Distance.HCSR04R", hcsDist1);
		SmartDashboard.putNumber("Distance.SharpIR.0", sharpIRDist0);
		SmartDashboard.putNumber("Distance.SharpIR.1", sharpIRDist1);
		ButtLifterSubsystem.getInstance().runIteration();
		ElevatorSubsystem.getInstance().runIteration();
		double[] gravity = Gyroscope.getInstance().getGravity();
		SmartDashboard.putNumberArray("Gyro Gravity", gravity);
	}

	/**
	 * Disabled
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
	}

	public void initDrive() {
		NeoDriveCommand.getInstance().start();
		buttLifterRunCommand.start();
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
		NeoDriveCommand.getInstance().cancel();
		buttLifterRunCommand.cancel();
		elevatorRunCommand.cancel();
	}

	@Override
	public void testPeriodic() {
	}

	public int getDriveStationNumber() {
		return driverStationNumber;
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
