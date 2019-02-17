package org.usfirst.frc.team3786.robot.utils;

import org.usfirst.frc.team3786.robot.subsystems.vision.Cameras;
import org.usfirst.frc.team3786.robot.subsystems.vision.PixyCamera;

import io.github.pseudoresonance.pixy2api.Pixy2Line.Vector;

public class RocketPortFinder {
    
    public static double calcTurn () {
        PixyCamera pixy = Cameras.getPixyCamera2();
        if(pixy == null) {
            return 0.0;
        }
        else {
            Vector[] vectors = pixy.getPixy().getLine().getVectors();
            if(vectors != null) {
                if(vectors.length > 0) {
                    Vector vector = vectors[0];
                    double leg1 = vector.getX0() - vector.getX1();
                    double leg2 = vector.getY1() - vector.getY0();
                    double theta = Math.toDegrees(Math.atan2(leg2, leg1));
                    return theta;
                }
                else {
                    return 0.0;
                }
            }
            else {
                return 0.0;
            }
        }
    }
}