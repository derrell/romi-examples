package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Spark;

public class Robot extends TimedRobot
{
  private Spark leftMotor;
  
  /**
   * This function is called when the robot is first started up and
   * should be used for any initialization code.
   */
  @Override
  public void teleopInit()
  {
    // The Romi has the left and right motors set to
    // PWM channels 0 and 1 respectively
    leftMotor = new Spark(0); // Spark is a type of motor controller
  }

  /** This function is called periodically (every 20ms) */
  @Override
  public void teleopPeriodic()
  {
    leftMotor.set(0.5);
  }
}
