package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.OI;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorRunCommand extends Command {

	public ElevatorRunCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		ElevatorSubsystem.getInstance().setElevatorSpeed(OI.getElevatorPower());
		SmartDashboard.putNumber("Left Elevator Throttle", ElevatorSubsystem.getInstance().getLeftElevatorSpeed());
		SmartDashboard.putNumber("Right Elevator Speed", ElevatorSubsystem.getInstance().getRightElevatorSpeed());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
	}
}
