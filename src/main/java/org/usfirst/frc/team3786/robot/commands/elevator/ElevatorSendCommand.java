
// getPosition(); 
// SendLevelCommand() button for level 1,2,3 takes enum values, figures out num of rotations = 1
// ElvSubSystem() Set height to number of rotations, check if rotations are close enough (close enuogh to what?), and if not, go again, return boolean true if close, false if it had to move
package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorSendCommand extends Command {
    
    ElevatorSubsystem.Levels levels;
    private double targetRotations;
    private double rotations;
    private boolean goingUp;
    private boolean done;
    private final double epsilon = 3.5;

    public ElevatorSendCommand(ElevatorSubsystem.Levels gotoLevel) {
        requires(ElevatorSubsystem.getInstance());
        levels = gotoLevel;
        targetRotations = levels.getRotations();
    }

    @Override
    protected void initialize() {
        done = false;
        System.err.println("Elevator Send Initialized");
        System.err.println("Elevator Starting Position: "+ElevatorSubsystem.getInstance().getRotation());
        System.err.println("[!] TARGET LEVEL IS: " + levels);
        System.err.println("[!] TARGET ROTATIONS: "+ targetRotations);
        rotations = ElevatorSubsystem.getInstance().getRotation();
        if(rotations < targetRotations) {
            if(Math.abs(targetRotations - rotations) < epsilon) {
                done = true;
            }
            else {
                goingUp = true;
            }
        }
        else if(rotations > targetRotations) {
            if(Math.abs(targetRotations - rotations) < epsilon) {
                done = true;
            }
            else {
                goingUp = false;   
            }
        }
        else {
            done = true;
        }
        System.err.println("Elevator Send Going Up?"+ goingUp);
    }

    @Override
    protected void execute() {
        if(!done) {
            rotations = ElevatorSubsystem.getInstance().getRotation();
            if(goingUp) {
                if(Math.abs(targetRotations - rotations) > epsilon) {
                    ElevatorSubsystem.getInstance().setElevatorSpeed(0.7);
                    System.err.println("[!] NUMBER OF ROTATIONS: "+ rotations);
                }
                else {
                    done = true;
                }
            }
            else {
                if(Math.abs(targetRotations - rotations) > epsilon) {
                    ElevatorSubsystem.getInstance().setElevatorSpeed(-0.7);
                    System.err.println("[!] NUMBER OF ROTATIONS: "+ rotations);
                }
                else {
                    done = true;
                }
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return done;
    }

    @Override
    protected void end() {
        ElevatorSubsystem.getInstance().setElevatorSpeed(0.0);
        ElevatorSubsystem.getInstance().setElevatorPos(targetRotations);
        System.err.println("Elevator Send completed");
        System.err.println("Final Elevator Position: "+ElevatorSubsystem.getInstance().getRotation());
    }

    @Override
    protected void interrupted() {
        
    }
}
