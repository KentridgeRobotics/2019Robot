package org.usfirst.frc.team3786.robot.subsystems.vision;

import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.links.Link;

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

	public Pixy2 getPixy() {
		return pixy;
	}

}