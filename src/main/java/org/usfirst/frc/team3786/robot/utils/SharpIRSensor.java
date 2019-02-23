package org.usfirst.frc.team3786.robot.utils;

import org.usfirst.frc.team3786.robot.Mappings;

import edu.wpi.first.wpilibj.AnalogInput;

public class SharpIRSensor {

    private static SharpIRSensor instance;

    private AnalogInput IRSensor = new AnalogInput(Mappings.IRSensor);

    public static SharpIRSensor getInstance() {
        if (instance == null)
            instance = new SharpIRSensor();
        return instance;
    }

    public double getDistanceCM() { //3.1V at 10cm to 0.4V at 80cm. 1 volt = 200cm. 0.1 volt = 20cm
        double calcDist;
        calcDist = (1 / ((0.0460526 * IRSensor.getAverageVoltage()) - 0.00592104)) + 9.0;
        return calcDist;
    }
}