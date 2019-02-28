/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.commands.climbdown;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team3786.robot.commands.climber.AllMtrsBrakeCommand;
import org.usfirst.frc.team3786.robot.commands.climber.DriveToWallCommand;
import org.usfirst.frc.team3786.robot.commands.elevator.ElevatorSendCommand;
import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.Levels;
import org.usfirst.frc.team3786.robot.commands.climbdown.BackwardsWithRollersCommand;

public class ClimbDownCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ClimbDownCommandGroup() {
    addSequential(new AllMtrsBrakeCommand());
    addSequential(new ElevatorSendCommand(Levels.ONE)); // tune later
    addSequential(new DriveToWallCommand(0.8, 40.0, false)); // tune later
    // buttlifter down
    addSequential(new BackwardsWithRollersCommand());
    // pull up buttlifter and raise elevator. Some variation of
    // ClimbWhileLevelCommand.
    addSequential(new DriveToWallCommand(0.8, 60, false)); // tune later
    addSequential(new ElevatorSendCommand(Levels.ONE));
  }
}
