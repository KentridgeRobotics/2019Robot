/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/* Nicholas Leung, created 1/11/19                                            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.grabbersystem;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

public class GrabberSystem extends Subsystem { //TODO: Fill in methods when more hardware info is out
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private static GrabberSystem instance;

  public static GrabberSystem getInstance()
  {
    if(instance == null)
      instance = new GrabberSystem();
    return instance;
  }

  private WPI_TalonSRX grabber;

  public GrabberSystem()
  {
    grabber = new WPI_TalonSRX(Mappings.grabber);
    grabber.setSafetyEnabled(false);
  }

  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setMotorSpeed(double speed)
  {
    grabber.set(speed);
  }

}
