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

public class DriveToWallCommand extends Command {

  private boolean isDone;
  private boolean isGyroInverted;
  private double targetDist = 60.0;

  private double targetHeading;
  private double error;
  private double currentHeading;
  private double correction;

  public DriveToWallCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(ChargerDriveSubsystem.getInstance());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    isGyroInverted = true;
    targetHeading = Gyroscope.getInstance().getHeading();
    System.err.println("DriveToWallCommand Started");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (UltrasonicSensor.getInstance().getDistanceCm() > targetDist) {
      currentHeading = Gyroscope.getInstance().getHeading();
      error = targetHeading - currentHeading; //negative means too far right, positive means too far left
      correction = error/90;
      ChargerDriveSubsystem.getInstance().arcadeDrive(0.5, correction);
      System.err.println("Distance to wall:" + UltrasonicSensor.getInstance().getDistanceCm());
    }
    else{
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
    ChargerDriveSubsystem.getInstance().setMotorSpeeds(0, 0);
    System.err.println("Finished driving to wall");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
