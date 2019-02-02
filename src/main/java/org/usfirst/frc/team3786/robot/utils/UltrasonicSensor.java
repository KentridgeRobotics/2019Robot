package org.usfirst.frc.team3786.robot.utils;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.AnalogInput;

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
        calcDist = (300.0 * (distSensor.getAverageVoltage() / 293.0)) / 10.0; 
        return calcDist;
    }
}