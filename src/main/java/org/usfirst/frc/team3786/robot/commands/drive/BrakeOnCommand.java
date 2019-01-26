package org.usfirst.frc.team3786.robot.commands.drive;

import org.usfirst.frc.team3786.robot.subsystems.TankDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeOnCommand extends Command {

    public BrakeOnCommand() {
        requires(TankDriveSubsystem.getInstance());
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        TankDriveSubsystem.getInstance().setBrake(true);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

}