package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.subsystems.ChargerDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class BoostCommand extends Command {

	public BoostCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		ChargerDriveSubsystem.getInstance().setBoost(true);
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
		ChargerDriveSubsystem.getInstance().setBoost(false);
	}
}
