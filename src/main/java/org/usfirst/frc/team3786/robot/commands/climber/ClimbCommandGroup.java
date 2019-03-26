/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climber;

import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorSendCommand;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.HatchLevels;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ClimbCommandGroup extends CommandGroup {
	/**
	 * Add your docs here.
	 */
	public ClimbCommandGroup() {
		// Add Commands here:
		addSequential(new AllMotorsBrakeCommand());
		addSequential(new ElevatorSendCommand(HatchLevels.TWO)); // tune later
		addSequential(new DriveToWallCommand(30));
		addSequential(new ClimbWhileLevelCommand());
		addSequential(new DriveToWallCommand(30));

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
