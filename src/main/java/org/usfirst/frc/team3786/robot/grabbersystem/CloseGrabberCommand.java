/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/* Nicholas Leung, created 1/11/19                                            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.grabbersystem;

import edu.wpi.first.wpilibj.command.Command;

public class CloseGrabberCommand extends Command {//TODO: Fill methods when hardware's ready
  private boolean isDone;

  public CloseGrabberCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(GrabberSystem.getInstance());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    GrabberSystem.getInstance().setMotorSpeed(-0.5);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
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

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
