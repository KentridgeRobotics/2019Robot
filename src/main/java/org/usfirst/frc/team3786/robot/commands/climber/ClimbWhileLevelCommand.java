/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbWhileLevelCommand extends Command {
  public ClimbWhileLevelCommand() {
    // Use requires() here to declare subsystem dependencies
    //Later, require front and rear climber
  }
  private double tiltTolerance = 3.0;
  private double correctionFactor = 0.9;
  private double frontSpeed;
  private double rearSpeed;
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    frontSpeed = 0.5;
    rearSpeed = 0.5;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double [] gravity = Gyroscope.getInstance().getGravity(); //get gyro values into an array.
    double gravityY = gravity[1];
    if(gravityY > tiltTolerance)
    {
      rearSpeed*=correctionFactor; //slow the back
    }
    else if(gravityY < -tiltTolerance)
    {
      frontSpeed*=correctionFactor;
    }
    Dashboard.getInstance().putNumber(false, "Front Climb Motor Speed", frontSpeed);
    Dashboard.getInstance().putNumber(false, "Rear Climb Motor Speed", rearSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
