package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorSendCommand extends Command {

	private ElevatorSubsystem.Levels targetLevel = null;
	private double targetRotations;

	public ElevatorSendCommand(ElevatorSubsystem.Levels target) {
		targetLevel = target;
		targetRotations = target.getRotations();
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
		return targetRotations == ElevatorSubsystem.getInstance().getCurrentLevel() ? true : false;
	}

	@Override
	protected void end() {
	}
}
