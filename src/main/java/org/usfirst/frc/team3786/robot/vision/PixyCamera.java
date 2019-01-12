package org.usfirst.frc.team3786.robot.vision;

import java.util.ArrayList;

import org.usfirst.frc.team3786.robot.utils.pixy2.Pixy2;
import org.usfirst.frc.team3786.robot.utils.pixy2.Pixy2CCC;
import org.usfirst.frc.team3786.robot.utils.pixy2.Pixy2CCC.Block;
import org.usfirst.frc.team3786.robot.utils.pixy2.links.Link;

public class PixyCamera {

    private final Pixy2 pixy;

    public PixyCamera(Link link) {
        pixy = Pixy2.createInstance(link);
        pixy.init();
    }

    public PixyCamera(Link link, int arg) {
        pixy = Pixy2.createInstance(link);
        pixy.init(arg);
    }

    public void run() {
        pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, 25);
        ArrayList<Block> blocks = pixy.getCCC().getBlocks();
        System.out.println("Blocks Found: " + blocks.size());
        for (Block b : blocks) {
            System.out.println(b.toString());
        }
    }

    public Pixy2 getPixy() {
        return pixy;
    }

}