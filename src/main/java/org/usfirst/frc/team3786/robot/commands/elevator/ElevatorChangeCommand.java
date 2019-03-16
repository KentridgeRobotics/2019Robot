package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.Robot;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorChangeCommand extends Command {
	
	private double desiredRotations;
	private double currentMotorRotations;
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

		for (ElevatorSubsystem.HatchLevels level : ElevatorSubsystem.HatchLevels.values()) {
			switch (verticalDirection) {
				case UP:
					if(currentMotorRotations < level.getRotations()) {
						desiredRotations = level.getRotations();
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
						if(currentMotorRotations > level.getRotations()) {
							possibleLowerRotation = level.getRotations();
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
					ElevatorSubsystem.getInstance().setElevatorSpeed(ElevatorSubsystem.upMultiplier);
					System.err.println("[!] GOING UP");
					break;
				case DOWN:
					if(currentMotorRotations <= desiredRotations) {
						isDone = true;
						return;
					}
					ElevatorSubsystem.getInstance().setElevatorSpeed(-ElevatorSubsystem.downMultiplier);
					System.err.println("[!] GOING DOWN");
					break;
				case STOP:
					ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
					isDone = true;
					break;
				default:
					ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
					isDone = true;
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
		//ElevatorSubsystem.getInstance().setElevatorPos(desiredRotations);
		System.err.println("[!] Elevator Change Completed");
		System.err.println("[!] Final Elevator Position: "+ElevatorSubsystem.getInstance().getRotation());
		Robot.elevatorRunCommand.start();
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
