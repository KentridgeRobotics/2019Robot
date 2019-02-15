/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Dashboard;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class ButtLifterTalonSubsystem extends Subsystem {
  private static ButtLifterTalonSubsystem instance;

  private WPI_TalonSRX buttLifter;
  private WPI_TalonSRX rollers;


  public static ButtLifterTalonSubsystem getInstance() {
    if(instance == null)
      instance = new ButtLifterTalonSubsystem();
    return instance;
  }

  public ButtLifterTalonSubsystem() {
    buttLifter = new WPI_TalonSRX(4);
    rollers = new WPI_TalonSRX(2);
  }

  public void setButtLifterSpeed(double speed) {
    buttLifter.set(speed);
    Dashboard.getInstance().putNumber(false, "Butt Lifter Speed Talon", speed);
  }

  public void setRollerSpeed(double speed) {
    rollers.set(speed);
    Dashboard.getInstance().putNumber(false, "Roller Speed Talon", speed);
  }

  public void setBrake(boolean brake)
  {
    if(brake)
    {
      buttLifter.setNeutralMode(NeutralMode.Brake);
      rollers.setNeutralMode(NeutralMode.Brake);
    }
    else
    {
      buttLifter.setNeutralMode(NeutralMode.Coast);
      rollers.setNeutralMode(NeutralMode.Coast);
    }
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
