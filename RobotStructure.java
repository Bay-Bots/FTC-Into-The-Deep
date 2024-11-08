package org.firstinspires.ftc.teamcode.Base;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.TouchSensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

import static java.lang.Double.parseDouble;

public class RobotStructure extends OpMode {



// INITIALIZATIONS ---------
// Assign names to parts for code
// Format: {Part Type} {Part Name in code};
// public / private is optional

    
    DcMotor motorFrontRight; // Control hub 1 = 2
    DcMotor motorFrontLeft;  // Control hub 1 = 1
    DcMotor motorBackRight;  // Control hub 1 = 0
    DcMotor motorBackLeft;   // Control hub 1 = 3
 // public DcMotor Linear_Slide;
    public TouchSensor limit1;
    public TouchSensor limit2;
    public DcMotor tapeMotor;   //  Expansion 2
    public DcMotor chainMotor;   //  Expansion  1
    public TouchSensor touch;
 //   public DcMotor Bucket1;
 //   public DcMotor Bucket2; 
    
// -------------------------
    @Override
    // All code in here will run whenever a TeleOp is initialized. 
    public void init() {
// Assign names to parts in Driver Hub configuration
// Format: {Part name in code} = hardwareMap.get({Part Type}.class, "{Name in driver hub}") 
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft"); 
        motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontRight"); 
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft"); 
        motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight"); 
        
      //  Bucket1 = harwareMap.get(DcMotor.class, "Bucket1");
      //  Linear_Slide = hardwareMap.get(DcMotor.class, "Linear_Slide");

        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        touch = hardwareMap.get(TouchSensor.class, "touch");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);


    }

    @Override
   public void loop() {}

    // This code is the structure for the driver control.
    public void initDriver() {
    float gamepad1LeftY = gamepad1.left_stick_y;
    float gamepad1LeftX = gamepad1.left_stick_x;
    float gamepad2RightY = gamepad2.right_stick_y;
    float gamepad1RightX = -gamepad1.right_stick_x;
    float gamepad2LeftY = gamepad2.left_stick_y;

    // Adjust the signs for translation
    float translation = -gamepad1LeftX;
    float rotation = gamepad1RightX;

    float frontRightPower = -gamepad1LeftY + translation + rotation;
    float frontLeftPower = -gamepad1LeftY - translation - rotation;
    float backLeftPower = -gamepad1LeftY + translation - rotation;
    float backRightPower = -gamepad1LeftY - translation + rotation;
    float armPower = gamepad2RightY;
    // float chainPower = gamepad2LeftY / 2;

    motorFrontLeft.setPower(-frontLeftPower / 1.5);
    motorBackLeft.setPower(-backLeftPower / 1.5);
    motorFrontRight.setPower(-frontRightPower / 1.5);
    motorBackRight.setPower(-backRightPower / 1.5);

        if (gamepad1.dpad_down) {
            setDriverMotorPower(-0.25, -0.25, -0.25, -0.25);
        } else if (gamepad1.dpad_up) {
            setDriverMotorPower(0.25, 0.25, 0.25, 0.25);
        } else if (gamepad1.dpad_left) {
            translateLeft(0.25);
        } else if (gamepad1.dpad_right) {
            translateRight(0.25);
        } 
    }  public void setDriverMotorPower(double FRightPower, double FLeftPower, double BRightPower, double BLeftPower) {
        motorFrontRight.setPower(FRightPower);
        motorFrontLeft.setPower(FLeftPower);
        motorBackLeft.setPower(BLeftPower);
        motorBackRight.setPower(BRightPower);
    }

    public void setDriverPowerZERO() {
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
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
    
    } public void quickStop() {
        tapeMotor.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
    }
}
