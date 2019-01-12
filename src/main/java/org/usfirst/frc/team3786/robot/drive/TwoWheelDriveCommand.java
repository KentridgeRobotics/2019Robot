package org.usfirst.frc.team3786.robot.drive;

import org.usfirst.frc.team3786.robot.OI;
import org.usfirst.frc.team3786.robot.Robot;

import org.usfirst.frc.team3786.robot.utils.XboxController;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TwoWheelDriveCommand {

    private static TwoWheelDriveCommand instance;

    private boolean xDisable = false;
    private boolean yDisable = false;

    public static TwoWheelDriveCommand getInstance() {
        if(instance == null)
            instance = new TwoWheelDriveCommand();
        return instance;
    }

    public TwoWheelDriveCommand() {
        //requires((Subsystem) Robot.instance.getDriveSubsystem());
    }

    protected void initialize(){
    }

    protected void execute() {
        // The drive controls
        double x = OI.getPrimaryController().getLeftStickX();
        double y = OI.getPrimaryController().getLeftStickY();
        // The turning controls
        double turn = OI.getPrimaryController().getRightStickX();
        if(OI.getPrimaryController().getRightTrigger() > 0) {
            x *= 0.7;
			y *= 0.7;
			turn *= 0.7;
        }
        if(this.xDisable)
            x = 0;
        if(this.yDisable)
            y = 0;
        SmartDashboard.putNumber("Turn", turn);
    }

}