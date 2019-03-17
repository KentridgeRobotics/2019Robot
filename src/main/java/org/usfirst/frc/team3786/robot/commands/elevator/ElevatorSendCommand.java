
// getPosition(); 
// SendLevelCommand() button for level 1,2,3 takes enum values, figures out num of rotations = 1
// ElvSubSystem() Set height to number of rotations, check if rotations are close enough (close enuogh to what?), and if not, go again, return boolean true if close, false if it had to move
package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorSendCommand extends Command {

	private double targetRotations;

	public ElevatorSendCommand(ElevatorSubsystem.HatchLevels target) {
		targetRotations = target.getRotations();
	}

	public ElevatorSendCommand(ElevatorSubsystem.BallLevels target) {
		targetRotations = target.getRotations();
	}

	@Override
	protected void initialize() {
		ElevatorSubsystem.getInstance().setLevel(targetRotations);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}
}
