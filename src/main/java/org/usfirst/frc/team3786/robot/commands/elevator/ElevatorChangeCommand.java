package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorChangeCommand extends Command {
	
	private double desiredRotations;
	private double currentMotorRotations;
	private ElevatorSubsystem.Levels nextLevel;
	private ElevatorSubsystem.VerticalDirection verticalDirection;
	private boolean isDone;

	public ElevatorChangeCommand(ElevatorSubsystem.VerticalDirection changeLevel) {
		verticalDirection = changeLevel;
		requires(ElevatorSubsystem.getInstance());
	}

	private double findRotations() {

		currentMotorRotations = ElevatorSubsystem.getInstance().getRotation();
		double possibleLowerRotation = 0.0;
		// Smallest one that is bigger than my current OR Biggest one that is smaller than my current

		for (ElevatorSubsystem.Levels levels : ElevatorSubsystem.Levels.values()) {
			switch (verticalDirection) {
				case UP:
					if(currentMotorRotations < levels.getRotations()) {
						desiredRotations = levels.getRotations();
						return desiredRotations;
					} 
					break;
				
				case DOWN:
					if(currentMotorRotations > levels.getRotations() && levels.getRotations() > possibleLowerRotation) {
						possibleLowerRotation = levels.getRotations();
					}
					break;
			
				case STOP:
					desiredRotations = currentMotorRotations;
					break;
			
				default:
					desiredRotations = currentMotorRotations;
					break;
			}
		}

		desiredRotations = possibleLowerRotation;
		return desiredRotations;
	}

	@Override
	protected void initialize() {
		this.desiredRotations = findRotations();
		isDone = false;
	}

	@Override
	protected void execute() {
		currentMotorRotations = ElevatorSubsystem.getInstance().getRotation();
		switch (verticalDirection) {
				case UP:
				if(currentMotorRotations >= desiredRotations) {
					isDone = true;
					return;
				}
				ElevatorSubsystem.getInstance().setElevatorSpeed(0.7);
				break;
			case DOWN:
				if(currentMotorRotations <= desiredRotations) {
					isDone = true;
					return;
				}
				ElevatorSubsystem.getInstance().setElevatorSpeed(-0.7);
				break;
			case STOP:
				ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
				break;
			default:
				ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
				break;
		}
	}
	

	@Override
	protected boolean isFinished() {
		return isDone;
	}

	@Override
	protected void end() {
		ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
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
