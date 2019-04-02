package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorSendCommand extends Command {

	private ElevatorSubsystem.Levels targetLevel = null;

	public ElevatorSendCommand(ElevatorSubsystem.Levels target) {
		targetLevel = target;
	}

	@Override
	protected void initialize() {
		ElevatorSubsystem.getInstance().setLevel(targetLevel);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return ElevatorSubsystem.getInstance().isAutoDone();
	}

	@Override
	protected void end() {
	}
}
