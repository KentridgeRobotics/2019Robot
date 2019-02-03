/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.usfirst.frc.team3786.robot.Dashboard;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class ButtLifterCANSparkSubsystem extends Subsystem {
  private static ButtLifterCANSparkSubsystem instance;
 
  private CANSparkMax buttLifter;
  private CANSparkMax rollers;

  public static ButtLifterCANSparkSubsystem getInstance() {
    if(instance == null)
      instance = new ButtLifterCANSparkSubsystem();
    return instance;
  }

  public ButtLifterCANSparkSubsystem() {
    buttLifter = new CANSparkMax(222, MotorType.kBrushless); //222 is placeholder will change later
    rollers = new CANSparkMax(555, MotorType.kBrushless); //555 is placeholder will change later
  }

  public void setButtLifterSpeed(double speed) {
    buttLifter.set(speed);
    Dashboard.getInstance().putNumber(false, "Butt Lifter Speed CANSpark", speed);
  }

  public void setRollerSpeed(double speed) {
    rollers.set(speed);
    Dashboard.getInstance().putNumber(false, "Roller Speed CANSpark", speed);
  }

  public void setBrake(boolean brake) {
    if(brake)
    {
      buttLifter.setIdleMode(IdleMode.kBrake);
      rollers.setIdleMode(IdleMode.kBrake);
    }
    else
    {
      buttLifter.setIdleMode(IdleMode.kCoast);
      rollers.setIdleMode(IdleMode.kCoast);
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
