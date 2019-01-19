
// getPosition(); 
// SendLevelCommand() button for level 1,2,3 takes enum values, figures out num of rotations = 1
// ElvSubSystem() Set height to number of rotations, check if rotations are close enough (close enuogh to what?), and if not, go again, return boolean true if close, false if it had to move
package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorSendCommand extends Command {
    
    ElevatorSubsystem.Levels levels;
    private double rotations, currentRotations;
    private boolean done;

    public ElevatorSendCommand() {
        requires(ElevatorSubsystem.getInstance());
        levels = levels.ONE;
    }

    @Override
    protected void initialize() {
        rotations = ElevatorSubsystem.getInstance().getRotation();
        done = false;
    }

    @Override
    protected void execute() {
        currentRotations = ElevatorSubsystem.getInstance().getRotation();
        System.out.println("[!] NUMBER OF ROTATIONS: " + rotations);
        if(rotations >= currentRotations + 1) {
            levels = levels.ONE;
            ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
            done = true;
        }
        switch (levels) {
            case ONE:
                System.out.println("[!] LEVEL 1 SELECTED.");
                break;
            case TWO:
                System.out.println("[!] LEVEL 2 SELECTED.");
                break;
            case THREE:
                System.out.println("[!] LEVEL 3 SELECTED.");
                break;
            default:
                System.out.println("[!] NO LEVEL SELECTED.");
                break;
        }

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
