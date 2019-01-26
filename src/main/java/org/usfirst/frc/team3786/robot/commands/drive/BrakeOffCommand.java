package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.subsystems.TankDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeOffCommand extends Command {

    public BrakeOffCommand() {
        requires(TankDriveSubsystem.getInstance());
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        TankDriveSubsystem.getInstance().setBrake(false);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

}