/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.subsystems.ButtLifterTalonSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.MaxSonar;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithRollersCommand extends Command {

	private boolean isDone;
	private double targetDist = 45.2; // 18 inches. This is an estimate...

	public DriveWithRollersCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(ButtLifterTalonSubsystem.getInstance()); // Test Chassis has Talons.
		requires(NeoDriveSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		isDone = false;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (MaxSonar.getInstance().getDistanceCM() > targetDist) {
			NeoDriveSubsystem.getInstance().arcadeDrive(0.5, 0.0); // Powers probably need to be tuned
			ButtLifterTalonSubsystem.getInstance().setRollerSpeed(0.5); // not sure if 0.5 or -0.5 is forward
		} else {
			isDone = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isDone;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		NeoDriveSubsystem.getInstance().arcadeDrive(0.0, 0.0);
		ButtLifterTalonSubsystem.getInstance().setRollerSpeed(0.0);
	}
}
