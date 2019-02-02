package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.Levels;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorChangeCommand extends Command {

	private boolean done;
	
	private double currentLevelRotations;
	private double currentMotorRotations;
	private ElevatorSubsystem.Levels nextLevel;
	private ElevatorSubsystem elevatorRotations;
	private ElevatorSubsystem.Levels levelRotations;
	private ElevatorSubsystem.VerticalDirection verticalDirection;

	public ElevatorChangeCommand(ElevatorSubsystem.VerticalDirection changeLevel) {
		requires(ElevatorSubsystem.getInstance());
	}

	@Override
	protected void initialize() {
		currentMotorRotations = elevatorRotations.getRotation();
		currentLevelRotations = levelRotations.getRotations();

		if(Math.abs(currentLevelRotations - currentMotorRotations) <= 0.0) {
			nextLevel = Levels.ZERO;
		}
	}

	@Override
	protected void execute() {
		for (ElevatorSubsystem.Levels levels : ElevatorSubsystem.Levels.values()) {
			switch (verticalDirection) {
				case UP:
					nextLevel = levels.up();
					if (nextLevel == ElevatorSubsystem.Levels.THREE)
						nextLevel = ElevatorSubsystem.Levels.THREE;
					break;
				case DOWN:
					nextLevel = levels.down();
					if (nextLevel == ElevatorSubsystem.Levels.ZERO)
						nextLevel = ElevatorSubsystem.Levels.ZERO;
					break;
				case STOP:
					nextLevel = levels.stop();
					break;
				default:
					nextLevel = levels.stop();
					break;
			}
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
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
