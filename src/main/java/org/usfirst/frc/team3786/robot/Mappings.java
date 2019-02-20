package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.grabber.GrabberCloseCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GripperInCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberOpenCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GripperOutCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberStopCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberStopFlingerCommand;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.VerticalDirection;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBoostOffCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBoostOnCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBrakeOnCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBrakeOffCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorDownCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorUpCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorChangeCommand;
import org.usfirst.frc.team3786.robot.commands.autodrive.rocketport.TurnHolder;
import org.usfirst.frc.team3786.robot.commands.autodrive.rocketport.TurnToRocketPort; //for testing
import org.usfirst.frc.team3786.robot.commands.climber.ManualButtLifterDown; //for calibration
import org.usfirst.frc.team3786.robot.commands.climber.ManualButtLifterUp; //for calibration
import org.usfirst.frc.team3786.robot.commands.climber.RollersBackwardCommand; //for testing
import org.usfirst.frc.team3786.robot.commands.climber.RollersForwardCommand; //for testing
import org.usfirst.frc.team3786.robot.utils.XboxController;

public class Mappings {
	// Controller Inputs
	public final static int primaryControllerId = 0;
	public final static int secondaryControllerId = 1;

	// CAN Controllers
	public final static int leftMotor = 8;
	public final static int rightMotor = 9;

	public final static int rollers = 3;
	public final static int buttLifter = 4;

	public final static int grabberMotor = 10;
	public final static int leftFlinger = 1;
	public final static int rightFlinger = 2;
	public final static int tilt = 5;

	public final static int leftElevator = 6;
	public final static int rightElevator = 7;

	// Analog Inputs
	public final static int UltrasonicSensor = 0;

	public static void setupDefaultMappings() {

		XboxController primary = OI.getPrimaryController();
		primary.buttonA.whenPressed(new NeoBrakeOnCommand());
		primary.buttonA.whenReleased(new NeoBrakeOffCommand());
		primary.buttonB.whenPressed(new NeoBoostOnCommand());
		primary.buttonB.whenReleased(new NeoBoostOffCommand());
		primary.buttonX.whileHeld(new TurnToRocketPort(new TurnHolder())); //for testing
		primary.buttonBumperLeft.whileHeld(new RollersBackwardCommand()); //for testing
		primary.buttonBumperRight.whileHeld(new RollersForwardCommand()); //for testing

		XboxController secondary = OI.getSecondaryController();
		GrabberStopCommand grabberStopCommand = new GrabberStopCommand();
		GrabberStopFlingerCommand stopFlinger = new GrabberStopFlingerCommand();
		secondary.buttonA.whileHeld(new ElevatorDownCommand());
		secondary.buttonB.whileHeld(new ElevatorUpCommand());
		secondary.buttonX.whileHeld(new ManualButtLifterDown()); //for calibration
		secondary.buttonY.whileHeld(new ManualButtLifterUp()); //for calibration
		secondary.buttonBumperLeft.whenPressed(new GrabberOpenCommand());
		secondary.buttonBumperLeft.whenReleased(grabberStopCommand);
		secondary.buttonBumperRight.whenPressed(new GrabberCloseCommand());
		secondary.buttonBumperRight.whenReleased(grabberStopCommand);
		secondary.buttonTriggerLeft.whenPressed(new GripperOutCommand());
		secondary.buttonTriggerLeft.whenReleased(stopFlinger);
		secondary.buttonTriggerRight.whenPressed(new GripperInCommand());
		secondary.buttonTriggerRight.whenReleased(stopFlinger);
		secondary.buttonView.whenPressed(new ElevatorChangeCommand(VerticalDirection.DOWN));
		secondary.buttonMenu.whenPressed(new ElevatorChangeCommand(VerticalDirection.UP));
	}

	public static void setupTestMappings() {
		XboxController primary = OI.getPrimaryController();
		primary.buttonBumperRight.whenPressed(new DebugMotorControllerIncrement());
		primary.buttonBumperLeft.whenPressed(new DebugMotorControllerDecrement());
	}

}