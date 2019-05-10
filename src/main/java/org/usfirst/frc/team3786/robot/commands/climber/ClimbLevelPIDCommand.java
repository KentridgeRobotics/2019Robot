/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.subsystems.ButtLifterRollersSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.ButtLifterSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbLevelPIDCommand extends Command {
  public ClimbLevelPIDCommand() {
    requires(ElevatorSubsystem.getInstance());
		requires(ButtLifterSubsystem.getInstance());
		requires(NeoDriveSubsystem.getInstance());
  }

	private double frontSpeed;
  private double rearSpeed;
  
	//private double derivative; //difference between errors between cycles. need prevError current error minus last error
	//next value of front motor is kp * p error
	//next elvatorspeed = prevelevatorspeed * (1 + (kp * error)) + (KD * (error - lastError) * (prevElevator - lastPrevElevator))

  private double kP = 0.7;
  private double kI = 0.0;
  private double kD = 0.0;

  private double error = 0.0;
  private double lastError = 0.0;
  private double integral = 0.0;
  private double derivative = 0.0;
  private double correction;

  private boolean doDriveForward;

  @Override
  protected void initialize() {
    doDriveForward = false;
		frontSpeed = -0.7;
		rearSpeed = -0.35;
		execute();
  }

  @Override
  protected void execute() {
    double[] gravity = Gyroscope.getInstance().getGravity();
    double gravityY = gravity[1];
    error = 0 - gravityY;
    integral += error;
    derivative = error - lastError;
    correction = (kP * error) + (kI * integral) + (kD * derivative);
    frontSpeed = frontSpeed * (1 + correction);
    rearSpeed = rearSpeed * (1 + correction);
    ElevatorSubsystem.getInstance().setElevatorSpeed(frontSpeed);
		ButtLifterSubsystem.getInstance().setSpeed(rearSpeed);
		Dashboard.getInstance().putNumber(false, "Front Climb Motor Speed", frontSpeed);
    Dashboard.getInstance().putNumber(false, "Rear Climb Motor Speed", rearSpeed);	
    lastError = error;
    //next value of front motor is kp * p error
    //next elvatorspeed = prevelevatorspeed * (1 + (kp * error)) + (KD * (error - lastError) * (prevElevator - lastPrevElevator));
		if (ElevatorSubsystem.getInstance().getRotation() < 1.0) {
			doDriveForward = true;
		}
		if (doDriveForward) {
			NeoDriveSubsystem.getInstance().arcadeDrive(0.8, 0.0);
			ButtLifterRollersSubsystem.getInstance().setSpeed(0.8);
		}
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    NeoDriveSubsystem.getInstance().arcadeDrive(0.0, 0.0);
    ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
    ButtLifterRollersSubsystem.getInstance().setSpeed(0.0);
    ButtLifterSubsystem.getInstance().setSpeed(0.0);
  }
}
