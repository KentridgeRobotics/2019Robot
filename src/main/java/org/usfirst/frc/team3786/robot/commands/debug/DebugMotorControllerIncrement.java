package org.usfirst.frc.team3786.robot.commands.debug;

import edu.wpi.first.wpilibj.command.Command;

public class DebugMotorControllerIncrement extends Command {

	private boolean run = false;

	public DebugMotorControllerIncrement() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		DebugMotorController.getInstance().increment();
		run = true;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (run) {
			run = false;
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
