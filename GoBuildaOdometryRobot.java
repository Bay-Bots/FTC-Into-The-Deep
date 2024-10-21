import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class GoBuildaOdometryRobot extends LinearOpMode {

    // Hardware
    private DcMotor leftMotor;
    private DcMotor rightMotor;

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
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");

        // Reset encoders
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        // Main loop
        while (opModeIsActive()) {
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
        // Get current encoder positions
        int leftPosition = leftMotor.getCurrentPosition();
        int rightPosition = rightMotor.getCurrentPosition();

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
