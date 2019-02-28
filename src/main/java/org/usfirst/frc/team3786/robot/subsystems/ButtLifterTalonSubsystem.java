/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3786.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class ButtLifterTalonSubsystem extends Subsystem {
  private static ButtLifterTalonSubsystem instance;

  private WPI_TalonSRX lifter;
  private WPI_TalonSRX rollers;

  public static ButtLifterTalonSubsystem getInstance() {
    if (instance == null)
      instance = new ButtLifterTalonSubsystem();
    return instance;
  }

  public ButtLifterTalonSubsystem() {
    lifter = new WPI_TalonSRX(Mappings.buttLifter);
    rollers = new WPI_TalonSRX(Mappings.rollers);
    lifter.setNeutralMode(NeutralMode.Brake);
    lifter.config_kP(0, 0.125);
  }

  public void setButtLifterSpeed(double speed) {
    lifter.set(speed);
    Dashboard.getInstance().putNumber(false, "Butt Lifter Speed Talon", speed);
  }

  public void setDesiredLifterPosition(double positionReal) {
    lifter.set(ControlMode.Position, positionReal);

  }

  public double lifterGetMotorOutputPercent() {
    return lifter.getMotorOutputPercent();
  }

  public int getRealLifterPosition() {
    return lifter.getSelectedSensorPosition();
  }

  public void setRollerSpeed(double speed) {
    rollers.set(speed);
    Dashboard.getInstance().putNumber(false, "Roller Speed  Talon", speed);
  }

  public void setBrake(boolean brake) {
    if (brake) {
      lifter.setNeutralMode(NeutralMode.Brake);
      rollers.setNeutralMode(NeutralMode.Brake);
    } else {
      lifter.setNeutralMode(NeutralMode.Coast);
      rollers.setNeutralMode(NeutralMode.Coast);
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
