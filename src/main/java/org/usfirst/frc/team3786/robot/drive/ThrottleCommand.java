package org.usfirst.frc.team3786.robot.drive;

import org.usfirst.frc.team3786.robot.drive.TwoWheelDriveSystem;

import edu.wpi.first.wpilibj.command.Command;

public class ThrottleCommand extends Command {

    public ThrottleCommand() {
        requires(TwoWheelDriveSystem.getInstance());
    }

    @Override
    protected void initialize() {
        TwoWheelDriveSystem.getInstance().setMotorSpeeds(1.0, 1.0);
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
}