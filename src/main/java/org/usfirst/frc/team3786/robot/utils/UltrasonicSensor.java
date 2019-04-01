package org.usfirst.frc.team3786.robot.utils;

import java.util.HashMap;

public class UltrasonicSensor implements DistanceSensorInterface {

	private static HashMap<Side, UltrasonicSensor> instances = new HashMap<Side, UltrasonicSensor>();

	private static boolean useIR = false;
	private static boolean useUS = true;

	private DistanceSensorInterface ir;
	private DistanceSensorInterface ultrasonic;

	public UltrasonicSensor(Side side, DistanceSensorInterface ir, DistanceSensorInterface ultrasonic) {
		this.ir = ir;
		this.ultrasonic = ultrasonic;
		instances.put(side, this);
	}

	public static UltrasonicSensor getInstance(Side side) {
		if (instances.containsKey(side))
			return instances.get(side);
		return null;
	}

	public double getDistanceCM() {
		if (useUS && useIR) {
			if (ir.getDistanceCM() < ultrasonic.getDistanceCM())
				return ir.getDistanceCM();
			else
				return ultrasonic.getDistanceCM();
		} else if (useUS)
			return ultrasonic.getDistanceCM();
		else if (useIR)
			return ir.getDistanceCM();
		return -1;
	}

	public double getDistanceMM() {
		if (useUS && useIR) {
			if (ir.getDistanceMM() < ultrasonic.getDistanceMM())
				return ir.getDistanceMM();
			else
				return ultrasonic.getDistanceMM();
		} else if (useUS)
			return ultrasonic.getDistanceMM();
		else if (useIR)
			return ir.getDistanceMM();
		return -1;
	}

	public double getDistanceIN() {
		if (useUS && useIR) {
			if (ir.getDistanceIN() < ultrasonic.getDistanceIN())
				return ir.getDistanceIN();
			else
				return ultrasonic.getDistanceIN();
		} else if (useUS)
			return ultrasonic.getDistanceIN();
		else if (useIR)
			return ir.getDistanceIN();
		return -1;
	}

	public double getDistanceFT() {
		if (useUS && useIR) {
			if (ir.getDistanceFT() < ultrasonic.getDistanceFT())
				return ir.getDistanceFT();
			else
				return ultrasonic.getDistanceFT();
		} else if (useUS)
			return ultrasonic.getDistanceFT();
		else if (useIR)
			return ir.getDistanceFT();
		return -1;
	}

	public enum Side {
		LEFT, RIGHT;
	}
}