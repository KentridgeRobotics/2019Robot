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

public class ClimbWhileLevelCommand extends Command {
	public ClimbWhileLevelCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(ElevatorSubsystem.getInstance());
		requires(ButtLifterSubsystem.getInstance());
		requires(NeoDriveSubsystem.getInstance());
	}

	private double tiltTolerance = 8.0;
	private double correctionFactor = 1.1; //something times the error
	private double frontSpeed;
	private double rearSpeed;

	private boolean doDriveForward;

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		doDriveForward = false;
		frontSpeed = -0.7;
		rearSpeed = -0.35;
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
			frontSpeed = -0.7; // set front to front speed. 1 + error fraction
			rearSpeed = -0.7; // set rear to front speed.
		}
		ElevatorSubsystem.getInstance().setElevatorSpeed(frontSpeed);
		ButtLifterSubsystem.getInstance().setSpeed(rearSpeed);
		Dashboard.getInstance().putNumber(false, "Front Climb Motor Speed", frontSpeed);
		Dashboard.getInstance().putNumber(false, "Rear Climb Motor Speed", rearSpeed);	
		if (ElevatorSubsystem.getInstance().getRotation() < 1.0) {
			doDriveForward = true;
		}
		if (doDriveForward) {
			NeoDriveSubsystem.getInstance().arcadeDrive(0.8, 0.0);
			ButtLifterRollersSubsystem.getInstance().setSpeed(0.8);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		//return ButtLifterTalonSubsystem.getInstance().isSwitchSet();
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
		ButtLifterSubsystem.getInstance().setSpeed(0.0);
		ButtLifterRollersSubsystem.getInstance().setSpeed(0.0);
		NeoDriveSubsystem.getInstance().arcadeDrive(0.0, 0.0);
	}
}
