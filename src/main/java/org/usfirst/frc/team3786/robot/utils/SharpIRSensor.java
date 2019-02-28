package org.usfirst.frc.team3786.robot.utils;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AnalogInput;

public class SharpIRSensor implements DistanceSensorInterface {

    private static ArrayList<SharpIRSensor> instances = new ArrayList<SharpIRSensor>();

    private final AnalogInput analogIn;

    public SharpIRSensor(int port) {
        analogIn = new AnalogInput(port);
        instances.add(this);
    }

    public static SharpIRSensor getInstance(int id) {
        return instances.size() > id ? instances.get(id) : null;
    }

    public double getDistanceCM() { // 3.1V at 10cm to 0.4V at 80cm. 1 volt = 200cm. 0.1 volt = 20cm
        double calcDist;
        calcDist = (1 / ((0.0460526 * analogIn.getAverageVoltage()) - 0.00592104)) + 9.0;
        return calcDist;
    }

    public double getDistanceMM() {
        return getDistanceCM() * 10.0;
    }

    public double getDistanceIN() {
        return getDistanceCM() * 0.3937007874;
    }

    public double getDistanceFT() {
        return getDistanceCM() * 0.03280839895;
    }
}