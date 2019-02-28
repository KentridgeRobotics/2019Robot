package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.utils.XboxController;

public class OI {

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

	public static double getRobotTurn() {
		return getPrimaryController().getRightStickX();
	}

	public static double getRobotThrottle() {
		return getPrimaryController().getLeftStickY();
	}

	public static double getButtLifterPower() {
		return getPrimaryController().getRightTrigger() - getPrimaryController().getLeftTrigger();
	}

	public static double getElevatorPower() {
		return getSecondaryController().getRightTrigger() - getSecondaryController().getLeftTrigger();
	}

}