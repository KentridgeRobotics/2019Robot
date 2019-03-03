package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.OI;
import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;

import edu.wpi.first.wpilibj.command.Command;

public class NeoDriveCommand extends Command {

	public static NeoDriveCommand instance;

	private boolean useTargetHeading;
	private double targetHeading;

	public static NeoDriveCommand getInstance() {
		if (instance == null)
			instance = new NeoDriveCommand();
		return instance;
	}

	public NeoDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(NeoDriveSubsystem.getInstance());
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
		// driver wants to go straight, haven't started using currentHeading yet.
		if (turn == 0 && !useTargetHeading) {
			targetHeading = Gyroscope.getInstance().getHeadingContinuous();
			useTargetHeading = true;
		}
		// driver wants to turn
		else if (turn != 0 || throttle == 0) {
			useTargetHeading = false;
		}
		// going straight with gyro
		if (useTargetHeading) {
			NeoDriveSubsystem.getInstance().gyroStraight(-throttle, targetHeading);
			Dashboard.getInstance().putBoolean(false, "Straight with Gyro?", true);
		}
		// driving with turn
		else {
			NeoDriveSubsystem.getInstance().arcadeDrive(-throttle, turn);
			Dashboard.getInstance().putBoolean(false, "Straight with Gyro?", false);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}
}
