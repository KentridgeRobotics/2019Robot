
// getPosition(); 
// SendLevelCommand() button for level 1,2,3 takes enum values, figures out num of rotations = 1
// ElvSubSystem() Set height to number of rotations, check if rotations are close enough (close enuogh to what?), and if not, go again, return boolean true if close, false if it had to move
package org.usfirst.frc.team3786.robot.commands.elevator;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj.command.Command;

public class ElevatorSendCommand extends Command {

	private ElevatorSubsystem.HatchLevels targetHatch = null;
	private ElevatorSubsystem.BallLevels targetBall = null;

	public ElevatorSendCommand(ElevatorSubsystem.HatchLevels target) {
		targetHatch = target;
	}

	public ElevatorSendCommand(ElevatorSubsystem.BallLevels target) {
		targetBall = target;
	}

	@Override
	protected void initialize() {
		if (targetHatch != null)
			ElevatorSubsystem.getInstance().setLevel(targetHatch);
		else if (targetBall != null)
			ElevatorSubsystem.getInstance().setLevel(targetBall);
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
