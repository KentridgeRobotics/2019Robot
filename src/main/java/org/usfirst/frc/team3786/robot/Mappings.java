package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.GrabberCloseCommand;
import org.usfirst.frc.team3786.robot.commands.GrabberOpenCommand;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
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
		primary.buttonA.whileHeld(new GrabberOpenCommand());
		primary.buttonB.whileHeld(new GrabberCloseCommand());
		XboxController secondary = OI.getSecondaryController();
	}

	public static void setupTestMappings() {
		XboxController primary = OI.getPrimaryController();
		primary.buttonBumperRight.whenPressed(new DebugMotorControllerIncrement());
		primary.buttonBumperLeft.whenPressed(new DebugMotorControllerDecrement());
	}

}