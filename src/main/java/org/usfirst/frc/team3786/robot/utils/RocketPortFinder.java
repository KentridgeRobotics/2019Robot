package org.usfirst.frc.team3786.robot.utils;

import org.usfirst.frc.team3786.robot.subsystems.vision.Cameras;
import org.usfirst.frc.team3786.robot.subsystems.vision.PixyCamera;

import io.github.pseudoresonance.pixy2api.Pixy2Line;
import io.github.pseudoresonance.pixy2api.Pixy2Line.Vector;

public class RocketPortFinder {
    
    public static double calcTurn () {
        PixyCamera pixy = Cameras.getPixyCamera1();
        pixy.getPixy().setLED(255, 255, 255);
        pixy.getPixy().setLamp((byte)1,(byte)1);
        if(pixy == null) {
            System.err.println("!!!Pixy is Null. Unsuccessful!!!");
            return 0.0;
        }
        else {
            byte features = pixy.getPixy().getLine().getAllFeatures();
            if((features & Pixy2Line.LINE_VECTOR) == Pixy2Line.LINE_VECTOR) {
                Vector[] vectors = pixy.getPixy().getLine().getVectors();
                    if(vectors != null) {
                        if(vectors.length > 0) {
                            Vector vector = vectors[0];
                            double leg1 = vector.getX0() - vector.getX1();
                            double leg2 = vector.getY1() - vector.getY0();
                            double theta = Math.toDegrees(Math.atan2(leg2, leg1));
                            System.err.println("!!!Theta is " + theta + ". Successful!!!");
                            return theta;
                        }
                        else {
                            System.err.println("!!!Vector length is 0 or less. Unsuccessful!!!");
                            return 0.0;
                            }
                    }
                else {
                    System.err.println("!!!Vector is Null. Unsuccessful!!!");
                    return 0.0;
                    }
                }
            else {
                System.err.println("!!!LINE_VECTOR Byte not on. Unsuccessful!!!");
                return 0.0;
            }
        }
    }
}