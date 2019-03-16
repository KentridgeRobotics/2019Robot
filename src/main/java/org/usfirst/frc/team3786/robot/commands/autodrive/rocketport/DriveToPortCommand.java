package org.usfirst.frc.team3786.robot.commands.autodrive.rocketport;

import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.vision.TargetLine;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToPortCommand extends Command {

	private static final double epsilon = 3.0;
	private static final double turningEpsilon = 10.0;
	private static final double positionThreshold = 10.0;
	private static final int lostLineThreshold = 4;

	private double position = 0.0;
	private double correction = 0.0;
	private boolean isDone = false;
	private boolean hasRunOnce = false;
	private int lostLineDuration = 0;

	public DriveToPortCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(NeoDriveSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (TargetLine.getInstance().hasLine()) {
			lostLineDuration = 0;
			hasRunOnce = true;
			correction = TargetLine.getInstance().getLineAngle();
			position = TargetLine.getInstance().getLinePosition();
		} else {
			lostLineDuration++;
		}
		if (hasRunOnce && lostLineDuration < lostLineThreshold) {
			if ((correction >= turningEpsilon && position < -positionThreshold)
					|| (correction <= -turningEpsilon && position > positionThreshold)) {
				NeoDriveSubsystem.getInstance().arcadeDrive(0.1, 0);
			} else if (correction < -turningEpsilon && position < -positionThreshold) {
				NeoDriveSubsystem.getInstance().arcadeDrive(0, 0.05);
			} else if (correction > turningEpsilon && position > positionThreshold) {
				NeoDriveSubsystem.getInstance().arcadeDrive(0, -0.05);
			} else if (Math.abs(position) <= positionThreshold) {
				if (correction > epsilon) {
					NeoDriveSubsystem.getInstance().arcadeDrive(0, 0.05);
				} else if (correction < -epsilon) {
					NeoDriveSubsystem.getInstance().arcadeDrive(0, -0.05);
				} else if (Math.abs(correction) < epsilon) {
					System.out.println("ALIGNED TO LINE");
					isDone = true;
				}
			}
		} else if (hasRunOnce && lostLineDuration >= lostLineThreshold) {
			NeoDriveSubsystem.getInstance().arcadeDrive(0, 0);
			System.out.println("FAILED TO FIND LINE");
			isDone = true;
		} else if (!hasRunOnce) {
			NeoDriveSubsystem.getInstance().arcadeDrive(0, 0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isDone;
	}

	// Called once after isFinished returns true
	protected void end() {
	}
}
