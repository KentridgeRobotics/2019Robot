package org.usfirst.frc.team3786.robot.subsystems.vision;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team3786.robot.subsystems.vision.Cameras;

import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2Line;
import io.github.pseudoresonance.pixy2api.Pixy2Line.Vector;

public class TargetLine extends Command {

	private static TargetLine instance;

	private static final int minLineLength = 25;

	private Pixy2 pixy;

	private int frameMid = 0;

	private double lineAngle = 0.0;
	private int linePosition = 0;
	private boolean hasLine = false;

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
		frameMid = pixy.getFrameHeight() / 2;
	}

	@Override
	protected void execute() {
		pixy.getLine().getMainFeatures();
		Vector[] vectors = pixy.getLine().getVectors();
		double testAngle = 0.0;
		double testLength = 0.0;
		int testPosition = 0;
		if (vectors != null) {
			for (Vector v : vectors) {
				double x = v.getX1() - v.getX0();
				double y = v.getY1() - v.getY0();
				double angle = 90.0;
				if (x != 0)
					angle = -Math.toDegrees(Math.atan(y / x));
				double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
				int position = (v.getY0() + v.getY1()) / 2;
				if (length > testLength) {
					testLength = length;
					testAngle = angle;
					testPosition = position;
				}
			}
		}
		if (testLength >= minLineLength) {
			lineAngle = testAngle;
			hasLine = true;
			linePosition = frameMid - testPosition;
		} else {
			lineAngle = 0;
			hasLine = false;
			linePosition = frameMid;
		}
	}

	/**
	 * Gets angle of line in Pixy
	 * 
	 * @return Angle of line (Sign is the same as when graphing)
	 */
	public double getLineAngle() {
		return lineAngle;
	}

	/**
	 * Gets position of line in Pixy
	 * 
	 * @return Distance from midpoint (Top of Camera is Positive)
	 */
	public double getLinePosition() {
		return linePosition;
	}

	/**
	 * If Pixy has found a line
	 * 
	 * @return If Pixy has a line
	 */
	public boolean hasLine() {
		return hasLine;
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}