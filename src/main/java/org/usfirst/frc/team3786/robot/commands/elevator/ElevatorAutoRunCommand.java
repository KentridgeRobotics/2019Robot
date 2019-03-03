package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.Robot;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorAutoRunCommand extends Command {

    private static ElevatorAutoRunCommand instance;

    private boolean isDone = false;
    private ElevatorSubsystem.Levels targetLevel;
    private ElevatorSubsystem.VerticalDirection direction;

	public ElevatorAutoRunCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(ElevatorSubsystem.getInstance());
	}

    public static ElevatorAutoRunCommand getInstance() {
		if (instance == null)
			instance = new ElevatorAutoRunCommand();
		return instance;
    }

	public void incrementLevel() {
        targetLevel = ElevatorSubsystem.getInstance().getLevelUp();
        direction = ElevatorSubsystem.VerticalDirection.UP;
        if (!this.isRunning()) {
            this.start();
        }
	}

	public void decrementLevel() {
		targetLevel = ElevatorSubsystem.getInstance().getLevelDown();
        direction = ElevatorSubsystem.VerticalDirection.DOWN;
        if (!this.isRunning()) {
            this.start();
        }
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        if (!isDone) {
			double currentMotorRotations = ElevatorSubsystem.getInstance().getRotation();
            if (currentMotorRotations < targetLevel.getRotations() && direction == ElevatorSubsystem.VerticalDirection.UP) {
                ElevatorSubsystem.getInstance().setElevatorSpeed(ElevatorSubsystem.upMultiplier);
            } else if (currentMotorRotations > targetLevel.getRotations() && direction == ElevatorSubsystem.VerticalDirection.DOWN) {
                ElevatorSubsystem.getInstance().setElevatorSpeed(-ElevatorSubsystem.downMultiplier);
            } else {
                isDone = true;
            }
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
        ElevatorSubsystem.getInstance().setElevatorSpeed(0);
        Robot.elevatorRunCommand.start();
    }
}
