package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.OI;
import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;
import org.usfirst.frc.team3786.robot.utils.Gyroscope;
import org.usfirst.frc.team3786.robot.utils.BNO055.CalData;

import edu.wpi.first.wpilibj.command.Command;

public class NeoDriveCommand extends Command {

	public static NeoDriveCommand instance;

	private boolean isGyroCalibrated = false;
	double targetHeading = 0.0;
	double currentHeading = 0.0;
	double lastHeading = 0.0;
	boolean currentlyTurning = false;

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
		isGyroCalibrated = true;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!isGyroCalibrated) {
			CalData calibration = Gyroscope.getInstance().getCalibration();
			if (calibration.accel > 1 && calibration.gyro > 2 && calibration.mag > 2 && calibration.sys > 1) {
				isGyroCalibrated = true;
			}
		}
		currentHeading = Gyroscope.getInstance().getHeadingContinuous();
		// When the number is negative, the wheels go forwards.
        // When the number is positive, the wheels go backwards.
        
		double throttle = OI.getRobotThrottle();
		double turn = OI.getRobotTurn();
		boolean useTargetHeading = true;
		// driver wants to go straight, haven't started using currentHeading yet.
		if ((Math.abs(turn) > 0.05) && isGyroCalibrated) {
			//Driver wants to keep on turning
			currentlyTurning = true;
			targetHeading = currentHeading;
			useTargetHeading = false;
			System.err.println("Turn active "+targetHeading);
		}
		else if((Math.abs(currentHeading - targetHeading) > 1.0) && currentlyTurning) {
			//Driver let go of turn joystick. We want to let heading settle.
			targetHeading = currentHeading;
			useTargetHeading = false;
			System.err.println("Turn passive "+targetHeading);
		}
		else if(Math.abs(throttle)<0.1){
			targetHeading=currentHeading;
			useTargetHeading = false;
		}
		else {
			System.err.println("Turn finished "+currentHeading+" targetHeading is "+ targetHeading);
			currentlyTurning = false;
			useTargetHeading = true;
		}
		// going straight with gyro
		if (useTargetHeading) {
			NeoDriveSubsystem.getInstance().gyroStraight(throttle, targetHeading);
			Dashboard.getInstance().putBoolean(false, "Straight with Gyro?", true);
		}
		// driving with turn
		else {
			NeoDriveSubsystem.getInstance().arcadeDrive(throttle, turn);
			Dashboard.getInstance().putBoolean(false, "Straight with Gyro?", false);
		}
		lastHeading = currentHeading;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}
}
