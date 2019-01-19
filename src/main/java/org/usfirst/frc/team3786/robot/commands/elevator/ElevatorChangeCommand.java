package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorChangeCommand extends Command {
    
    ElevatorSubsystem.Levels levels;
    ElevatorSubsystem.Levels currentLevel = levels.ZERO;
    private boolean done;
    private double desiredRotations = levels.getRotations();

    private ElevatorSubsystem.VerticalDirection verticalDirection;

    public ElevatorChangeCommand(ElevatorSubsystem.VerticalDirection changeLevel) {
        requires(ElevatorSubsystem.getInstance());
    }

    @Override
    protected void initialize() {
        double currentRotations = ElevatorSubsystem.getInstance().getRotation();
        for(ElevatorSubsystem.Levels levels : ElevatorSubsystem.Levels.values()) {
            if(verticalDirection == verticalDirection.UP) {
                currentLevel = levels.up();
                if(currentLevel == levels.THREE) {
                    currentLevel = levels.THREE;
                }
            }
            if(verticalDirection == verticalDirection.DOWN) {
                currentLevel = levels.down();
                if(currentLevel == levels.ZERO) {
                    currentLevel = levels.ZERO;
                }
            }
            if(verticalDirection == verticalDirection.STOP) {
                currentLevel = levels.stop();
            }
        }
        done = false;
    }

    @Override
    protected void execute() {        
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

    public void up() {
        verticalDirection = verticalDirection.UP;
    }

    public void down() {
        verticalDirection = verticalDirection.DOWN;
    }

    public void stop() {
        verticalDirection = verticalDirection.STOP;
    }
}
