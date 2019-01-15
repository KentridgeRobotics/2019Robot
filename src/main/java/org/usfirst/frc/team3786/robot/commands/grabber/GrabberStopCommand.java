package org.usfirst.frc.team3786.robot.commands.grabber;

import org.usfirst.frc.team3786.robot.subsystems.GrabberSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class GrabberStopCommand extends Command {

	public GrabberStopCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(GrabberSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		GrabberSubsystem.getInstance().setGrabberSpeed(0);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
