package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.grabber.GrabberCloseCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GripperInCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberOpenCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GripperOutCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.TiltDownCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.TiltUpCommand;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBoostCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBrakeCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorBallDownCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorBallUpCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorHatchDownCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorHatchUpCommand;
import org.usfirst.frc.team3786.robot.commands.climber.ManualButtLifterDownCommand;
import org.usfirst.frc.team3786.robot.commands.climber.ManualButtLifterUpCommand;
import org.usfirst.frc.team3786.robot.commands.climber.RollersBackwardCommand;
import org.usfirst.frc.team3786.robot.commands.climber.RollersForwardCommand;

import java.util.Map;

import org.usfirst.frc.team3786.robot.utils.XboxController;

public class Mappings {
	// Controller IDs
	public final static int primaryControllerId = 0;
	public final static int secondaryControllerId = 1;

	// CAN IDs
	public final static int left1Motor = 8;
	public final static int right1Motor = 9;
	public final static int leftFollowMotor = 12;
	public final static int rightFollowMotor = 13;

	public final static int rollersMotor = 3;
	public final static int buttLifterMotor = 4;

	public final static int grabberMotor = 10;
	public final static int grabberGripperMotor = 2;
	public final static int grabberTiltMotor = 5;

	public final static int leftElevatorMotor = 6;
	public final static int rightElevatorMotor = 7;

	// Digital IO
	public final static Map.Entry<Integer, Integer> ultrasonicLeft = Map.entry(0, 1);
	public final static Map.Entry<Integer, Integer> ultrasonicRight = Map.entry(2, 3);
	public final static int grabberLimitSwitch = 4;
	public final static int elevatorLimitSwitch = 5;
	public final static int buttlifterLimitSwitch = 6;

	// Analog IO
	public final static int irSensorLeft = 0;
	public final static int irSensorRight = 1;

	// Flash Drive Location for Data Storage
	public final static String dataStoragePath = "/home/lvuser";
	// public final static String dataStoragePath = "/media/sda1";
	public final static String gyroCalibrationFileName = "gyroCalibration.dat";

	public static void setupDefaultMappings() {

		XboxController primary = OI.getPrimaryController();
		NeoBoostCommand boostCommand = new NeoBoostCommand();
		primary.buttonA.whileHeld(new NeoBrakeCommand());
		primary.buttonB.whileHeld(boostCommand);
		primary.buttonStickLeft.whileHeld(boostCommand);
		primary.buttonBumperRight.whileHeld(new RollersForwardCommand()); //uncomment for testing 
		primary.buttonBumperLeft.whileHeld(new RollersBackwardCommand()); //uncomment for testing
		primary.buttonPovUp.whileHeld(new ManualButtLifterUpCommand());
		primary.buttonPovDown.whileHeld(new ManualButtLifterDownCommand());
		// primary.buttonPovLeft.whenPressed(new NavRocketPortCommandGroup());
		// primary.buttonPovDown.whenPressed(new ClimbDownCommandGroup());
		// primary.buttonPovUp.whenPressed(new ClimbCommandGroup());

		XboxController secondary = OI.getSecondaryController();
		secondary.buttonB.whileHeld(new GripperOutCommand());
		secondary.buttonA.whileHeld(new GripperInCommand());
		secondary.buttonBumperLeft.whileHeld(new GrabberOpenCommand());
		secondary.buttonBumperRight.whileHeld(new GrabberCloseCommand());
		secondary.buttonY.whileHeld(new TiltUpCommand());
		secondary.buttonX.whileHeld(new TiltDownCommand());
		secondary.buttonPovUp.whenPressed(new ElevatorHatchUpCommand());
		secondary.buttonPovDown.whenPressed(new ElevatorHatchDownCommand());
		secondary.buttonPovRight.whenPressed(new ElevatorBallUpCommand());
		secondary.buttonPovLeft.whenPressed(new ElevatorBallDownCommand());
	}

	public static void setupTestMappings() {
		XboxController primary = OI.getPrimaryController();
		primary.buttonBumperRight.whenPressed(new DebugMotorControllerIncrement());
		primary.buttonBumperLeft.whenPressed(new DebugMotorControllerDecrement());
	}

}