package org.usfirst.frc.team3786.robot;

import org.usfirst.frc.team3786.robot.commands.grabber.GrabberCloseCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberOpenCommand;
import org.usfirst.frc.team3786.robot.commands.grabber.GrabberStopCommand;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.Levels;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.VerticalDirection;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerDecrement;
import org.usfirst.frc.team3786.robot.commands.debug.DebugMotorControllerIncrement;
import org.usfirst.frc.team3786.robot.commands.drive.BoostOffCommand;
import org.usfirst.frc.team3786.robot.commands.drive.BoostOnCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorChangeCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorDownCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorStopCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorUpCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorSendCommand;
import org.usfirst.frc.team3786.robot.utils.XboxController;

public class Mappings {

	public final static int primaryControllerId = 0;
	public final static int secondaryControllerId = 1;

	public final static int leftMotor = 1;
	public final static int rightMotor = 2;

	public final static int grabberMotor = 3;
	public final static int flingerMotor = 4;

	public final static int tiltMotor = 5;
	public final static int elevatorMotor = 8;

	public static void setupDefaultMappings() {
		XboxController primary = OI.getPrimaryController();
		primary.buttonA.whenPressed(new BoostOnCommand());
		primary.buttonA.whenReleased(new BoostOffCommand());
		primary.buttonX.whenPressed(new ElevatorUpCommand());
		primary.buttonB.whenPressed(new ElevatorSendCommand(Levels.ZERO));
		primary.buttonX.whenReleased(new ElevatorStopCommand());
		primary.buttonY.whenPressed(new ElevatorDownCommand());
		primary.buttonY.whenReleased(new ElevatorStopCommand());
		primary.buttonBumperLeft.whenPressed(new ElevatorChangeCommand(VerticalDirection.UP));
		primary.buttonBumperLeft.whenReleased(new ElevatorChangeCommand(VerticalDirection.STOP));
		primary.buttonBumperRight.whenPressed(new ElevatorChangeCommand(VerticalDirection.DOWN));
		primary.buttonBumperRight.whenPressed(new ElevatorChangeCommand(VerticalDirection.STOP));
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