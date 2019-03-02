package org.usfirst.frc.team3786.robot.utils;

import java.util.HashMap;

public class UltrasonicSensor implements DistanceSensorInterface {

    private static HashMap<Side, UltrasonicSensor> instances = new HashMap<Side, UltrasonicSensor>();

    private DistanceSensorInterface ir;
    private DistanceSensorInterface ultrasonic;

    public UltrasonicSensor(Side side, DistanceSensorInterface ir, DistanceSensorInterface ultrasonic) {
        this.ir = ir;
        this.ultrasonic = ultrasonic;
        instances.put(side, this);
    }

    public static UltrasonicSensor getInstance(Side side) {
        if (instances.containsKey(side))
            return instances.get(side);
        return null;
    }

    public double getDistanceCM() {
        if (ir.getDistanceCM() < ultrasonic.getDistanceCM())
            return ir.getDistanceCM();
        else
            return ultrasonic.getDistanceCM();
    }

    public double getDistanceMM() {
        if (ir.getDistanceMM() < ultrasonic.getDistanceMM())
            return ir.getDistanceMM();
        else
            return ultrasonic.getDistanceMM();
    }

    public double getDistanceIN() {
        if (ir.getDistanceIN() < ultrasonic.getDistanceIN())
            return ir.getDistanceIN();
        else
            return ultrasonic.getDistanceIN();
    }

    public double getDistanceFT() {
        if (ir.getDistanceFT() < ultrasonic.getDistanceFT())
            return ir.getDistanceFT();
        else
            return ultrasonic.getDistanceFT();
    }

    public enum Side {
        LEFT, RIGHT;
    }
}