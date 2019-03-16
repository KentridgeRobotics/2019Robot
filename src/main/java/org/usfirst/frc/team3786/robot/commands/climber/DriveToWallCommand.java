/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.utils.MaxSonar;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToWallCommand extends Command {

  private boolean isDone;
  private boolean isGyroInverted;
  private boolean goForward;
  private double targetDist;
  private double speed;
  private double targetHeading;

  public DriveToWallCommand(double speed, double targetDist, boolean goForward) {
    // Use requires() here to declare subsystem dependencies
    requires(NeoDriveSubsystem.getInstance());
    this.targetDist = targetDist;
    this.goForward = goForward;
    this.speed = speed;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    isGyroInverted = true;
    targetHeading = Gyroscope.getInstance().getHeadingContinuous();
    System.err.println("DriveToWallCommand Started");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (goForward) { // do we want to go forward?
      if (MaxSonar.getInstance().getDistanceCM() > targetDist) { // is the sensor farther than desired?
        NeoDriveSubsystem.getInstance().gyroStraight(speed, targetHeading);
        System.err.println("Distance to wall:" + MaxSonar.getInstance().getDistanceCM());
      }
      else {
        isDone = true;
      }
    }
    else { // we're going backwards
      if (MaxSonar.getInstance().getDistanceCM() < targetDist) { // is the sensor closer than desired?
        if (speed > 0) { // this is just to see if someone puts a negative number for speed when we're
                         // going backwards. A failsafe of sorts
          speed = -1 * speed;
        }
        NeoDriveSubsystem.getInstance().gyroStraight(speed, targetHeading);
        System.err.println("Distance to wall:" + MaxSonar.getInstance().getDistanceCM());
      }
      else {
        isDone = true;
      }
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
    NeoDriveSubsystem.getInstance().setMotorSpeeds(0, 0);
    System.err.println("Finished driving to wall");
  }
}
