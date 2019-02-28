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
					else {
						isDone = true;
					} 
					break;
				
				case DOWN:
					if(currentMotorRotations <= 0.0) {
						desiredRotations = 0.0;
					}
					else {
						if(currentMotorRotations > levels.getRotations()) {
							possibleLowerRotation = levels.getRotations();
							desiredRotations = possibleLowerRotation;
							//return desiredRotations;
						}
						else {
							isDone = true;
						}
					}
					break;
			
				case STOP:
					desiredRotations = currentMotorRotations;
					isDone = true;
					break;
			
				default:
					desiredRotations = currentMotorRotations;
					isDone = true;
					break;
			}
		}
		return desiredRotations;
	}

	@Override
	protected void initialize() {
		this.desiredRotations = findRotations();
		System.err.println("[!] Elevator Change Initialized");
		System.err.println("[!] Starting Elevator Pos: "+ ElevatorSubsystem.getInstance().getRotation());
		System.err.println("[!] Desired Rotations: "+ this.desiredRotations);
		isDone = false;
	}

	@Override
	protected void execute() {
		if(!isDone) {
			currentMotorRotations = ElevatorSubsystem.getInstance().getRotation();
			System.err.println("[!] Current Elevator Rotations: "+currentMotorRotations);
			switch (verticalDirection) {
					case UP:
					if(currentMotorRotations >= desiredRotations) {
						isDone = true;
						return;
					}
					ElevatorSubsystem.getInstance().setElevatorSpeed(0.7);
					System.err.println("[!] GOING UP");
					break;
				case DOWN:
					if(currentMotorRotations <= desiredRotations) {
						isDone = true;
						return;
					}
					ElevatorSubsystem.getInstance().setElevatorSpeed(-0.7);
					System.err.println("[!] GOING DOWN");
					break;
				case STOP:
					ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
					break;
				default:
					ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
					break;
			}
		}
	}
	

	@Override
	protected boolean isFinished() {
		return isDone;
	}

	@Override
	protected void end() {
		ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
		ElevatorSubsystem.getInstance().setElevatorPos(desiredRotations);
		System.err.println("[!] Elevator Change Completed");
		System.err.println("[!] Final Elevator Position: "+ElevatorSubsystem.getInstance().getRotation());
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
