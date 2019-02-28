package org.usfirst.frc.team3786.robot.commands.grabber;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.NumConstants;
import org.usfirst.frc.team3786.robot.subsystems.GrabberSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class GrabberCloseCommand extends Command {

	public GrabberCloseCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(GrabberSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		GrabberSubsystem.getInstance().setGrabberSpeed(-0.5);
		Dashboard.getInstance().putNumber(false, "Grabber Current (Amps)",
				GrabberSubsystem.getInstance().getGrabberCurrent());
		if (GrabberSubsystem.getInstance().getGrabberCurrent() > NumConstants.GRABBER_AMP_LIMIT) {
			Dashboard.getInstance().putBoolean(true, "Grabber Overcurrent", true);
		} else {
			Dashboard.getInstance().putBoolean(true, "Grabber Overcurrent", false);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		GrabberSubsystem.getInstance().setGrabberSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
