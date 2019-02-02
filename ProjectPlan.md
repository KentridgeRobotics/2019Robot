# Project Plan for 2019 Robot

## Ideas

- Climber system to get onto the hab third level
- Visual recognition of vision targets on hatches.
- Drive train controls: teleop steering and throttle
- Brake button on the xbox controller
- Turbo button to go faster
- Or switch to toggle between fast and slow, or a gearshift
- If the robot has multiple gears, a real gearshift.
- Recognize lines on the field (maybe using infrared sensors on the bottom of the robot)
- Calibrate and test visual recognition
- Small automatic functions: place a hatch cover, place a cargo ball, pick up hatch cover, pick up ball
- Make dashboard configuration for competition that only has information useful to the driver (no diagnostics)


## Schedule

1. Driving, gear shifts, turbo, brakes

Should have standard controls (throttle, steering) on driver's controller, plus
a brake button and a turbo button. (Maybe also a "gear shift" that lets us switch between high-speed and
low-speed mode.)

Gunner's controller should have buttons for raising arm to preset levels and for lining up precisely with a hatch.
Picking up a ball?
Picking up a port cover?

Driving onto the high platform?

Brushless motor controllers MUST use the setSmartCurrentLimit() method of CANSparkMax, to avoid burning out the NEOs.

2. Create diagnostic vs. competition dashboard

Competition mode: only the important stuff is displayed: camera, autonomous settings (are there any?), current auto action that is running, current target, current speed settings

Diagnostic mode: lots of extra stuff: distance to target, direction to target

3. Visual sensing, image recognition

First idea for how to line up: first, drive to the outside end of the nearest piece of tape on the ground, and then turn
to face the vision targets. That should put us in the correct alignment.

Direction and distance to the end of the nearest tape line. 
Direction and distance to the vision targets.

4. Climbing

- Turn all motors to brake mode
- Lift front grabber
- drive forward until close enough
- throttle to zero
- (PARALLEL) Push down on back and front lifters at the same speed. Use gyroscope to keep the bot level.
- Stop lifting when we're high enough
- Drive forward using the rear rollers and the drive wheels on very low speed
- Stop when lifter is close enough
- Pull up the lifter and raise the grabber
- drive forward a bit more and stop

5. Grabber

- In the grabber close command, we can use getOutputCurrent() (Talon method) to determine when the motor stalls (current will go up), and back off on the throttle for the motor. If the motor is holding jaws closed, it should be fine to leave it engaged at low-ish power until released (maybe).

6. Primary Controller controls

- Left Stick = Throttle
- Right Stick = Steer
- Button A = N/A
- Button B = Turbo
- Start button = Butt lift
- Left Trigger = Brake/Slow down
- Right Trigger = N/A

7. Secondary Controller controls

- Button A = Grabber in (intake)
- Button B = Grabber out
- Button X = Elevator up
- Button Y = Elevator down
- Left bumper = Elevator level += 1
- Right bumper = Elevator level -= 1
- Left trigger = Grabber open
- Right trigger = Grabber close