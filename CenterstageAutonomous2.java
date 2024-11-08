// 
// package org.firstinspires.ftc.teamcode;
// 
// import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
// import com.qualcomm.robotcore.hardware.GyroSensor;
// import com.qualcomm.hardware.bosch.BNO055IMU;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import org.firstinspires.ftc.teamcode.Base.AutoRobotStruct;
// import com.qualcomm.robotcore.hardware.ColorSensor;
// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
// import org.firstinspires.ftc.vision.VisionPortal;
// import org.firstinspires.ftc.vision.tfod.TfodProcessor;
// import android.graphics.Color;
// import java.util.List;
// 
// //  New imports
// import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
// import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
// import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
// import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
// import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
// import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
// import org.firstinspires.ftc.robotcore.external.navigation.Position;
// import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
// import java.util.Locale;
// 
// import static java.lang.Double.parseDouble;
// 
// @Autonomous(name = "CenterstageAutonomous2")
// public class CenterstageAutonomous2 extends AutoRobotStruct {
// 
//     private double x;
//     private double y;
//     private VisionDetectorOne visionDetector;
// 
//     double heading;
//     private GyroSensor gyroSensor;
//     Orientation angles;
//     Acceleration gravity;
// 
// 
//     @Override
//     public void runOpMode() {
//         visionDetector = new VisionDetectorOne(this);
//         colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
// 
//         visionDetector.startDetection();
//         initRunner();
// 
//      // init internal expansion hub gyro
//             // Initialize IMU
//             gyroSensor = hardwareMap.get(GyroSensor.class, "gyroSensor");
// 
//         // Calibrate gyro (if necessary)
//         gyroSensor.calibrate();
//         while (gyroSensor.isCalibrating()) {
//             telemetry.addData("Status", "Calibrating gyro...");
//             telemetry.update();
//         }
//         waitForStart();
//         // AutoCVCMD.stopStream();
// 
//         while (opModeIsActive()) {
//             List<Recognition> recognitions = visionDetector.getRecognitions();
//             visionDetector.telemetryTfod(this);
//             
//             // get initial orientation
//             double currentAngle = gyroSensor.getHeading();
// 
//             telemetry.update();
//             sleep(20);
// 
//             if (recognitions.size() > 0) {
//                 Recognition recognition = recognitions.get(0);
//                 x = (recognition.getLeft() + recognition.getRight()) / 2;
//                 y = (recognition.getTop() + recognition.getBottom()) / 2;
// 
//                 // Movement logic based on the detected position
//                 if (x > -10 && x < 290) {
//                     telemetry.addData("Left position", ".");
//                     telemetry.addData("Line = ", atLine);
//                     position = 1;
//                     sleep(250);
//                     visionDetector.stopDetection();
//                 } else if (x > 300 && x < 575) {
//                     telemetry.addData("Middle position", ".");
//                     telemetry.addData("Line = ", atLine);
//                     position = 2;
//                     sleep(250);
//                     visionDetector.stopDetection();
//                 } else {
//                     telemetry.addData("Right position", ".");
//                     telemetry.addData("Line = ", atLine);
//                     position = 3;
//                     sleep(250);
//                     visionDetector.stopDetection();
//                 }
// 
//                 // Move to the line
//                 while (!atLine && position > 0) {
//                     setDriverMotorPower(0.15, 0.15, 0.15, 0.15);
//                     telemetry.addData("OFF", "LINE");
//                     telemetry.update();
//                     float[] hsvValues = readColor(colorSensor);
//                      if (hsvValues[0] < 25) {
//                         atLine = true;
//                         setDriverMotorZero();
//                         break; // Exit the loop after reaching the line
//                     } else {
//                         atLine = false;
//                     }
// 
//                     // Add a sleep to prevent continuous updates and provide time for the robot to stop
//                     sleep(100);
//                 }
//                 }
//             //  Check to make sure heading is still at zero degrees
//              double currentPosition = currentAngle;
//             while (currentPosition < -1.0 || currentPosition > 1.0) {
//                 // self centering
//                 telemetry.addData("heading", currentPosition);
//                 telemetry.update();
//                 if (currentPosition > 1.0) {
//                     telemetry.addData("heading", currentPosition);
//                     telemetry.update();
//                     setDriverMotorPower(0.20, -0.20, 0.20, -0.20);
//                     currentPosition = currentAngle;
//                 }
// 
//                 if (currentPosition < -1.0) {
//                     telemetry.addData("heading", currentPosition);
//                     telemetry.update();
//                     setDriverMotorPower(-0.20, 0.20, -0.20, 0.20);
//                     currentPosition = currentAngle;
//                 }
//                 
//                     // Check for the line detection condition
//                     
//             }
// 
//             // Update telemetry data
//             if (position == 1) {
//                 while (currentAngle < 90) {
//                     setDriverMotorPower(-0.20, 0.20, -0.20, 0.20);
//                 }
//             }
//             telemetry.addData("At Line: ", atLine);
//             telemetry.addData("X: ", x);
//             telemetry.addData("Y: ", y);
//             telemetry.update();
//         }
//     }
//     public float[] readColor(ColorSensor colorSensor) {
//         float hsvValues[] = {0F, 0F, 0F};
//         colorSensor.enableLed(true); // Assuming you want to turn ON the LED
//         Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
//         return hsvValues;
//     }
// }