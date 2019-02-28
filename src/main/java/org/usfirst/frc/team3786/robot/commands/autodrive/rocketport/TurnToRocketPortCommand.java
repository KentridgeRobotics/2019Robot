/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.autodrive.rocketport;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToRocketPortCommand extends Command {

  private TurnHolder holder;

  private double targetHeading;
  private double initHeading;
  private double currentHeading;
  private double epsilon = 3.0; // tune this later

  private boolean isDone;

  public TurnToRocketPortCommand(TurnHolder holder) {
    // Use requires() here to declare subsystem dependencies
    requires(NeoDriveSubsystem.getInstance());
    this.holder = holder;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    initHeading = Gyroscope.getInstance().getHeadingContinuous();
    targetHeading = initHeading + holder.turn;
    System.err.println("!!!Turn to Rocket Port started!!!");
    System.err.println("Holder.Turn is " + holder.turn);
    Dashboard.getInstance().putNumber(false, "Target Heading", targetHeading);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    currentHeading = Gyroscope.getInstance().getHeadingContinuous();
    if (Math.abs(currentHeading - targetHeading) > epsilon) {
      NeoDriveSubsystem.getInstance().gyroStraight(0.0, targetHeading);
      Dashboard.getInstance().putNumber(false, "Current Heading", currentHeading);
      System.err
          .println("!!!Robot is Turning towards rocket!!! target=" + targetHeading + " current=" + currentHeading);
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
    System.err.println("!!!Turn To Rocket Port Completed!!!");
  }
  /*
   * // Called when another command which requires one or more of the same //
   * subsystems is scheduled to run
   * 
   * @Override protected void interrupted() { }
   */
}
