package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
import org.usfirst.frc.team3786.robot.utils.XboxController;

public class Mappings {

	public final static int primaryControllerId = 0;
	public final static int secondaryControllerId = 1;

	public static void setupDefaultMappings() {
		IO.getPrimaryController();
	}

	public static void setupTestMappings() {
		XboxController primary = IO.getPrimaryController();
		primary.buttonBumperRight.whenPressed(new DebugMotorControllerIncrement());
		primary.buttonBumperLeft.whenPressed(new DebugMotorControllerDecrement());
	}

}