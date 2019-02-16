package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.grabber.GrabberCloseCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberInCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberOpenCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberOutCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberStopCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberStopFlingerCommand;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.VerticalDirection;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBoostOffCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBoostOnCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBrakeOnCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBrakeOffCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorChangeCommand;
import org.usfirst.frc.team3786.robot.utils.XboxController;

public class Mappings {
	// Controller Inputs
	public final static int primaryControllerId = 0;
	public final static int secondaryControllerId = 1;

	// CAN Controllers
	public final static int leftMotor = 8; // Drive left 1, SPARK Max controller
	public final static int rightMotor = 9; // Drive right 1, SPARK MAX controller

	public final static int rollers = 3;
	public final static int buttLifter = 4; // ID2 used to be grabber. ID 4 used to be flinger

	public final static int grabberMotor = 10;
	public final static int flingerMotor = 13;

	public final static int tiltMotor = 5; // Drive, Talon SRX
	public final static int elevatorMotor = 12; // Lift, SPARK Max

	// Analog Inputs
	public final static int UltrasonicSensor = 0;

	public static void setupDefaultMappings() {

		XboxController primary = OI.getPrimaryController();
		primary.buttonA.whenPressed(new NeoBrakeOnCommand());
		primary.buttonA.whenReleased(new NeoBrakeOffCommand());
		primary.buttonB.whenPressed(new NeoBoostOnCommand());
		primary.buttonB.whenReleased(new NeoBoostOffCommand());

		XboxController secondary = OI.getSecondaryController();
		GrabberStopCommand grabberStopCommand = new GrabberStopCommand();
		GrabberStopFlingerCommand stopFlinger = new GrabberStopFlingerCommand();
		secondary.buttonA.whenPressed(new ElevatorChangeCommand(VerticalDirection.DOWN));
		secondary.buttonA.whenReleased(new ElevatorChangeCommand(VerticalDirection.STOP));
		secondary.buttonB.whenPressed(new ElevatorChangeCommand(VerticalDirection.UP));
		secondary.buttonB.whenReleased(new ElevatorChangeCommand(VerticalDirection.STOP));
		secondary.buttonBumperLeft.whenPressed(new GrabberOpenCommand());
		secondary.buttonBumperLeft.whenReleased(grabberStopCommand);
		secondary.buttonBumperRight.whenPressed(new GrabberCloseCommand());
		secondary.buttonBumperRight.whenReleased(grabberStopCommand);
		secondary.buttonTriggerLeft.whenPressed(new GrabberOutCommand());
		secondary.buttonTriggerLeft.whenReleased(stopFlinger);
		secondary.buttonTriggerRight.whenPressed(new GrabberInCommand());
		secondary.buttonTriggerRight.whenReleased(stopFlinger);
	}

	public static void setupTestMappings() {
		XboxController primary = OI.getPrimaryController();
		primary.buttonBumperRight.whenPressed(new DebugMotorControllerIncrement());
		primary.buttonBumperLeft.whenPressed(new DebugMotorControllerDecrement());
	}

}