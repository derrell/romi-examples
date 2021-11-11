package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Spark;

public class Robot extends TimedRobot
{
  private int ticks; // number of times autonomousPeriodic has been entered
  private Spark leftMotor;  // left motor controller
  private Spark rightMotor; // right motor controller
  private XboxController driverController;  // XBox controller for user input

  
  /** This function is called once when the robot is started up */
  @Override
  public void robotInit()
  {
    // The Romi has the left and right motors set to
    // PWM channels 0 and 1 respectively
    leftMotor = new Spark(0); // Spark is a type of motor controller
    rightMotor = new Spark(1); // Spark is a type of motor controller

    // Connect to the XBox controller
    driverController = new XboxController(0);
  }

  /** This function is called periodically (every 20ms) while in teleop mode */
  @Override
  public void teleopPeriodic()
  {
    if (driverController.getAButton())
    {
      leftMotor.set(0.5);
      rightMotor.set(-0.5);
    }
    else if (driverController.getBButton())
    {
      leftMotor.set(-0.5);
      rightMotor.set(0.5);
    }
    else if (driverController.getXButton())
    {
      leftMotor.set(-0.5);
      rightMotor.set(-0.5);
    }
    else if (driverController.getYButton())
    {
      leftMotor.set(0.5);
      rightMotor.set(0.5);
    }
    else
    {
      leftMotor.set(0);
      rightMotor.set(0);
    }
  }

  /** this function is called once each time autonomous mode is entered */
  @Override
  public void autonomousInit()
  {
    ticks = 0;
  }

  /** This function is called periodically (every 20 ms) while in autonomous mode */
  @Override
  public void autonomousPeriodic()
  {
    // Have we reached our time limit yet?
    // 50 ticks per second, for 3 seconds
    if (ticks++ >= 50 * 3)
    {
      // Yup. Stop both motors
      leftMotor.set(0);
      rightMotor.set(0);
    }
    else
    {
      // Not yet at time limit. Keep moving
      leftMotor.set(0.5);
      rightMotor.set(-0.5);
    }
  }
}
