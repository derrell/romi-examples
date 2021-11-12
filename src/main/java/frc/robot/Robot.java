package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Encoder;

public class Robot extends TimedRobot
{
  // Constants
  final double WHEEL_DIAMETER_INCHES = 2.75591; // 70 mm
  final double TICKS_PER_REVOLUTION = 1440.0;
  final double INCHES_TO_MOVE = 24.0;
  final double MOTOR_EQUALIZER_RATIO = 1.081;   // > 0 makes R motor power greater than L

  // Member variables
  final double motorPower = 0.5; // power level to apply to motor [ 0, 1.0 ] (negated for backwards)
  double ticksAtDestination; // tick count when arriving at destination, in autonomous
  private Spark leftMotor;  // left motor controller
  private Spark rightMotor; // right motor controller
  private XboxController driverController;  // XBox controller for user input
  private Encoder leftEncoder;
  private Encoder rightEncoder;

  
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

    // The Romi has onboard encoders that are hardcoded
    // to use DIO (digitil IO) pins 4/5 and 6/7 for the
    // left and right
    leftEncoder = new Encoder(4, 5);
    rightEncoder = new Encoder(6, 7);

    // Connect to the XBox controller
    driverController = new XboxController(0);
  }

  /** this function is called once each time teleop mode is entered */
  @Override
  public void teleopInit()
  {
    // Reset the two relative encoders back to zero
    leftEncoder.reset();
    rightEncoder.reset();
  }

  /** This function is called periodically (every 20ms) while in teleop mode */
  @Override
  public void teleopPeriodic()
  {
    if (driverController.getAButton())
    {
      System.out.println("A");
      leftMotor.set(motorPower);
      rightMotor.set(motorPower * MOTOR_EQUALIZER_RATIO);
    }
    else if (driverController.getBButton())
    {
      System.out.println("B");
      leftMotor.set(-motorPower);
      rightMotor.set(-motorPower * MOTOR_EQUALIZER_RATIO);
    }
    else if (driverController.getXButton())
    {
      leftMotor.set(-motorPower);
      rightMotor.set(motorPower * MOTOR_EQUALIZER_RATIO);
    }
    else if (driverController.getYButton())
    {
      leftMotor.set(motorPower);
      rightMotor.set(-motorPower * MOTOR_EQUALIZER_RATIO);
    }
    else
    {
      leftMotor.set(0);
      rightMotor.set(0);
    }

    System.out.println("Encoder L: " + leftEncoder.get() + " R: " + rightEncoder.get());
  }

  /** this function is called once each time autonomous mode is entered */
  @Override
  public void autonomousInit()
  {
    double circumference;
    double fullRotationsNeeded;

    // Reset the two relative encoders back to zero
    leftEncoder.reset();
    rightEncoder.reset();

    // Calculate how many ticks are needed to go the specified INCHES_TO_MOVE
    circumference = WHEEL_DIAMETER_INCHES * Math.PI;
    fullRotationsNeeded = INCHES_TO_MOVE / circumference;
    ticksAtDestination = Math.round(TICKS_PER_REVOLUTION * fullRotationsNeeded);

    // Start the Romi moving forward
    leftMotor.set(motorPower);
    rightMotor.set(motorPower * MOTOR_EQUALIZER_RATIO);
  }

  /** This function is called periodically (every 20 ms) while in autonomous mode */
  @Override
  public void autonomousPeriodic()
  {
    // Have we reached our first destination?
    if (leftEncoder.get() >= ticksAtDestination)
    {
      // Yup. Reverse direction
      leftMotor.set(-motorPower);
      rightMotor.set(-motorPower * MOTOR_EQUALIZER_RATIO);
    }

    // Have we reached our origin?
    if (leftEncoder.get() < 0)
    {
      // Yup. Turn off the motors.
      leftMotor.set(0);
      rightMotor.set(0);
    }
  }
}
