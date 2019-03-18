package org.usfirst.frc.team3786.robot.commands.grabber;

import org.usfirst.frc.team3786.robot.subsystems.GrabberGripperSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class GripperInCommand extends Command {

	public GripperInCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(GrabberGripperSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		GrabberGripperSubsystem.getInstance().setGripperSpeed(-0.75); // tune later
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		GrabberGripperSubsystem.getInstance().setGripperSpeed(0);
	}
}
