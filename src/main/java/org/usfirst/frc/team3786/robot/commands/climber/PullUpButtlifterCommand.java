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
    requires(ButtLifterTalonSubsystem.getInstance());
    //might require buttlifter
  }



  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    restPos = -200;

    ButtLifterTalonSubsystem.getInstance().setDesiredLifterPosition(restPos);
    System.err.println("PullUpButtlifterCommand Initialized");

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    realPos = ButtLifterTalonSubsystem.getInstance().getRealLifterPosition();
    if(ButtLifterTalonSubsystem.getInstance().lifterGetMotorOutputPercent() < 5.0) {
      System.err.println("Buttlifter Position: "+realPos);
      System.err.println("Buttlifter output percent: "+ButtLifterTalonSubsystem.getInstance().lifterGetMotorOutputPercent());
      isDone = true;
    }
    else {
      System.err.println("Buttlifter Position: "+realPos);
      System.err.println("Buttlifter output percent: "+ButtLifterTalonSubsystem.getInstance().lifterGetMotorOutputPercent());
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
    System.err.println("PullUpButtlifterCommand finished");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
