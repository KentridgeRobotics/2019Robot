package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.NumConstants;
import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NeoBoostCommand extends Command {

	public NeoBoostCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		NeoDriveSubsystem.getInstance().setBoost(true);
		NeoDriveSubsystem.getInstance().changeRampRate(NumConstants.DRIVE_BOOST_RAMP_RATE);
		SmartDashboard.putString("Throttle Mode", "Boost On");
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
		NeoDriveSubsystem.getInstance().setBoost(false);
		NeoDriveSubsystem.getInstance().changeRampRate(NumConstants.DEFAULT_DRIVE_RAMP_RATE);
		SmartDashboard.putString("Throttle Mode" , "Normal");
	}
}
