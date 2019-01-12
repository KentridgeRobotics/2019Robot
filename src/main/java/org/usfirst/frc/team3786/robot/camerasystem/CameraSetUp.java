/*
    **Nicholas Leung, Created 1/5/2019.
*/
package org.usfirst.frc.team3786.robot.camerasystem;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class CameraSetUp 
{
    private UsbCamera driveCamera; //This is the camera that'll show up on the dashboard for driving.
    
    public void driveCamInit()
    {
        driveCamera = CameraServer.getInstance().startAutomaticCapture();
        if (driveCamera != null)
        {
			driveCamera.setResolution(320, 240);
            driveCamera.setFPS(30);
            driveCamera.setWhiteBalanceManual(5000);
			driveCamera.setBrightness(50);
            driveCamera.setExposureManual(50);
		}
    }

}