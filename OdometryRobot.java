import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Encoder;

public class OdometryRobot extends LinearOpMode {

    // Hardware
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private DcMotor deadWheelMotor; // This is the motor for dead wheel tracking
    private Servo deadWheelServo; // Servo to control drop down mechanism

    // Odometry variables
    private double robotX = 0;
    private double robotY = 0;
    private double robotHeading = 0; // In radians
    private double deadWheelDistance = 0; // Distance measured by the dead wheels

    @Override
    public void runOpMode() {
        // Initialize hardware
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");
        deadWheelMotor = hardwareMap.get(DcMotor.class, "dead_wheel_motor");
        deadWheelServo = hardwareMap.get(Servo.class, "dead_wheel_servo");

        // Reset encoders
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        deadWheelMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        // Main loop
        while (opModeIsActive()) {
            // Control drop-down mechanism
            if (gamepad1.a) {
                deadWheelServo.setPosition(1); // Drop down
            } else if (gamepad1.b) {
                deadWheelServo.setPosition(0); // Retract
            }

            // Update odometry
            updateOdometry();

            // Drive robot based on gamepad input
            double leftPower = gamepad1.left_stick_y;
            double rightPower = gamepad1.right_stick_y;

            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);

            // Display telemetry
            telemetry.addData("X: ", robotX);
            telemetry.addData("Y: ", robotY);
            telemetry.addData("Heading: ", robotHeading);
            telemetry.update();
        }
    }

    private void updateOdometry() {
        // Basic odometry update logic
        int leftPosition = leftMotor.getCurrentPosition();
        int rightPosition = rightMotor.getCurrentPosition();

        // Calculate distance traveled by each wheel
        double leftDistance = leftPosition * WHEEL_CIRCUMFERENCE / ENCODER_TICKS_PER_REV;
        double rightDistance = rightPosition * WHEEL_CIRCUMFERENCE / ENCODER_TICKS_PER_REV;

        // Update robot position
        double deltaDistance = (leftDistance + rightDistance) / 2;
        robotX += deltaDistance * Math.cos(robotHeading);
        robotY += deltaDistance * Math.sin(robotHeading);

        // Update heading (assuming a differential drive)
        robotHeading += (rightDistance - leftDistance) / TRACK_WIDTH; // TRACK_WIDTH is the distance between wheels
    }
}
