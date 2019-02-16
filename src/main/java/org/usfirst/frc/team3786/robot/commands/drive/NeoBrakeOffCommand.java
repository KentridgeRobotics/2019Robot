package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.subsystems.NeoDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class NeoBrakeOffCommand extends Command {

    public NeoBrakeOffCommand() {
    }

    @Override
    protected void initialize() {
        NeoDriveSubsystem.getInstance().setkkBrake(false);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

}