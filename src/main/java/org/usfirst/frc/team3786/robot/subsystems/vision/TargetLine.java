package org.usfirst.frc.team3786.robot.subsystems.vision;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3786.robot.subsystems.vision.Cameras;

import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2Line;
import io.github.pseudoresonance.pixy2api.Pixy2Line.Vector;

public class TargetLine extends Command {

    private static TargetLine instance;
    private Pixy2 pixy;

    public static TargetLine getInstance() {
        if (instance == null)
            instance = new TargetLine();
        return instance;
    }

    public TargetLine() {
    }

    @Override
    protected void initialize() {
        pixy = Cameras.getPixyCamera1().getPixy();
        pixy.getLine().setMode(Pixy2Line.LINE_MODE_WHITE_LINE);
        pixy.setLamp((byte) 1, (byte) 1);
        pixy.setLED(255, 255, 255);
    }

    @Override
    protected void execute() {
        pixy.getLine().getMainFeatures();
        Vector[] vectors = pixy.getLine().getVectors();
        if (vectors != null) {
            for (Vector v : vectors) {
                double x = v.getX1() - v.getX0();
                double y = v.getY1() - v.getY0();
                double angle = 0.0;
                if (y != 0)
                    angle = Math.toDegrees(Math.atan(x / y));
                System.out.println("Vector " + v.getIndex() + ": " + angle);
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}