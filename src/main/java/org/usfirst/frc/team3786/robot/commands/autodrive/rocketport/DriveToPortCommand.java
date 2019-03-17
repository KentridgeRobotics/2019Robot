package org.usfirst.frc.team3786.robot.commands.autodrive.rocketport;

import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.vision.TargetLine;
import org.usfirst.frc.team3786.robot.utils.UltrasonicSensor;
import org.usfirst.frc.team3786.robot.utils.UltrasonicSensor.Side;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToPortCommand extends Command {

	private static final double epsilon = 3.0;
	private static final double turningEpsilon = 10.0;
	private static final double positionEpsilon = 10.0;
	private static final int lostLineLimit = 4;

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
		if (hasRunOnce && lostLineDuration < lostLineLimit) {
			if ((correction >= turningEpsilon && position < -positionEpsilon)
					|| (correction <= -turningEpsilon && position > positionEpsilon)) {
				NeoDriveSubsystem.getInstance().arcadeDrive(0.1, 0);
				System.out.println("DriveToPort: Forward - Position Correction");
			} else if (correction < -turningEpsilon && position < -positionEpsilon) {
				NeoDriveSubsystem.getInstance().arcadeDrive(0, 0.05);
				System.out.println("DriveToPort: Right Turn");
			} else if (correction > turningEpsilon && position > positionEpsilon) {
				NeoDriveSubsystem.getInstance().arcadeDrive(0, -0.05);
				System.out.println("DriveToPort: Left Turn");
			} else if (Math.abs(position) <= positionEpsilon) {
				if (correction > epsilon) {
					NeoDriveSubsystem.getInstance().arcadeDrive(0, 0.05);
					System.out.println("DriveToPort: Right Turn");
				} else if (correction < -epsilon) {
					NeoDriveSubsystem.getInstance().arcadeDrive(0, -0.05);
					System.out.println("DriveToPort: Left Turn");
				} else if (Math.abs(correction) < epsilon) {
					System.out.println("DriveToPort: Aligned to Line");
					if (UltrasonicSensor.getInstance(Side.LEFT).getDistanceCM() > 50
							&& UltrasonicSensor.getInstance(Side.RIGHT).getDistanceCM() > 50) {
						NeoDriveSubsystem.getInstance().arcadeDrive(0.1, 0);
					} else {
						NeoDriveSubsystem.getInstance().arcadeDrive(0, 0);
						System.out.println("DriveToPort: Reached Wall");
						isDone = true;
					}
				}
			}
		} else if (hasRunOnce && lostLineDuration >= lostLineLimit) {
			NeoDriveSubsystem.getInstance().arcadeDrive(0, 0);
			System.out.println("DriveToPort: Line Lost");
			isDone = true;
		} else if (!hasRunOnce) {
			System.out.println("DriveToPort: Failed to Find Line");
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
