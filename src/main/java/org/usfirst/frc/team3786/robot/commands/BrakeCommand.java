package org.usfirst.frc.team3786.robot.commands;

import org.usfirst.frc.team3786.robot.subsystems.TankDriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class BrakeCommand extends Command {

    private boolean isBrake;

    public BrakeCommand(boolean isBrake) {
        requires(TankDriveSubsystem.getInstance());
        this.isBrake = isBrake;
    }
    
    @Override
    protected void initialize() {
        if(isBrake == true) {
            TankDriveSubsystem.getInstance().setMotorSpeeds(0.0, 0.0);
        }
        if(isBrake == false) {

        }
    }

    protected boolean isFinished() {
        return false;
    }

}