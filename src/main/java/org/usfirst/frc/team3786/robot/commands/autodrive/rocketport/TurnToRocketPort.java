/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.autodrive.rocketport;

import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.utils.RocketPortFinder;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToRocketPort extends Command {

  private double targetHeading;
  private double initHeading;
  private double currentHeading;
  private double epsilon = 3.0; //tune this later

  private boolean isDone;
  
  public TurnToRocketPort() {
    // Use requires() here to declare subsystem dependencies
    requires(NeoDriveSubsystem.getInstance());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    initHeading = Gyroscope.getInstance().getHeading();
    targetHeading = initHeading + RocketPortFinder.calcTurn();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    currentHeading = Gyroscope.getInstance().getHeading();
    if(Math.abs(currentHeading - targetHeading) > epsilon) {
      NeoDriveSubsystem.getInstance().gyroStraight(0.0, targetHeading);
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
