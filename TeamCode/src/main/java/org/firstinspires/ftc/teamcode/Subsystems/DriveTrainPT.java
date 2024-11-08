//Leilanie

package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Hardware.RobotParametersPT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DriveTrainPT {
    private RobotParametersPT params;
    public DcMotor FrontLeftDCMotor;
    public DcMotor FrontRightDCMotor;
    public DcMotor BackLeftDCMotor;
    public DcMotor BackRightDCMotor;
    private IMU imu;
    private YawPitchRollAngles orientation;


    public DriveTrainPT(RobotParametersPT params, HardwareMap hardwareMap) {
        this.params = params;
        // Initialize drive motors
        FrontLeftDCMotor = hardwareMap.get(DcMotor.class, params.frontLeftMotorName);
        FrontRightDCMotor = hardwareMap.get(DcMotor.class, params.frontRightMotorName);
        BackLeftDCMotor = hardwareMap.get(DcMotor.class, params.backLeftMotorName);
        BackRightDCMotor = hardwareMap.get(DcMotor.class, params.backRightMotorName);

        // Set motor directions
        FrontLeftDCMotor.setDirection(DcMotor.Direction.REVERSE);
        FrontRightDCMotor.setDirection(DcMotor.Direction.FORWARD);
        BackLeftDCMotor.setDirection(DcMotor.Direction.REVERSE);
        BackRightDCMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontLeftDCMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontLeftDCMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));

        imu.resetYaw();

        FrontRightDCMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRightDCMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set all motors to brake when power is zero
        FrontLeftDCMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightDCMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftDCMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightDCMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double drive, double strafe, double rotate) {
        FrontLeftDCMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightDCMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double frontLeftPower = (drive + strafe + rotate);
        double frontRightPower = (drive - strafe - rotate);
        double backLeftPower = (drive - strafe + rotate);
        double backRightPower = (drive + strafe - rotate);

        FrontLeftDCMotor.setPower(frontLeftPower);
        FrontRightDCMotor.setPower(frontRightPower);
        BackLeftDCMotor.setPower(backLeftPower);
        BackRightDCMotor.setPower(backRightPower);
    }

    public void driveStraight(double power) {
        FrontLeftDCMotor.setPower(power);
        FrontRightDCMotor.setPower(power);
        BackLeftDCMotor.setPower(power);
        BackRightDCMotor.setPower(power);
    }

    public void turnLeft(double power) {
        FrontLeftDCMotor.setPower(-power);
        FrontRightDCMotor.setPower(power);
        BackLeftDCMotor.setPower(-power);
        BackRightDCMotor.setPower(power);
    }

    public void turnRight(double power) {
        FrontLeftDCMotor.setPower(power);
        FrontRightDCMotor.setPower(-power);
        BackLeftDCMotor.setPower(power);
        BackRightDCMotor.setPower(-power);
    }

    public void stop() {
        FrontLeftDCMotor.setPower(0);
        FrontRightDCMotor.setPower(0);
        BackLeftDCMotor.setPower(0);
        BackRightDCMotor.setPower(0);
    }

    public double getYaw() {
        orientation = imu.getRobotYawPitchRollAngles();
        double currentYaw = orientation.getYaw(AngleUnit.DEGREES);

        return currentYaw;

    }

    public void turnRightByGyro(double angle, double power) {
        //Its important to run without encoder bc encoders doesnt work on turning
        FrontLeftDCMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightDCMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (angle < getYaw()) {
            FrontLeftDCMotor.setPower(power);
            FrontRightDCMotor.setPower(-power * 0.75);
            BackLeftDCMotor.setPower(power);
            BackRightDCMotor.setPower(-power * 0.75);
            //sleep(2000);
        }
    }

    public void alignAngle(double angle, double power) {
        FrontLeftDCMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightDCMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        orientation = imu.getRobotYawPitchRollAngles();
        double currentYaw = orientation.getYaw(AngleUnit.DEGREES);

        if (currentYaw < angle) {
            //turnLeftByGyro(angle, power);
        } else {
            turnRightByGyro(angle, power);
        }
    }

    public int getNewPosition(double distance) {
        double Counts_Per_Motor_Rev = RobotParametersPT.Counts_Per_Motor_Wheel;
        double Drive_Gear_Reduction = RobotParametersPT.Drive_Gear_Reduction;
        double Wheel_Diameter = RobotParametersPT.Wheel_Diameter;
        double Counts_Per_Inch_Drive = (Counts_Per_Motor_Rev * Drive_Gear_Reduction) / (Wheel_Diameter * 3.1415);
        return (int) (distance * Counts_Per_Inch_Drive);
    }

    ArrayList<Boolean> initialized = new ArrayList<>(Arrays.asList(false, false));

    public boolean driveStraight(double power, double distance) {
        int distanceInTicks = getNewPosition(distance);
        int [] targets = new int[2];
        List<DcMotor> motors = new ArrayList<>(Arrays.asList(FrontLeftDCMotor, FrontRightDCMotor));
        boolean done = true;

        for(int i = 0; i < motors.size(); i++) {
            DcMotor motor = motors.get(i);

            if (!initialized.get(i)) {
                targets[i] = motor.getCurrentPosition() + distanceInTicks;
                motor.setTargetPosition(targets[i]);
                initialized.set(i, true);
            }

            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(power);
            if(Math.abs(motor.getCurrentPosition() - targets[i]) > 20) done = false;
        }

        BackLeftDCMotor.setPower(FrontLeftDCMotor.getPower());
        BackRightDCMotor.setPower(FrontRightDCMotor.getPower());

        if(done){
            Collections.fill(initialized, false);
        }
        return done;
    }


}
