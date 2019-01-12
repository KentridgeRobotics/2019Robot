package org.usfirst.frc.team3786.robot.drive;

import org.usfirst.frc.team3786.robot.drive.TwoWheelDriveSystem;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeCommand extends Command {

    public BrakeCommand() {
        requires(TwoWheelDriveSystem.getInstance());
    }

    @Override
    protected void initialize() {
        TwoWheelDriveSystem.getInstance().setMotorSpeeds(0.0, 0.0);
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
}