package org.usfirst.frc.team3786.robot.utils;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Ultrasonic;

public class HCSR04 implements DistanceSensorInterface {

    private static ArrayList<HCSR04> instances = new ArrayList<HCSR04>();

    private DigitalInput dIn;
    private DigitalOutput dOut;
    private Ultrasonic ultrasonic;

    public HCSR04(int out, int in) {
        dOut = new DigitalOutput(out);
        dIn = new DigitalInput(in);
        ultrasonic = new Ultrasonic(dOut, dIn);
        ultrasonic.setDistanceUnits(Ultrasonic.Unit.kMillimeters);
        ultrasonic.setEnabled(true);
        instances.add(this);
    }

    public static HCSR04 getInstance(int id) {
        return instances.size() > id ? instances.get(id) : null;
    }

    public static void init() {
        if (instances.size() > 0)
            instances.get(0).ultrasonic.setAutomaticMode(true);
    }

    public double getDistanceMM() {
        return ultrasonic.isRangeValid() ? ultrasonic.getRangeMM() : -1.0;
    }

    public double getDistanceCM() {
        return ultrasonic.isRangeValid() ? ultrasonic.getRangeMM() / 10 : -1.0;
    }

    public double getDistanceIN() {
        return ultrasonic.isRangeValid() ? ultrasonic.getRangeInches() : -1.0;
    }

    public double getDistanceFT() {
        return ultrasonic.isRangeValid() ? ultrasonic.getRangeInches() / 12 : -1.0;
    }

}