package org.usfirst.frc.team3786.robot.subsystems.vision;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.command.Command;

import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.Link;

public class TargetBall extends Command {

    private static Pixy2 pixy;

    public TargetBall(Link link) {
        pixy = Pixy2.createInstance(link);
        pixy.init();
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Block largestBlock = null;
        pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, 1);
        ArrayList<Block> blocks = pixy.getCCC().getBlocks();
        
        for(Block block : blocks) {
            if(largestBlock == null) {
                largestBlock = block;
            } else if(block.getWidth() > largestBlock.getWidth()) {
                largestBlock = block;
            }
        }
        System.out.println("[!] BALL X: " + largestBlock.getX());
        System.out.println("[!] BALL Y: " + largestBlock.getY());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        
    }

}