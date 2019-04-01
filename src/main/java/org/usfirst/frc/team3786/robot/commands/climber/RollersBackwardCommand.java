/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.subsystems.ButtLifterRollersSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class RollersBackwardCommand extends Command {
	public RollersBackwardCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(ButtLifterRollersSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		ButtLifterRollersSubsystem.getInstance().setSpeed(-0.5);
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
		ButtLifterRollersSubsystem.getInstance().setSpeed(0.0);
	}
}
