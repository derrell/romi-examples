package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.sensor.RomiGyro;

public class Robot extends TimedRobot
{
  // Constants
  final double MOTOR_EQUALIZER_RATIO = 1.081;   // > 0 makes R motor power greater than L

  // Member variables
  final double motorPowerHigh = 0.5; // high power level to apply to motor [ 0, 1.0 ] (negated for backwards)
  final double motorPowerLow = 0.1;  // low power level to apply to motor [ 0, 1.0 ] (negated for backwards)
  double ticksAtDestination; // tick count when arriving at destination, in autonomous
  private Spark leftMotor;  // left motor controller
  private Spark rightMotor; // right motor controller
  private XboxController driverController;  // XBox controller for user input
  private RomiGyro gyro;

  
  /** This function is called once when the robot is started up */
  @Override
  public void robotInit()
  {
    // The Romi has the left and right motors set to
    // PWM channels 0 and 1 respectively
    leftMotor = new Spark(0); // Spark is a type of motor controller
    rightMotor = new Spark(1);

    // We want positive values to mean move forward, for both motors.
    // Invert the right motor since it's mounted in the opposite
    // direction as the left motor.
    rightMotor.setInverted(true);

    // Connect to the gyro
    gyro = new RomiGyro();

    // Connect to the XBox controller
    driverController = new XboxController(0);
  }

  /** this function is called once each time teleop mode is entered */
  @Override
  public void teleopInit()
  {
  }

  /** This function is called periodically (every 20ms) while in teleop mode */
  @Override
  public void teleopPeriodic()
  {
    if (driverController.getAButton())
    {
      leftMotor.set(motorPowerHigh);
      rightMotor.set(motorPowerHigh * MOTOR_EQUALIZER_RATIO);
    }
    else if (driverController.getBButton())
    {
      leftMotor.set(-motorPowerHigh);
      rightMotor.set(-motorPowerHigh * MOTOR_EQUALIZER_RATIO);
    }
    else if (driverController.getXButton())
    {
      leftMotor.set(-motorPowerHigh);
      rightMotor.set(motorPowerHigh * MOTOR_EQUALIZER_RATIO);
    }
    else if (driverController.getYButton())
    {
      leftMotor.set(motorPowerHigh);
      rightMotor.set(-motorPowerHigh * MOTOR_EQUALIZER_RATIO);
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
    // Reset the gyro so the robot's current heading is 0
    gyro.reset();

    // Start 
    leftMotor.set(motorPowerLow);
    rightMotor.set(-motorPowerLow * MOTOR_EQUALIZER_RATIO);
  }

  /** This function is called periodically (every 20 ms) while in autonomous mode */
  @Override
  public void autonomousPeriodic()
  {
    System.out.println("Gyro: X=" + Math.round(gyro.getAngleX()) + " Y=" + Math.round(gyro.getAngleY()) + " Z=" + Math.round(gyro.getAngleZ()));
  }
}
