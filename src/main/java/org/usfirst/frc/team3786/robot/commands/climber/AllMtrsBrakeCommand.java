/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.subsystems.ButtLifterTalonSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.GrabberSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class AllMtrsBrakeCommand extends Command {
  public AllMtrsBrakeCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(NeoDriveSubsystem.getInstance());
    requires(GrabberSubsystem.getInstance());
    requires(ButtLifterTalonSubsystem.getInstance()); //Practice Chassis has Talons
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    NeoDriveSubsystem.getInstance().setkkBrake(true);
    GrabberSubsystem.getInstance().setBrake(true);
    ButtLifterTalonSubsystem.getInstance().setBrake(true);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }
}
