//Leilanie

package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotParametersPT;

@Autonomous(name="AutoParkLeft", group="Autonomous")
public class AutoParkLeft extends LinearOpMode {


    private final ElapsedTime runtime = new ElapsedTime();
    int phase = 0;
    //hello world; we are about to override
    @Override
    public void runOpMode(){
        RobotParametersPT params = new RobotParametersPT();
        Robot myRobot = new Robot(params, hardwareMap, true, false, false, true);
        telemetry.addData("Status", "Initialized");
        telemetry.addData("yaw ", myRobot.driveTrain.getYaw());
        telemetry.update();

        // Wait for the start button to be pressed
        waitForStart();

        // Autonomous: Parking in observation zone
        telemetry.addData("Status", "Running");
        telemetry.update();

        runtime.reset();
        phase = 0;

       /* while(opModeIsActive()){
            telemetry.addData("yaw 1",myRobot.driveTrain.getYaw());
            telemetry.update();
        }*/
        while (opModeIsActive()){
            //myRobot.driveStraight(params.defaultDrivePower*params.powerReduction);
            //sleep(1500);

            //Go forward 41 in
            myRobot.driveTrain.driveStraight(RobotParametersPT.defaultDrivePower * RobotParametersPT.powerReduction, 41.0);
            while (myRobot.driveTrain.FrontLeftDCMotor.isBusy()) {}
            myRobot.driveTrain.stop();
            sleep(3000);
            //Turn right to angle 90, using gyro
            myRobot.driveTrain.turnRightByGyro(-90, RobotParametersPT.defaultDrivePower * RobotParametersPT.powerReduction);
            while (myRobot.driveTrain.FrontLeftDCMotor.isBusy()) {
                telemetry.addData("yaw 1", myRobot.driveTrain.getYaw());
                telemetry.update();
                sleep(500);
            }
            myRobot.driveTrain.stop();
            sleep(3000);

            //Go straight just a little bit to make sure its in the space
            myRobot.driveTrain.driveStraight(RobotParametersPT.defaultDrivePower * RobotParametersPT.powerReduction, 7.0);
            while (myRobot.driveTrain.FrontLeftDCMotor.isBusy()) {}
            myRobot.driveTrain.stop();
            sleep(1000);

            telemetry.addData("Arm Position #1", myRobot.arm.getCurrentPosition(myRobot.arm.ArmMotor1));
            telemetry.update();
            telemetry.addData("Arm Position #2", myRobot.arm.getCurrentPosition(myRobot.arm.ArmMotor2));
            telemetry.update();

            myRobot.arm.moveArm(150);
            while (myRobot.arm.ArmMotor1.isBusy()) {
                telemetry.addData("Arm Position #1", myRobot.arm.getCurrentPosition(myRobot.arm.ArmMotor1));
                telemetry.update();
                telemetry.addData("Arm Position #2", myRobot.arm.getCurrentPosition(myRobot.arm.ArmMotor2));
                telemetry.update();
            }
            myRobot.driveTrain.stop();
            sleep(1000);

            telemetry.addData("Arm Position #1", myRobot.arm.getCurrentPosition(myRobot.arm.ArmMotor1));
            telemetry.update();
            telemetry.addData("Arm Position #2", myRobot.arm.getCurrentPosition(myRobot.arm.ArmMotor2));
            telemetry.update();

            break;

        }

    }

}

