// package org.firstinspires.ftc.teamcode;
// import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
// import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
// import org.firstinspires.ftc.teamcode.Base.AutoRobotStruct;
// import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
// import com.qualcomm.robotcore.hardware.GyroSensor;
// import com.qualcomm.hardware.bosch.BNO055IMU;
// import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
// import com.qualcomm.robotcore.hardware.ColorSensor;
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
// import android.graphics.Color;
// import java.util.List;
// import com.qualcomm.robotcore.hardware.TouchSensor;
// import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
// import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
// import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
// import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
// @Autonomous(name = "BlueFar")
// public class BlueFar extends AutoRobotStruct {
// 
//     private double x;
//     private double y;
//     // Change VisionDetectorBlue to VisionDetectorRed to detect red cone instead.
//     private VisionDetectorBlue visionDetector;
//     private long startTime;
//     private boolean positionSet = false;
//     private ModernRoboticsI2cGyro gyroSensor;  // Use BNO055IMU for BHI260AP
//     private Orientation lastAngles = new Orientation();
//     IntegratingGyroscope gyro;
//     private float zAngle;
//     private void initializeIMU() {
//     try {
//         gyroSensor = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyroSensor");
//         gyro = (IntegratingGyroscope)gyroSensor;
//         gyroSensor.calibrate();
//         while (gyroSensor.isCalibrating()) {
//             telemetry.addData("Status", "Calibrating gyro...");
//             telemetry.update();
//             sleep(50);
//         }
// 
//         telemetry.addData("Status", "Gyro Calibration Complete");
//         telemetry.update();   
//         } catch (Exception e) {
//         telemetry.addData("Gyro Initialization Error", e.getMessage());
//     }
//     telemetry.update();
// }
// 
// 
//     public void runOpMode() {
//         visionDetector = new VisionDetectorBlue(this);
//         visionDetector.startDetection();
// 
//         initializeIMU();
//         initRunner();  // Initialize hardware components
//          // Initialize IMU
//         colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
//         dragBlock.setPosition(0.08);
// 
//         // Rest of your code...
//         waitForStart();
// 
//         startTime = System.currentTimeMillis();
//         while (opModeIsActive() && !positionSet) {
//             visionDetector.telemetryTfod(this);
// 
//             if (System.currentTimeMillis() - startTime < 5000) {
//                 List<Recognition> recognitions = visionDetector.getRecognitions();
//                 telemetry.update();
//                 sleep(20);
// 
//                 // Check if a model is detected
//                 if (recognitions.size() > 0) {
//                     Recognition recognition = recognitions.get(0);
//                     x = (recognition.getLeft() + recognition.getRight()) / 2;
//                     y = (recognition.getTop() + recognition.getBottom()) / 2;
// 
//                     // Movement logic based on the detected position
//                     if (x > -10 && x < 290) {
//                         telemetry.addData("Left position", ".");
//                         telemetry.addData("Line = ", atLine);
//                         position = 1;
//                         visionDetector.stopDetection();
//                     } else if (x > 300 && x < 575) {
//                         telemetry.addData("Middle position", ".");
//                         telemetry.addData("Line = ", atLine);
//                         position = 2;
//                         visionDetector.stopDetection();
//                     } else {
//                         telemetry.addData("Right position", ".");
//                         telemetry.addData("Line = ", atLine);
//                         position = 3;
//                         visionDetector.stopDetection();
//                     }
//                 }
//             } else {
//                 telemetry.addData("Timeout", "Detection took too long.");
//                 positionSet = true;  // Exit the loop if the detection takes too long
//                 position = 3;
//                 
//                 
//                 // ANDREW ANDREW ANDREW ANDREW above position is position chosen by default. 1, 2, or 3. Line 99. 
//                 // middle = 2, right = 3, left = 1
//             }
//             telemetry.update();
// 
//             // Additional logic based on the detected position after reaching the line
//             if (!atLine && position > 0) {
//                 driveForward(1000);
//                 moveTowardLine();
// 
//                 if (position == 1) {
//                     driveForward(100);
//                     sleep(200);
//                     turnLeft();
//                     driveForward(150);
//                     sleep(200);
//                     dragBlock.setPosition(0.5);
//                   
//                     
//                     
//                     
//                 } else if (position == 2) {
//                     // Move forward for position 2
//                    // sleep(500);
//                     driveBackward(55);
//                     dragBlock.setPosition(0.5);
//                    
//                     //2300 first
// 
//                     // moveToTouch();
// 
//                 } else if (position == 3) {
//                     driveBackward(170);
//                     turnLeft();
//                     driveBackward(900);
//                     dragBlock.setPosition(0.5);
//                
//                     // Turn right for position 3
//                     
//                     // Use the gyro to turn right
//                 }
//                 setDriverMotorZero(); 
//                 // steps for setting up autonomous, 
//                 // 1: put arm in starting positon, 
//                 // 2: put drag block in position, 
//                 // 3: put pixle in claw.
//                 sleep(20000);
//             }
//         }
//     }
// // Turning Right
// private void turnRight() {
//     while (opModeIsActive() && zAngle > -84.5) {
//         zAngle = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//         setDriverMotorPower(0.20, -0.20, 0.20, -0.20);
//         telemetry.addData("Turning Right", "Current Angle: %.2f", zAngle);
//         telemetry.update();
//         if (zAngle < -90) {
//             setDriverMotorZero();
//             return;
//         }
//     }
// 
//     setDriverMotorZero();
// }
// //turning left                                           skibity
//  private void turnLeft() {
//      while (opModeIsActive() && zAngle < 88.5) {
//          zAngle = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//          setDriverMotorPower(-0.20, 0.20, -0.20, 0.20);
//          telemetry.addData("Turning Left", "Current Angle: %.2f", zAngle);
//          telemetry.update();
// 
//          if (zAngle > 88.5) {
//              setDriverMotorZero();
//              return;
//          }
//      }
//      setDriverMotorZero();
//  }
// 
// 
// 
//      private void moveTowardLine() {
//     long startTime = System.currentTimeMillis();
//     double initialAngle = gyroSensor.getHeading();
// 
//     while (!atLine && position > 0 && opModeIsActive()) {
//       // Gyro-based correction
//       double correction = calculateCorrection(initialAngle, gyroSensor.getHeading());
// 
//    // while (System.currentTimeMillis() - startTime < 3500) {
//     //setDriverMotorPower(0.20 + correction, 0.20 - correction, 0.20 + correction, 0.20 - correction);
//     //}
// 
//     setDriverMotorPower(0.05 + correction, 0.05 - correction, 0.05 + correction, 0.05 - correction);
// 
//       telemetry.addData("Moving to ", "LINE");
// 
//       // Add telemetry to show elapsed time
//       telemetry.addData("Elapsed Time", "%d ms", System.currentTimeMillis() - startTime);
//         
//       telemetry.update();
// 
//       // Check for the line detection condition
//       float[] hsvValues = readColor(colorSensor);
//       telemetry.addData("Hue Value", "%.2f", hsvValues[0]);
// 
//       if (hsvValues[0] > 195) {
//         atLine = true;
//         setDriverMotorZero();
//         break; // Exit the loop after reaching the line
//       } else {
//         atLine = false;
//       }
// 
//       // Add a sleep to prevent continuous updates and provide time for the robot to stop
//       sleep(100);
// 
//       // Check if the elapsed time exceeds a certain threshold (e.g., 5 seconds)
//     }
//     telemetry.update();
//   }
// 
//   private double calculateCorrection(double initialAngle, double currentAngle) {
//     // Calculate the correction based on the difference between the initial and current gyro angles
//     double angleDifference = currentAngle - initialAngle;
//     // Adjust the correction factor as needed based on your robot's behavior
//     double correctionFactor = 0.005;
//     return angleDifference * correctionFactor;
//   }
// 
// 
//     public float[] readColor(ColorSensor colorSensor) {
//         float hsvValues[] = {0F, 0F, 0F};
//         colorSensor.enableLed(true); // Assuming you want to turn ON the LED
//         Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
//         return hsvValues;
//     }
// }
// 