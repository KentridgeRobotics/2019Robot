package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.subsystems.ChargerDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeCommand extends Command {

    public BrakeCommand() {
    }

    @Override
    protected void initialize() {
        ChargerDriveSubsystem.getInstance().setBrake(true);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        ChargerDriveSubsystem.getInstance().setBrake(false);
    }

}