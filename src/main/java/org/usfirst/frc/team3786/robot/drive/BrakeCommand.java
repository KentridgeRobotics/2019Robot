package org.usfirst.frc.team3786.robot.drive;

import org.usfirst.frc.team3786.robot.drive.TwoWheelDriveSystem;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeCommand extends Command {

    private boolean isBrake;

    public BrakeCommand(boolean isBrake) {
        requires(TwoWheelDriveSystem.getInstance());
        this.isBrake = isBrake;
    }

    @Override
    protected void initialize() {
        if(isBrake == true) {
            TwoWheelDriveSystem.getInstance().setMotorSpeeds(0.0, 0.0);
        }
        if(isBrake == false) {

        }
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
}