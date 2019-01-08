package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.utils.XboxController;

public class IO {

	private static XboxController primaryController = null;
	private static XboxController secondaryController = null;

	public static XboxController getPrimaryController() {
		if (primaryController == null)
			primaryController = new XboxController(Mappings.primaryControllerId);
		return primaryController;
	}

	public static XboxController getSecondaryController() {
		if (secondaryController == null)
			secondaryController = new XboxController(Mappings.secondaryControllerId);
		return secondaryController;
	}

}