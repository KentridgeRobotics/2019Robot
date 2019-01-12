/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/* Nicholas Leung, created 1/11/19                                            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.grabbersystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;

public class GrabberTiltSystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private static GrabberTiltSystem instance;

  public static GrabberTiltSystem getInstance()
  {
    if(instance == null)
      instance = new GrabberTiltSystem();
    return instance;
  }

  WPI_TalonSRX tilter;

  public GrabberTiltSystem()
  {
    tilter = new WPI_TalonSRX(Mappings.tilter);
    tilter.setSafetyEnabled(false);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setMotorSpeed(double speed)
  {
    tilter.set(speed);
  }
}
