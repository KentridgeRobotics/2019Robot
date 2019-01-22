package org.usfirst.frc.team3786.robot.utils;

import java.awt.Color;

import edu.wpi.first.wpilibj.I2C;

public class LED {

	private static int updateDivisor = 0;
	private static int hueUpdate = updateDivisor - 1;
	private static float hue = -1;
	private static byte brightness = (byte) 255;

	private static int address = 0x55;

	private static I2C i2c = null;

	private static byte[] currentColor = { 0, 0, 0, 0 };

	public static void setup() {
		i2c = new I2C(I2C.Port.kOnboard, address);
	}

	public void close() {
		i2c.close();
	}

	public static void setRGB(int r, int g, int b) {
		byte[] data = { brightness, (byte) r, (byte) g, (byte) b };
		currentColor = data;
		i2c.transaction(data, data.length, new byte[0], 0);
	}

	public static void setRGB(Color c) {
		setRGB(c.getRed(), c.getGreen(), c.getBlue());
	}

	public static void setHSV(int hue, float saturation, float brightness) {
		int rgb = Color.HSBtoRGB(hue / 360, saturation, brightness);
		setRGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
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
			setRGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
		}
	}

	public static void setHue(float hue) {
		LED.hue = hue;
	}

	public static void setBrightness(byte brightness) {
		LED.brightness = brightness;
		setRGB(currentColor[1], currentColor[2], currentColor[3]);
	}

}