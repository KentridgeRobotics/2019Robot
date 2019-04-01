/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Robot;
import org.usfirst.frc.team3786.robot.subsystems.ButtLifterTalonSubsystem;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbWhileLevelCommand extends Command {
	public ClimbWhileLevelCommand() {
		// Use requires() here to declare subsystem dependencies
	}

	private double tiltTolerance = 8.0;
	private double correctionFactor = 1.1;
	private double frontSpeed;
	private double rearSpeed;

	private boolean isDone;

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		ButtLifterTalonSubsystem.getInstance().initButtlifterlimitSwitch();
		isDone = false;
		frontSpeed = -0.7;
		rearSpeed = -0.7;
		execute();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double[] gravity = Gyroscope.getInstance().getGravity(); // get gyro values into an array.
		double gravityY = gravity[1];
		if (gravityY > tiltTolerance) // is gravityY greater than 3? Is the robot tilted more than 3 degrees forward?
		{
			frontSpeed /= correctionFactor; // slow the back
			frontSpeed *= correctionFactor; // speed up the rear
		} else if (gravityY < -tiltTolerance) // is gravityY less than -3? Is the robot tilted more than 3 degrees
												// backwards?
		{
			frontSpeed /= correctionFactor; // slow the front
			rearSpeed *= correctionFactor; // speed up the rear
		} else {
			frontSpeed = -0.7; // set front to front speed.
			rearSpeed = -0.7; // set rear to front speed.
		}
		Robot.getElevator().setElevatorSpeed(frontSpeed);
		ButtLifterTalonSubsystem.getInstance().setButtLifterSpeed(rearSpeed);
		Dashboard.getInstance().putNumber(false, "Front Climb Motor Speed", frontSpeed);
		Dashboard.getInstance().putNumber(false, "Rear Climb Motor Speed", rearSpeed);
		if (Robot.getElevator().getRotation() < 2.0) {
			isDone = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		//return ButtLifterTalonSubsystem.getInstance().isSwitchSet();
		return isDone;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.getElevator().setElevatorSpeed(0.0);
		ButtLifterTalonSubsystem.getInstance().setButtLifterSpeed(0.0);
		ButtLifterTalonSubsystem.getInstance().setRollerSpeed(0.0);
	}
}
