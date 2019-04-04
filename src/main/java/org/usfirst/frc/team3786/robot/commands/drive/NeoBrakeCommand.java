package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NeoBrakeCommand extends Command {

	public NeoBrakeCommand() {
	}

	@Override
	protected void initialize() {
		NeoDriveSubsystem.getInstance().setkkBrake(true);
		SmartDashboard.putString("Throttle Mode", "Brake");
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
		NeoDriveSubsystem.getInstance().setkkBrake(false);
		SmartDashboard.putString("Throttle Mode", "Normal");
	}

}