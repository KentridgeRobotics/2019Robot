package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.OI;
import org.usfirst.frc.team3786.robot.subsystems.ChargerDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class TankDriveCommand extends Command {

	public static TankDriveCommand instance;

	public static TankDriveCommand getInstance() {
		if (instance == null)
			instance = new TankDriveCommand();
		return instance;
	}

	public TankDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(ChargerDriveSubsystem.getInstance());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// When the number is negative, the wheels go forwards.
		// When the number is positive, the wheels go backwards.
		double throttle = OI.getRobotThrottle();
		double turn = OI.getRobotTurn();
		double leftTrigger = OI.getPrimaryController().getLeftTrigger();
		ChargerDriveSubsystem.getInstance().arcadeDrive(throttle * (leftTrigger - 1) / 2.0, -turn * (leftTrigger - 1) / 2.0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
