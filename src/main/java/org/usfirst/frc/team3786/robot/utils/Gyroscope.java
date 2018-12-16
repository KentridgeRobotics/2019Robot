package org.usfirst.frc.team3786.robot.utils;

import org.usfirst.frc.team3786.robot.utils.BNO055.CalData;
import org.usfirst.frc.team3786.robot.utils.BNO055.opmode_t;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyroscope implements Runnable {

	private static Gyroscope instance;

	public static Gyroscope getInstance() {
		if (instance == null)
			instance = new Gyroscope();
		return instance;
	}

	private static BNO055 imu;
	private double accelX, accelY;
	private double robotAccelX, robotAccelY, robotHead;
	private double velX, velY, dispX, dispY;
	private double last, now;
	private double dT;

	public Gyroscope() {
		if (imu == null)
			imu = BNO055.getInstance(opmode_t.OPERATION_MODE_NDOF);

		velX = 0.0;
		velY = 0.0;

		dispX = 0.0;
		dispY = 0.0;

		last = System.nanoTime() / 1000000000.0;
	}

	public double getHeadingContinuous() {
		return imu.getHeadingEuler();
	}

	public double getHeading() {
		return imu.getVectorEuler()[0];
	}

	public double[] getVector() {
		return imu.getVectorEuler();
	}

	public double[] getAccel() {
		double[] result = imu.getVectorLinAccel();
		return result;
	}

	public double[] getGravity() {
		return imu.getVectorGrav();
	}

	public CalData getCalibration() {
		return imu.getCalibration();
	}

	public void run() {
		now = System.nanoTime() / 1000000000.0;
		dT = now - last;
		double velXNext;
		double velYNext;
		double dispXNext;
		double dispYNext = 0.0;
		double[] accel = getAccel();
		robotAccelX = accel[0];
		robotAccelY = accel[1];

		robotHead = getHeading();
		SmartDashboard.putNumberArray("Gyroscope", getVector());
		SmartDashboard.putNumber("Heading", robotHead);

		accelX = Math.cos(robotHead) * robotAccelX + Math.sin(robotHead) * robotAccelY;
		accelY = -Math.sin(robotHead) * robotAccelX + Math.cos(robotHead) * robotAccelY;

		velXNext = velX + accelX * dT;
		velYNext = velY + accelY * dT;

		velX = velXNext;
		velY = velYNext;

		SmartDashboard.putNumber("AccelX", accelX);
		SmartDashboard.putNumber("AccelY", accelY);
		SmartDashboard.putNumber("Time", dT);

		SmartDashboard.putNumber("VelX", velX);
		SmartDashboard.putNumber("VelY", velY);

		dispXNext = dispX + velX * dT;

		dispX = dispXNext;
		dispYNext = dispY + velY * dT;
		dispY = dispYNext;

		last = now;

	}

	public double getVelX() {
		return velX;
	}

	public double getVelY() {
		return velY;
	}

	public double getDispX() {
		return dispX;
	}

	public double getDispY() {
		return dispY;
	}

}