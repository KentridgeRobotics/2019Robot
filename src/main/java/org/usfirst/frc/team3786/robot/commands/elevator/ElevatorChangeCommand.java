package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorChangeCommand extends Command {
    
    ElevatorSubsystem.Levels levels;

    private boolean done;

    public ElevatorChangeCommand(ElevatorSubsystem.Levels newLevel) {
        requires(ElevatorSubsystem.getInstance());
        levels = newLevel;
    }

    @Override
    protected void initialize() {
        done = false;
    }

    @Override
    protected void execute() {
        double desiredRotations = levels.getRotations();
        
        System.out.println("[!] DESIRED ROTATIONS: " + desiredRotations);
    }

    @Override
    protected boolean isFinished() {
        return done;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
