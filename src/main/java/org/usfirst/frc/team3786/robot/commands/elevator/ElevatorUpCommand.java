package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorUpCommand extends Command {

	public ElevatorUpCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(ElevatorSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		ElevatorSubsystem.getInstance().setElevatorSpeed(0.5);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Dashboard.getInstance().putNumber(false, "Elevator Pos", ElevatorSubsystem.getInstance().getRotation());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		ElevatorSubsystem.getInstance().setElevatorSpeed(0);
	}
}
