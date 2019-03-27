/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.UltrasonicSensor;
import org.usfirst.frc.team3786.robot.utils.UltrasonicSensor.Side;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToWallCommand extends Command {

	private static final int distEpsilon = 2;
	private static final int wallDistanceEpsilon = 10;
	private static final double turnSpeed = 0.1;
	private static final double driveSpeed = 0.1;
	private static final double robotWidth = 71.12;

	private int wallDistance;

	private boolean isDone = false;

	public DriveToWallCommand(int wallDistance) {
		// Use requires() here to declare subsystem dependencies
		requires(NeoDriveSubsystem.getInstance());

		this.wallDistance = wallDistance;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double distLeft = UltrasonicSensor.getInstance(Side.LEFT).getDistanceCM();
		double distRight = UltrasonicSensor.getInstance(Side.RIGHT).getDistanceCM();

		double speed = 0;
		if (distRight > wallDistance || distLeft > wallDistance) {
			speed = driveSpeed;
		}
		if (distLeft + distEpsilon > distRight) {
			NeoDriveSubsystem.getInstance().arcadeDrive(speed, turnSpeed);
		} else if (distRight + distEpsilon > distLeft) {
			NeoDriveSubsystem.getInstance().arcadeDrive(speed, -turnSpeed);
		} else if (distRight > wallDistance && distLeft > wallDistance) {
			NeoDriveSubsystem.getInstance().arcadeDrive(speed, 0);
		} else if (Math.abs(wallDistance - distRight) < wallDistanceEpsilon
				|| Math.abs(wallDistance - distLeft) < wallDistanceEpsilon) {
			NeoDriveSubsystem.getInstance().arcadeDrive(-driveSpeed, 0);
		} else if (distRight < wallDistance && distLeft < wallDistance) {
			NeoDriveSubsystem.getInstance().setMotorSpeeds(0, 0);
			isDone = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isDone;
	}
}
