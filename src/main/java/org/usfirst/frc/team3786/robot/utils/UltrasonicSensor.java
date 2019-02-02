package org.usfirst.frc.team3786.robot.utils;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.AnalogInput;

public class UltrasonicSensor implements DistanceSensorInterface {

    private AnalogInput distSensor = new AnalogInput(Mappings.UltrasonicSensor);

    @Override
    public double getDistanceCm() {
        double calcDist;
        calcDist = ((distSensor.getAverageVoltage() / 1000) / 300) * 10; //convert to mV, then divide to find mm, finally convert to cm.
        return calcDist;
    }
}