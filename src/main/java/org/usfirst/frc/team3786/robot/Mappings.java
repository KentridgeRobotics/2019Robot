package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.grabber.GrabberCloseCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GripperInCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberOpenCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GripperOutCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberStopCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GripperStopCommand;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.Levels; //for testing
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.VerticalDirection;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBoostOffCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBoostOnCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBrakeOnCommand;
import org.usfirst.frc.team3786.robot.commands.drive.NeoBrakeOffCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorDownCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorSendCommand; //for testing
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorUpCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorChangeCommand;
import org.usfirst.frc.team3786.robot.commands.TestCommand;
import org.usfirst.frc.team3786.robot.commands.autodrive.rocketport.TurnHolder;
import org.usfirst.frc.team3786.robot.commands.autodrive.rocketport.TurnToRocketPort; //for testing
import org.usfirst.frc.team3786.robot.commands.climber.ManualButtLifterDown; //for calibration
import org.usfirst.frc.team3786.robot.commands.climber.ManualButtLifterUp; //for calibration
import org.usfirst.frc.team3786.robot.commands.climber.PullUpButtlifterCommand;
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
	public final static int leftGripper = 1;
	public final static int rightGripper = 2;
	public final static int tilt = 5;

	public final static int leftElevator = 12;
	public final static int rightElevator = 13;

	// Analog Inputs
	public final static int UltrasonicSensor = 0;
	public final static int IRSensor = 1;

	public static void setupDefaultMappings() {

		XboxController primary = OI.getPrimaryController();
		/*
		 * primary.buttonA.whenPressed(new NeoBrakeOnCommand());
		 * primary.buttonA.whenReleased(new NeoBrakeOffCommand());
		 * primary.buttonB.whenPressed(new NeoBoostOnCommand());
		 * primary.buttonB.whenReleased(new NeoBoostOffCommand());
		 */ // removed 2/22/19
		primary.buttonA.whenPressed(new PullUpButtlifterCommand());
		primary.buttonX.whileHeld(new TurnToRocketPort(new TurnHolder())); // for testing
		primary.buttonY.whenPressed(new ElevatorSendCommand(Levels.THREE)); // for calibration
		primary.buttonBumperLeft.whileHeld(new RollersBackwardCommand()); // for testing
		primary.buttonBumperRight.whileHeld(new RollersForwardCommand()); // for testing

		XboxController secondary = OI.getSecondaryController();
		GrabberStopCommand grabberStopCommand = new GrabberStopCommand();
		GripperStopCommand stopGripper = new GripperStopCommand();
		secondary.buttonA.whileHeld(new ElevatorDownCommand());
		secondary.buttonB.whileHeld(new ElevatorUpCommand());
		// secondary.buttonX.whileHeld(new ManualButtLifterDown()); //for calibration
		// secondary.buttonY.whenPressed(new ElevatorSendCommand(Levels.THREE)); //for
		// calibration
		secondary.buttonStickLeft.whileHeld(new ManualButtLifterUp());
		secondary.buttonStickRight.whileHeld(new ManualButtLifterDown());
		secondary.buttonX.whenPressed(new GripperInCommand());
		secondary.buttonX.whenReleased(stopGripper);
		secondary.buttonY.whenPressed(new GripperOutCommand());
		secondary.buttonY.whenReleased(stopGripper);
		secondary.buttonBumperLeft.whenPressed(new GrabberOpenCommand());
		secondary.buttonBumperLeft.whenReleased(grabberStopCommand);
		secondary.buttonBumperRight.whenPressed(new GrabberCloseCommand());
		secondary.buttonBumperRight.whenReleased(grabberStopCommand);
		/*
		 * secondary.buttonTriggerLeft.whenPressed(new GripperOutCommand());
		 * secondary.buttonTriggerLeft.whenReleased(stopGripper);
		 * secondary.buttonTriggerRight.whenPressed(new GripperInCommand());
		 * secondary.buttonTriggerRight.whenReleased(stopGripper);
		 */ // removed 2/22/19
		secondary.buttonView.whenPressed(new ElevatorChangeCommand(VerticalDirection.DOWN));
		secondary.buttonMenu.whenPressed(new ElevatorChangeCommand(VerticalDirection.UP));
	}

	public static void setupTestMappings() {
		XboxController primary = OI.getPrimaryController();
		primary.buttonBumperRight.whenPressed(new DebugMotorControllerIncrement());
		primary.buttonBumperLeft.whenPressed(new DebugMotorControllerDecrement());
	}

}