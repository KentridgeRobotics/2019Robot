/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.autodrive.rocketport;

import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.vision.Cameras;
import org.usfirst.frc.team3786.robot.subsystems.vision.PixyCamera;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import io.github.pseudoresonance.pixy2api.Pixy2Line;
import io.github.pseudoresonance.pixy2api.Pixy2Line.Vector;

import edu.wpi.first.wpilibj.command.Command;

public class DriveUntilVectorFound extends Command {

  private boolean isDone;

  byte features;

  private double initHeading;
  public double theta;

  private TurnHolder holder;

  public DriveUntilVectorFound(TurnHolder holder) {
    // Use requires() here to declare subsystem dependencies
    requires(NeoDriveSubsystem.getInstance());
    this.holder = holder;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    isDone = false;
    initHeading = Gyroscope.getInstance().getHeadingContinuous();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    PixyCamera pixy = Cameras.getPixyCamera1();
    features = pixy.getPixy().getLine().getAllFeatures();
    if((features & Pixy2Line.LINE_VECTOR) == Pixy2Line.LINE_VECTOR) {
      Vector[] vectors = pixy.getPixy().getLine().getVectors();
      if(vectors != null) {
        if(vectors.length > 0) {
          Vector vector = vectors[0];
          double leg1 = vector.getX0() - vector.getX1();
          double leg2 = vector.getY1() - vector.getY0();
          theta = Math.toDegrees(Math.atan2(leg2, leg1));
          holder.turn = theta;
          System.err.println("!!!Theta is " + theta + ". Successful!!!");
          isDone = true;
        }
        else {
          System.err.println("!!!Vector length is 0 or less. Unsuccessful!!!");
          NeoDriveSubsystem.getInstance().gyroStraight(0.5, initHeading);
        }//(vectors.length > 0)
      }
      else {
        System.err.println("!!!Vector is Null. Unsuccessful!!!");
        NeoDriveSubsystem.getInstance().gyroStraight(0.5, initHeading);
        }//(vectors != null)
      }
    else {
      System.err.println("!!!LINE_VECTOR Byte not on. Unsuccessful!!!");
      NeoDriveSubsystem.getInstance().gyroStraight(0.5, initHeading);
    }//((features & Pixy2Line.LINE_VECTOR) == Pixy2Line.LINE_VECTOR)
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
    System.err.println("Holder.turn is "+holder.turn);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
