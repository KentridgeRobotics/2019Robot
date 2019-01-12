package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
import org.usfirst.frc.team3786.robot.grabbersystem.OpenGrabberCommand;
import org.usfirst.frc.team3786.robot.grabbersystem.CloseGrabberCommand;
import org.usfirst.frc.team3786.robot.grabbersystem.GrabberStopCommand;
import org.usfirst.frc.team3786.robot.utils.XboxController;

public class Mappings {

	public final static int primaryControllerId = 0;
	public final static int secondaryControllerId = 1;

	public final static int leftMotor = 1;
	public final static int rightMotor = 2;
	public final static int grabber = 3;
	public final static int flinger = 4;
	public final static int tilter = 5;

	public static void setupDefaultMappings() {
		OI.getPrimaryController();
	}

	public static void setupTestMappings() {
		XboxController primary = OI.getPrimaryController();
		GrabberStopCommand stopGrabber = new GrabberStopCommand();
		primary.buttonBumperRight.whenPressed(new DebugMotorControllerIncrement());
		primary.buttonBumperLeft.whenPressed(new DebugMotorControllerDecrement());
		primary.buttonA.whenPressed(new OpenGrabberCommand());
		primary.buttonA.whenReleased(stopGrabber);
		primary.buttonB.whenPressed(new CloseGrabberCommand());
		primary.buttonB.whenReleased(stopGrabber);
	}

}