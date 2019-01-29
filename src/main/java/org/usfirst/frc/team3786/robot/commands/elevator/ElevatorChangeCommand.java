package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.Levels;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorChangeCommand extends Command {

	private boolean done;
	private double desiredRotations = 0;

	private ElevatorSubsystem.VerticalDirection verticalDirection;
	private ElevatorSubsystem.Levels currentLevel = Levels.ZERO;

	public ElevatorChangeCommand(ElevatorSubsystem.VerticalDirection changeLevel) {
		requires(ElevatorSubsystem.getInstance());
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		System.out.println("[!] DESIRED ROTATIONS: " + desiredRotations);
		for (ElevatorSubsystem.Levels levels : ElevatorSubsystem.Levels.values()) {
			switch (verticalDirection) {
			case UP:
				currentLevel = levels.up();
				if (currentLevel == ElevatorSubsystem.Levels.THREE)
					currentLevel = ElevatorSubsystem.Levels.THREE;
				break;
			case DOWN:
				currentLevel = levels.down();
				if (currentLevel == ElevatorSubsystem.Levels.ZERO)
					currentLevel = ElevatorSubsystem.Levels.ZERO;
				break;
			case STOP:
				currentLevel = levels.stop();
				break;
			}
		}
		done = false;
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

	public void up() {
		verticalDirection = ElevatorSubsystem.VerticalDirection.UP;
	}

	public void down() {
		verticalDirection = ElevatorSubsystem.VerticalDirection.DOWN;
	}

	public void stop() {
		verticalDirection = ElevatorSubsystem.VerticalDirection.STOP;
	}
}
