package org.usfirst.frc.team3786.robot;

import java.util.HashMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Dashboard 
{

    private static Dashboard instance;

    public void init(boolean showDiagonistics) 
    {
        SmartDashboard.putString("init called", "true");
    }

    public void periodic() 
    {
        SmartDashboard.putBoolean("periodic.called", true);
    }

    public void putString(String key, String value) 
    {
        if (key.equals("Dashboard Mode")) 
            {
                SmartDashboard.putString(key, value);
            }
    }

    public void putNumber(String key, double value)
    {
        SmartDashboard.putNumber(key, value);
    }

        public static Dashboard getInstance() 
        {
            if (instance == null)
                instance = new Dashboard();
            return instance;
        }
}

