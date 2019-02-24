package org.usfirst.frc.team3786.robot.utils;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MaxSonar implements DistanceSensorInterface {

    private static MaxSonar instance;

    private AnalogInput distSensor = null;

    public static MaxSonar getInstance() {
        if (instance == null)
            instance = new MaxSonar();
        return instance;
    }

    public double getDistanceCM() {
        double calcDist;
        SmartDashboard.putNumber("Ultrasonic Avg Bits", distSensor.getAverageBits());
        calcDist = (300.0 * (distSensor.getAverageVoltage() / 0.293)) / 10.0;
        return calcDist;
    }

    public double getDistanceMM() {
        return getDistanceCM() * 10;
    }

    public double getDistanceIN() {
        return getDistanceCM() * 0.3937007874;
    }

    public double getDistanceFT() {
        return getDistanceCM() * 0.03280839895;
    }
}