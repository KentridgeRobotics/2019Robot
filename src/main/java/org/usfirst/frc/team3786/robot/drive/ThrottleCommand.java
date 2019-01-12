package org.usfirst.frc.team3786.robot.drive;

import org.usfirst.frc.team3786.robot.drive.TwoWheelDriveSystem;

import edu.wpi.first.wpilibj.command.Command;

public class ThrottleCommand extends Command {

    private boolean isThrottle;

    public ThrottleCommand(boolean isThrottle) {
        requires(TwoWheelDriveSystem.getInstance());
        this.isThrottle = isThrottle;
    }

    @Override
    protected void initialize() {
        if(isThrottle == true) {
            TwoWheelDriveSystem.getInstance().setMotorSpeeds(1.0, 1.0);
        }
        if(isThrottle == false) {

        }
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
}