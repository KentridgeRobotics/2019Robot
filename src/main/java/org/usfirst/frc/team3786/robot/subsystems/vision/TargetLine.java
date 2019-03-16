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
    }

    @Override
    protected void execute() {
        pixy.getLine().getMainFeatures();
        Vector[] vectors = pixy.getLine().getVectors();
        for (Vector v : vectors) {
            v.print();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}