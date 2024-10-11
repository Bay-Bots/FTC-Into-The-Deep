package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Base.AutoRobotStruct;
import org.gobuilda.odometry.Odometry;
import org.gobuilda.odometry.OdometryConfig;

@Autonomous(name = "Threads Test with Odometry")
public class ThreadsTest extends AutoRobotStruct {

    private Thread drivetrainThread;
    private Thread armThread;
    private boolean running = true;

    private Odometry odometry;

    @Override
    public void init() {
        super.initRunner(); // Initialize the hardware from AutoRobotStruct

        // Initialize odometry
        OdometryConfig config = new OdometryConfig();
        config.setLeftMotor(motorBackLeft);  // Adjust motor mapping
        config.setRightMotor(motorBackRight); // Adjust motor mapping
        config.setEncoderCountPerRevolution(1120); // Adjust based on your encoder specs
        odometry = new Odometry(config);
    }

    @Override
    public void start() {
        // Start the drivetrain thread
        drivetrainThread = new Thread(new DrivetrainTask());
        drivetrainThread.start();

        // Start the arm control thread
        armThread = new Thread(new ArmTask());
        armThread.start();
    }

    @Override
    public void loop() {
        // Update odometry
        odometry.update();

        // Telemetry data
        telemetry.addData("Status", "Running");
        telemetry.addData("X Position", odometry.getX());
        telemetry.addData("Y Position", odometry.getY());
        telemetry.addData("Heading", odometry.getHeading());
        telemetry.update();
    }

    @Override
    public void stop() {
        // Stop the threads when the OpMode stops
        running = false;
        try {
            drivetrainThread.join(); // Wait for the drivetrain thread to finish
            armThread.join();        // Wait for the arm thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DrivetrainTask implements Runnable {
        @Override
        public void run() {
            double targetX = 50; // Example target X position
            double targetY = 50; // Example target Y position

            setDriverMotorPower(1.0, 1.0, 1.0, 1.0); // Start moving

            while (running && !reachedTarget(targetX, targetY)) {
                // Keep driving until the target is reached
            }

            setDriverMotorZero(); // Stop the motors
        }

        private boolean reachedTarget(double targetX, double targetY) {
            double currentX = odometry.getX();
            double currentY = odometry.getY();
            double threshold = 5; // Define how close to the target you need to be

            return Math.abs(currentX - targetX) < threshold && Math.abs(currentY - targetY) < threshold;
        }
    }

    private class ArmTask implements Runnable {
        @Override
        public void run() {
            // Example: Move the arm for 2 seconds
            Arm1.setPower(0.5); // Assuming Arm1 is the motor you want to control
            try {
                Thread.sleep(2000); // Move the arm for 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Stop the arm motor
            Arm1.setPower(0);
        }
    }
}
