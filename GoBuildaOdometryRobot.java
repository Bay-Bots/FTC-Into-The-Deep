import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class GoBuildaOdometryRobot extends LinearOpMode {

    // Hardware
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

    // Odometry variables
    private double robotX = 0;
    private double robotY = 0;
    private double robotHeading = 0; // In radians

    // Constants for odometry calculations
    private static final double WHEEL_CIRCUMFERENCE = 0.1; // Example value in meters
    private static final double ENCODER_TICKS_PER_REV = 1120; // Change according to your encoder
    private static final double TRACK_WIDTH = 0.3; // Distance between left and right wheels

    @Override
    public void runOpMode() {
        // Initialize hardware
        motorFrontLeft = hardwareMap.get(DcMotor.class, "motorFrontLeft");
        motorFrontRight = hardwareMap.get(DcMotor.class, "motorFrontRight");
        motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");
        motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight");

        // Reset encoders
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        // Main loop
        while (opModeIsActive()) {
            // Update odometry
            updateOdometry();

            // Get joystick inputs for mecanum drive
            double drive = -gamepad1.left_stick_y; // Forward/reverse
            double strafe = gamepad1.left_stick_x; // Left/right
            double rotate = gamepad1.right_stick_x; // Rotation

            // Calculate motor powers
            double frontLeftPower = drive + strafe + rotate;
            double frontRightPower = drive - strafe - rotate;
            double backLeftPower = drive - strafe + rotate;
            double backRightPower = drive + strafe - rotate;

            // Normalize the powers if any exceeds 1.0
            double maxPower = Math.max(Math.abs(frontLeftPower), Math.max(Math.abs(frontRightPower), Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
            if (maxPower > 1.0) {
                frontLeftPower /= maxPower;
                frontRightPower /= maxPower;
                backLeftPower /= maxPower;
                backRightPower /= maxPower;
            }

            // Set motor powers
            motorFrontLeft.setPower(frontLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackLeft.setPower(backLeftPower);
            motorBackRight.setPower(backRightPower);

            // Display telemetry
            telemetry.addData("X: ", robotX);
            telemetry.addData("Y: ", robotY);
            telemetry.addData("Heading: ", robotHeading);
            telemetry.update();
        }
    }

    private void updateOdometry() {
        // Get current encoder positions
        int leftPosition = motorFrontLeft.getCurrentPosition(); // You can average or use other encoders for better accuracy
        int rightPosition = motorFrontRight.getCurrentPosition();

        // Calculate distance traveled by each wheel
        double leftDistance = leftPosition * WHEEL_CIRCUMFERENCE / ENCODER_TICKS_PER_REV;
        double rightDistance = rightPosition * WHEEL_CIRCUMFERENCE / ENCODER_TICKS_PER_REV;

        // Update robot position
        double deltaDistance = (leftDistance + rightDistance) / 2;
        robotX += deltaDistance * Math.cos(robotHeading);
        robotY += deltaDistance * Math.sin(robotHeading);

        // Update heading
        robotHeading += (rightDistance - leftDistance) / TRACK_WIDTH; // Update heading based on wheel distance
    }
}
