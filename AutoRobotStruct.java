
package org.firstinspires.ftc.teamcode.Base;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import android.graphics.Color;
import com.qualcomm.robotcore.hardware.TouchSensor;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx; // Use DcMotorEx for encoder support
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.GyroSensor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

public class AutoRobotStruct extends LinearOpMode {
    public GyroSensor gyroSensor;
    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorBackLeft;
    public ColorSensor colorSensor; // CH I2C 2
    
    @Override
    public void runOpMode() throws InterruptedException {}

    public void initRunner() throws InternalError {

        motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft"); // 3
        motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontRight"); // 2
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft"); // 1
        motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight"); // 0
        WebcamName webcam1 = hardwareMap.get(WebcamName.class, "Webcam1"); // Adjust name if needed
        
        

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setDriverMotorPower(double FRightPower, double FLeftPower, double BRightPower, double BLeftPower) {
        motorFrontRight.setPower(FRightPower);
        motorFrontLeft.setPower(FLeftPower);
        motorBackLeft.setPower(BLeftPower);
        motorBackRight.setPower(BRightPower);
    }

    public void setDriverMotorPower(double FRightPower, double FLeftPower, double BRightPower, double BLeftPower, int s) {
        motorFrontRight.setPower(FRightPower);
        motorFrontLeft.setPower(FLeftPower);
        motorBackLeft.setPower(BLeftPower);
        motorBackRight.setPower(BRightPower);
        sleep(s);
    }

    public void setDriverMotorZero() {
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        sleep(100);
    }

    public void translateRight(double m) {
        motorFrontRight.setPower(-m);
        motorFrontLeft.setPower(m);
        motorBackLeft.setPower(-m);
        motorBackRight.setPower(m);
    }

    public void translateLeft(double m) {
        motorFrontRight.setPower(m);
        motorFrontLeft.setPower(-m);
        motorBackLeft.setPower(m);
        motorBackRight.setPower(-m);
    } public void driveForward (int targetPosition) {
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        motorFrontRight.setTargetPosition(targetPosition);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setPower(.15);
        
        motorFrontLeft.setTargetPosition(targetPosition);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setPower(.15);
        
        motorBackRight.setTargetPosition(targetPosition);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setPower(.15);
        
        motorBackLeft.setTargetPosition(targetPosition);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setPower(.15);
        while (motorFrontRight.isBusy() ){
            telemetry.addData("Moving to Position", "...");
            telemetry.update();
        }
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
    } public void rotateLeft() {
      int targetPosition = 1140;
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        motorFrontRight.setTargetPosition(-targetPosition);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setPower(.15);
        
        motorFrontLeft.setTargetPosition(targetPosition);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setPower(.15);
        
        motorBackRight.setTargetPosition(-targetPosition);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setPower(.15);
        

        motorBackLeft.setTargetPosition(targetPosition);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setPower(.15);
        
          while (motorFrontRight.isBusy() ){
            telemetry.addData("Moving to Position", "...");
            telemetry.update();
        }
        
    }public void rotateRight() {
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int targetPosition = 1140;
        motorFrontRight.setTargetPosition(targetPosition);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setPower(.15);
        
        motorFrontLeft.setTargetPosition(-targetPosition);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setPower(.15);
        
        motorBackRight.setTargetPosition(targetPosition);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setPower(.15);
        
        motorBackLeft.setTargetPosition(-targetPosition);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setPower(.15);
        
          while (motorFrontRight.isBusy() ){
            telemetry.addData("Moving to Position", "...");
            telemetry.update();
        }
        
    } public void driveBackward(int targetPosition) {
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        motorFrontRight.setTargetPosition(-targetPosition);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setPower(.25);
        
        motorFrontLeft.setTargetPosition(-targetPosition);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setPower(.25);
        
        motorBackRight.setTargetPosition(-targetPosition);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setPower(.25);
        
        motorBackLeft.setTargetPosition(-targetPosition);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setPower(.25);
        
          while (motorFrontRight.isBusy() ){
            telemetry.addData("Moving to Position", "...");
            telemetry.update();
        }
        
         motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
    }
}
