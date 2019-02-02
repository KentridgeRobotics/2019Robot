package org.usfirst.frc.team3786.robot.utils;

import org.usfirst.frc.team3786.robot.Dashboard;
import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UltrasonicSensor implements DistanceSensorInterface {

    private static UltrasonicSensor instance;

    private AnalogInput distSensor = new AnalogInput(Mappings.UltrasonicSensor);

    public static UltrasonicSensor getInstance() {
		if (instance == null)
			instance = new UltrasonicSensor();
		return instance;
	}

    @Override
    public double getDistanceCm() {
        double calcDist;
        SmartDashboard.putNumber("Ultrasonic Avg Bits", distSensor.getAverageBits());
        calcDist = (300.0 * (distSensor.getAverageVoltage() / 0.293)) / 10.0; 
        return calcDist;
    }
}