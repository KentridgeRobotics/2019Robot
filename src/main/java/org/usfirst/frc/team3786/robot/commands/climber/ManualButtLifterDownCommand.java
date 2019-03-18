/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.subsystems.ButtLifterTalonSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ManualButtLifterDownCommand extends Command {
	public ManualButtLifterDownCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(ButtLifterTalonSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		ButtLifterTalonSubsystem.getInstance().setButtLifterSpeed(-0.5);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Dashboard.getInstance().putNumber(false, "Buttlifter Pos",
				ButtLifterTalonSubsystem.getInstance().getRealLifterPosition());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		ButtLifterTalonSubsystem.getInstance().setButtLifterSpeed(0.0);
	}
}
