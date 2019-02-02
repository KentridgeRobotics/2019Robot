package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorChangeCommand extends Command {
	
	private double desiredRotations;
	private double currentMotorRotations;
	private ElevatorSubsystem.Levels nextLevel;
	private ElevatorSubsystem elevatorRotations;
	private ElevatorSubsystem.VerticalDirection verticalDirection;

	public ElevatorChangeCommand(ElevatorSubsystem.VerticalDirection changeLevel) {
		verticalDirection = changeLevel;
		requires(ElevatorSubsystem.getInstance());
	}

	@Override
	protected void initialize() {
		currentMotorRotations = elevatorRotations.getRotation();
		double possibleLowerRotation = 0.0;
		// Smallest one that is bigger than my current OR Biggest one that is smaller than my current
		findLevel : for (ElevatorSubsystem.Levels levels : ElevatorSubsystem.Levels.values()) {
			switch (verticalDirection) {
				case UP:
					if(currentMotorRotations < levels.getRotations()) {
						desiredRotations = levels.getRotations();
						break findLevel;
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
	}

	@Override
	protected void execute() {
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
