package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.grabber.GrabberCloseCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberOpenCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberStopCommand;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
import org.usfirst.frc.team3786.robot.commands.drive.BoostOffCommand;
import org.usfirst.frc.team3786.robot.commands.drive.BoostOnCommand;
import org.usfirst.frc.team3786.robot.utils.XboxController;

public class Mappings {

	public final static int primaryControllerId = 0;
	public final static int secondaryControllerId = 1;

	public final static int leftMotor = 1;
	public final static int rightMotor = 2;

	public final static int grabberMotor = 3;
	public final static int flingerMotor = 4;

	public final static int tiltMotor = 5;
	public final static int elevatorMotor = 6;

	public static void setupDefaultMappings() {
		XboxController primary = OI.getPrimaryController();
		primary.buttonA.whenPressed(new BoostOnCommand());
		primary.buttonA.whenReleased(new BoostOffCommand());
		XboxController secondary = OI.getSecondaryController();
		GrabberStopCommand grabberStopCommand = new GrabberStopCommand();
		secondary.buttonA.whenPressed(new GrabberOpenCommand());
		secondary.buttonA.whenReleased(grabberStopCommand);
		secondary.buttonB.whenPressed(new GrabberCloseCommand());
		secondary.buttonB.whenReleased(grabberStopCommand);
	}

	public static void setupTestMappings() {
		XboxController primary = OI.getPrimaryController();
		primary.buttonBumperRight.whenPressed(new DebugMotorControllerIncrement());
		primary.buttonBumperLeft.whenPressed(new DebugMotorControllerDecrement());
	}

}