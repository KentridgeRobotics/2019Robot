/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/* Nicholas Leung, created 1/12/19                                            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.elevatorsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class ElevatorSystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private static ElevatorSystem instance;

  public static ElevatorSystem getInstance()
  {
    if(instance == null)
      instance = new ElevatorSystem();
    return instance;
  }

  private WPI_TalonSRX elevatorMotor;

  public ElevatorSystem()
  {
    elevatorMotor = new WPI_TalonSRX(Mappings.elevatorMotor);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setMotorSpeed(double speed)
  {
    elevatorMotor.set(speed);
  }
}
