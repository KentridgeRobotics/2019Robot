package org.usfirst.frc.team3786.robot.commands.debug;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team3786.robot.IO;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DebugMotorController extends Command {
	private static DebugMotorController instance;

	private WPI_TalonSRX motor;
	
	private int motorId = 1;

	public static DebugMotorController getInstance() {
		if (instance == null)
			instance = new DebugMotorController();
		return instance;
	}

	public DebugMotorController() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		if (motor == null) {
			motor = new WPI_TalonSRX(1);
			motorId = 1;
		}
		double power = IO.getPrimaryController().getLeftStickY();
		SmartDashboard.putNumber("DEBUG.MOTOR_ID", motorId);
		SmartDashboard.putNumber("DEBUG.MOTOR_POWER", power);
		motor.set(power);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
	
	public void setId(int id) {
		if (id < 1)
			id = 1;
		motorId = id;
		motor = new WPI_TalonSRX(motorId);
	}

	public int getId() {
		return motorId;
	}

	public void increment() {
		motorId++;
		motor = new WPI_TalonSRX(motorId);
	}
	
	public void decrement() {
		motorId--;
		if (motorId < 1) {
			motorId = 1;
		}
		motor = new WPI_TalonSRX(motorId);
	}
}
