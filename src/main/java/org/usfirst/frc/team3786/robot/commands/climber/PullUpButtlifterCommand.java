/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3786.robot.subsystems.ButtLifterTalonSubsystem;



public class PullUpButtlifterCommand extends Command {

  private int realPos;  //current position of butt lifter
  private int restPos;  //rest position or neutral position
  private int epsilon = 120;

  private boolean isDone;

  public PullUpButtlifterCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    //might require buttlifter
  }



  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    realPos = ButtLifterTalonSubsystem.getInstance().getRealLifterPosition();
    restPos = 0;

    ButtLifterTalonSubsystem.getInstance().setDesiredLifterPosition(restPos);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Math.abs(restPos - realPos) < epsilon) {
      isDone = true;
    }
    else {
      isDone = false;
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
