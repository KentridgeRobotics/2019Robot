package org.usfirst.frc.team3786.robot;

import java.util.HashMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Dashboard 
{

    private static Dashboard instance;

    private boolean shouldDisplayField(String key)
    {
        return true;
    }

    private boolean showDiagnostics;

    public void init(boolean showDiagonistics) 
    {
        this.showDiagnostics = showDiagonistics;
    }

    public void putString(String key, String value) 
    {
        if (shouldDisplayField(key)) 
            {
                SmartDashboard.putString(key, value);
            }
    }

    public void putNumber(String key, double value)
    {
        if(shouldDisplayField(key))

        {
        SmartDashboard.putNumber(key, value);
        }
    }
    
    public void putBoolean(String key, boolean value)
    {
        if(shouldDisplayField(key))
        {
            SmartDashboard.putBoolean(key, value);
        }
    }
    
    public void putData(String key, Sendable value)
    {
        if(shouldDisplayField(key))
        {
            SmartDashboard.putData(key, value);
        }
    }

        public static Dashboard getInstance() 
        {
            if (instance == null)
                instance = new Dashboard();
            return instance;
        }
}

