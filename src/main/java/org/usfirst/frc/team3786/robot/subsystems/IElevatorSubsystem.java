package org.usfirst.frc.team3786.robot.subsystems;

import org.usfirst.frc.team3786.robot.subsystems.ElevatorSubsystem.Levels;

public interface IElevatorSubsystem {

    public void safetyRun();

    public void setElevatorSpeed(double speed);

    public double getRotation();

    public void setLevel(Levels level);

    public void setLevel(double level);

    public Levels getCurrentLevel();

    public Levels getHatchLevelUp();

    public Levels getHatchLevelDown();

    public Levels getBallLevelUp();

    public Levels getBallLevelDown();

    public void incrementHatchLevel();

    public void decrementHatchLevel();

    public void incrementBallLevel();

    public void decrementBallLevel();

}