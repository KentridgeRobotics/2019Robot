/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.utils.UltrasonicSensor;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForwardCommand extends Command {

  private boolean isDone;

  private double targetDist = 22.86; //9 inches.

  private double targetHeading;

  public DriveForwardCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(NeoDriveSubsystem.getInstance());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    targetHeading = Gyroscope.getInstance().getHeading();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(UltrasonicSensor.getInstance().getDistanceCm() > targetDist) {
      NeoDriveSubsystem.getInstance().gyroStraight(0.8, targetHeading);
    }
    else {
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
