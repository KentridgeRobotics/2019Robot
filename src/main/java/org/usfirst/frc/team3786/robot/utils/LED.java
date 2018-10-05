package org.usfirst.frc.team3786.robot.utils;

import java.awt.Color;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LED {

	private static int updateDivisor = 0;
	private static int hueUpdate = updateDivisor - 1;
	private static float hue = -1;
	private static float brightness = 1.0f;

	public static void setRGB(int r, int g, int b) {
		double[] data = { r, g, b };
		SmartDashboard.putNumberArray("LED", data);
	}

	public static void colorCyclePeriodic() {
		hueUpdate++;
		if (hueUpdate >= updateDivisor) {
			hue++;
			if (hue > 360) {
				hue = 0;
			}
			hueUpdate = -1;
			int rgb = Color.HSBtoRGB(hue / 360, 1.0f, brightness);
			LED.setRGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
		}
	}

	public static void setHue(float hue) {
		LED.hue = hue;
	}

	public static void setBrightness(float brightness) {
		LED.brightness = brightness;
	}

}