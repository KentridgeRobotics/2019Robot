package org.usfirst.frc.team3786.robot.utils;

import java.awt.Color;

import edu.wpi.first.wpilibj.I2C;

public class LED {

	private static I2C i2c = null;

	private static int address = 0x55;

	private static byte[] buffer = new byte[4];

	public static void setup() {
		i2c = new I2C(I2C.Port.kOnboard, address);
	}

	public static void close() {
		i2c.close();
	}

	private static void send() {
		i2c.transaction(buffer, buffer.length, new byte[0], 0);
	}

	public static void setRGB(int r, int g, int b) {
		buffer[0] = (byte) r;
		buffer[1] = (byte) g;
		buffer[2] = (byte) b;
		send();
	}

	public static void setRGB(int rgb) {
		setRGB((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
	}

	public static void setColor(Color c) {
		setRGB(c.getRed(), c.getGreen(), c.getBlue());
	}

	public static void setHSV(int hue, float saturation, float brightness) {
		int rgb = Color.HSBtoRGB(((float) hue) / 360, saturation / 100, brightness / 100);
		setRGB((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
	}

	public static void setBrightness(byte brightness) {
		buffer[3] = brightness;
		send();
	}

}