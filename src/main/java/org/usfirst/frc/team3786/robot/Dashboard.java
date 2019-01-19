package org.usfirst.frc.team3786.robot;

import java.util.HashMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.AutonomousChooser;

public class Dashboard
{
    
    public void init(boolean showDiagonistics) {
        SmartDashboard.putString("init called", "true");
    }

    public void periodic() {
        SmartDashboard.putBoolean("periodic.called", true);
    }

    public void putString(String key, String value) {
        if (key.equals("Dashboard Mode")) {
            SmartDashboard.putString(key, value);
        }

    }
    
    private SendableChooser<Command> autonomousCommandChooser = new SendableChooser<Command>();
	public SendableChooser<Integer> autonomousThrottleChooser = new SendableChooser<Integer>();

}

