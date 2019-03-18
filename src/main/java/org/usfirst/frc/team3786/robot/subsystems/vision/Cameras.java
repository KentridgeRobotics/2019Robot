package org.usfirst.frc.team3786.robot.subsystems.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import io.github.pseudoresonance.pixy2api.links.SPILink;

public class Cameras {

	private static UsbCamera drive = null;
	private static PixyCamera pixy1 = null;
	private static PixyCamera pixy2 = null;

	public static void setup() {
		initDrive();
		pixy1 = new PixyCamera(new SPILink());
		pixy2 = new PixyCamera(new SPILink(), 1);
		initLine();
	}

	public static PixyCamera getPixyCamera1() {
		return pixy1;
	}

	public static PixyCamera getPixyCamera2() {
		return pixy2;
	}

	public static void initLine() {
		TargetLine.getInstance().setRunWhenDisabled(true);
		TargetLine.getInstance().start();
	}

	public static void initDrive() {
		drive = CameraServer.getInstance().startAutomaticCapture();
		drive.setConnectVerbose(0);
		if (drive != null) {
			drive.setResolution(320, 240);
			drive.setFPS(30);
			drive.setWhiteBalanceManual(4500);
			drive.setExposureAuto();
			drive.setBrightness(50);
		}
	}

}