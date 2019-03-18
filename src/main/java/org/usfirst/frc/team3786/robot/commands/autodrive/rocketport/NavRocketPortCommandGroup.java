/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.autodrive.rocketport;

import org.usfirst.frc.team3786.robot.commands.climber.DriveToWallCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class NavRocketPortCommandGroup extends CommandGroup {
	/**
	 * Add your docs here.
	 */
	public NavRocketPortCommandGroup() {
		TurnHolder holder = new TurnHolder();
		addSequential(new DriveUntilVectorFoundCommand(holder));
		addSequential(new TurnToRocketPortCommand(holder));
		addSequential(new DriveToWallCommand(0.8, 20, true)); // tune later

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
