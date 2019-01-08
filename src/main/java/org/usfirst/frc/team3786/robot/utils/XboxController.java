package org.usfirst.frc.team3786.robot.utils;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XboxController {

	edu.wpi.first.wpilibj.XboxController controller;

	public JoystickButton buttonA;
	public JoystickButton buttonB;
	public JoystickButton buttonX;
	public JoystickButton buttonY;
	public JoystickButton buttonBumperLeft;
	public JoystickButton buttonBumperRight;
	public JoystickButton buttonView;
	public JoystickButton buttonMenu;
	public JoystickButton buttonStickLeft;
	public JoystickButton buttonStickRight;

	public XboxController() {
		this(0);
	}

	public XboxController(int id) {
		controller = new edu.wpi.first.wpilibj.XboxController(id);
		setupButtons();
	}

	private void setupButtons() {
		buttonA = new JoystickButton(controller, XboxControllerButton.A.getId());
		buttonB = new JoystickButton(controller, XboxControllerButton.B.getId());
		buttonX = new JoystickButton(controller, XboxControllerButton.X.getId());
		buttonY = new JoystickButton(controller, XboxControllerButton.Y.getId());
		buttonBumperLeft = new JoystickButton(controller, XboxControllerButton.BUMPER_LEFT.getId());
		buttonBumperRight = new JoystickButton(controller, XboxControllerButton.BUMPER_RIGHT.getId());
		buttonView = new JoystickButton(controller, XboxControllerButton.VIEW.getId());
		buttonMenu = new JoystickButton(controller, XboxControllerButton.MENU.getId());
		buttonStickLeft = new JoystickButton(controller, XboxControllerButton.STICK_LEFT.getId());
		buttonStickRight = new JoystickButton(controller, XboxControllerButton.STICK_RIGHT.getId());
	}

	public void getButton(XboxControllerButton button) {
		JoystickButton button = new JoystickButton(controller, 1);
	}

	public enum XboxControllerButton {
		A(1),
		B(2),
		X(3),
		Y(4),
		BUMPER_LEFT(5),
		BUMPER_RIGHT(6),
		VIEW(7),
		MENU(8),
		STICK_LEFT(9),
		STICK_RIGHT(10);

		private final int id;

		XboxControllerButton(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

}