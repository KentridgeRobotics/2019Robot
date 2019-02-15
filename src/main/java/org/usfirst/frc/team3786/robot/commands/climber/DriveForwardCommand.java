/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.subsystems.ChargerDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.utils.UltrasonicSensor;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForwardCommand extends Command {

  private boolean isDone;

  private double targetDist = 22.86; //9 inches.

  private double targetHeading;
  private double currentHeading;
  private double error;
  private double correction;

  public DriveForwardCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(ChargerDriveSubsystem.getInstance());
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
      currentHeading = Gyroscope.getInstance().getHeading();
      error = targetHeading - currentHeading;
      correction = error/90;
      ChargerDriveSubsystem.getInstance().arcadeDrive(0.8, correction);
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
    ChargerDriveSubsystem.getInstance().arcadeDrive(0.0, 0.0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
