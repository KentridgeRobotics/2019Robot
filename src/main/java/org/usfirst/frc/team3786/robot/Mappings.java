package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.grabber.GrabberCloseCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GripperInCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberOpenCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GripperOutCommand;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.VerticalDirection;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBoostCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBrakeCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorDownCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorUpCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorChangeCommand;

import java.util.Map;

import org.usfirst.frc.team3786.robot.commands.autodrive.rocketport.NavRocketPortCommandGroup;
import org.usfirst.frc.team3786.robot.commands.climbdown.ClimbDownCommandGroup;
import org.usfirst.frc.team3786.robot.commands.climber.ClimbCommandGroup;
import org.usfirst.frc.team3786.robot.commands.climber.RollersBackwardCommand; //for testing
import org.usfirst.frc.team3786.robot.commands.climber.RollersForwardCommand; //for testing
import org.usfirst.frc.team3786.robot.utils.XboxController;

public class Mappings {
	// Controller IDs
	public final static int primaryControllerId = 0;
	public final static int secondaryControllerId = 1;

	// CAN IDs
	public final static int leftMotor = 8;
	public final static int rightMotor = 9;

	public final static int rollers = 3;
	public final static int buttLifter = 4;

	public final static int grabberMotor = 10;
	public final static int leftGripper = 1;
	public final static int rightGripper = 2;
	public final static int tilt = 5;

	public final static int leftElevator = 12;
	public final static int rightElevator = 13;

	// Digital IO
	public final static Map.Entry<Integer, Integer> ultrasonicLeft = Map.entry(0, 1);
	public final static Map.Entry<Integer, Integer> ultrasonicRight = Map.entry(2, 3);
	public final static int grabberLimitSwitch = 4;

	// Analog IO
	public final static int irSensorLeft = 0;
	public final static int irSensorRight = 1;

	public static void setupDefaultMappings() {

		XboxController primary = OI.getPrimaryController();
		primary.buttonA.whileHeld(new NeoBrakeCommand());
		primary.buttonB.whileHeld(new NeoBoostCommand());
		primary.buttonBumperRight.whileHeld(new RollersForwardCommand());
		primary.buttonBumperLeft.whileHeld(new RollersBackwardCommand());
		primary.buttonPovLeft.whenPressed(new NavRocketPortCommandGroup());
		primary.buttonPovDown.whenPressed(new ClimbDownCommandGroup());
		primary.buttonPovUp.whenPressed(new ClimbCommandGroup());

		XboxController secondary = OI.getSecondaryController();
		secondary.buttonB.whileHeld(new GripperOutCommand());
		secondary.buttonA.whileHeld(new GripperInCommand());
		secondary.buttonBumperLeft.whileHeld(new GrabberOpenCommand());
		secondary.buttonBumperRight.whileHeld(new GrabberCloseCommand());
		secondary.buttonY.whileHeld(new ElevatorUpCommand());
		secondary.buttonX.whileHeld(new ElevatorDownCommand());
		secondary.buttonPovUp.whenPressed(new ElevatorChangeCommand(VerticalDirection.UP));
		secondary.buttonPovDown.whenPressed(new ElevatorChangeCommand(VerticalDirection.DOWN));
	}

	public static void setupTestMappings() {
		XboxController primary = OI.getPrimaryController();
		primary.buttonBumperRight.whenPressed(new DebugMotorControllerIncrement());
		primary.buttonBumperLeft.whenPressed(new DebugMotorControllerDecrement());
	}

}