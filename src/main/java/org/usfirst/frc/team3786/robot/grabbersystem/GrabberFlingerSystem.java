/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/* Nicholas Leung, created 1/12/19                                            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.grabbersystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class GrabberFlingerSystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private static GrabberFlingerSystem instance;

  public static GrabberFlingerSystem getInstance()
  {
    if(instance == null)
      instance = new GrabberFlingerSystem();
    return instance;
  }

  private WPI_TalonSRX flinger;

  public GrabberFlingerSystem()
  {
    flinger = new WPI_TalonSRX(Mappings.flinger);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setMotorSpeed(double speed)
  {
    flinger.set(speed);
  }
}
