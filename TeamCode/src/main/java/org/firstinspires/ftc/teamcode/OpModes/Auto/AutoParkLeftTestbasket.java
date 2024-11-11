//Leilanie

package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Hardware.RobotParametersPT;

@Autonomous(name="AutoParkLeftBasketTest", group="Autonomous")
public class AutoParkLeftTestbasket extends LinearOpMode {


    private RobotParametersPT params;
    private Robot myRobot;
    private ElapsedTime runtime = new ElapsedTime();
    int phase = 0;

    double driveToBasketDis = 7.0;
    boolean driveToBasket = false;
    boolean stepZero = true;
    boolean stepOne = false;
    boolean turnReached = false;
    boolean stepTwo = false;
    boolean armRaised = false;
    boolean stepThree = false;
    boolean slideRaised = false;
    boolean stepFour = false;
    boolean stepFive = false;
    boolean stepSix = false;


    //hello world; we are about to override
    @Override
    public void runOpMode() {
        params = new RobotParametersPT();
        myRobot = new Robot(params, hardwareMap, true, true, true, true);
        //telemetry = new MultipleTelemetry(telemetry,FtcDashboard.getInstance().getTelemetry());

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


        while (opModeIsActive()) {
            if (stepZero) {
                myRobot.claw.turnIn(1);
                myRobot.arm.moveArmVersion2(-350);
                //telemetry.addData("Arm telemetry", myRobot.arm.getTelemetryForArm());
                //telemetry.update();
                myRobot.slide.moveSlidesVersion2(myRobot.slide.slideStartingPosition + 2100);
                telemetry.addData("Slides telemetry", myRobot.slide.getTelemetryForSlides());
                telemetry.update();
                if (!driveToBasket) {
                    stepOne = true;
                }
            }

            //sleep(1000);
            if (stepOne) {
                myRobot.driveTrain.initializedFrontLeft = false;
                myRobot.driveTrain.initializedFrontRight = false;
                //telemetry.addData("StepOne", " tar pos " + myRobot.driveTrain.getNewPosition(11.0));
                //telemetry.update();
                //sleep(1000);
                driveToBasket = myRobot.driveTrain.driveStraightPT(params.defaultDrivePower * params.powerReduction, driveToBasketDis);
                //telemetry.addData("StepOne", "Running curr pos " + myRobot.driveTrain.FrontLeftDCMotor.getCurrentPosition());
                //telemetry.addData("StepOne", "drive reached " + driveStraightReached);
                //telemetry.update();
                //sleep(1000);

                if ((myRobot.driveTrain.FrontLeftDCMotor.getCurrentPosition() - myRobot.driveTrain.getNewPosition(driveToBasketDis)) > 20) {
                    driveToBasket = true;
                } else {
                    driveToBasket = false;
                }
               /* telemetry.addData("StepOne", "Running curr pos " + myRobot.driveTrain.FrontLeftDCMotor.getCurrentPosition());
                telemetry.addData("StepOne", stepOne);
                telemetry.addData("StepOne", " tar pos " + myRobot.driveTrain.getNewPosition(driveToBasketDis));
                telemetry.addData("StepOne", "drive reached " + driveToBasket);
                */

                if (driveToBasket) {
                    stepOne = false;
                    myRobot.driveTrain.stop();
                    //driveStraightReached = false;
                    //telemetry.addData("StepOne after", "drive reached " + driveToBasket);
                    //telemetry.update();
                    stepTwo = true;
                }
                telemetry.update();
            }

            if (stepTwo) {
                stepZero = false;
                myRobot.arm.moveArmVersion2(-350);
                //telemetry.addData("Arm telemetry", myRobot.arm.getTelemetryForArm());
                //telemetry.update();
                myRobot.slide.moveSlidesVersion2(myRobot.slide.slideStartingPosition + 2100);
                //telemetry.addData("Slides telemetry", myRobot.slide.getTelemetryForSlides());
                //telemetry.update();
                if (myRobot.slide.SlideMotor1.getCurrentPosition() > myRobot.slide.slideStartingPosition + 2000) {
                    myRobot.arm.moveArmVersion2(-400);
                    myRobot.claw.turnOut(1);
                    stepFour = false;
                }
            }

            if (stepFour) {
                stepThree = false;
                stepTwo = false;
                myRobot.claw.turnOut(1);
                myRobot.slide.moveSlidesVersion2(myRobot.slide.slideStartingPosition+2100);
                myRobot.arm.moveArmVersion2(-250);

                stepFive = true;
            }


            if (stepFive) {
                stepFour = false;
                myRobot.claw.turnOut(1);
                myRobot.arm.moveArmVersion2(-250);
                myRobot.slide.moveSlidesVersion2(myRobot.slide.slideStartingPosition);
                //myRobot.arm.moveArmVersion2(0);
                telemetry.addData("Slides telemetry", myRobot.slide.getTelemetryForSlides());
                telemetry.update();
                if (myRobot.slide.SlideMotor1.getCurrentPosition() < myRobot.slide.slideStartingPosition + 250) {
                    myRobot.claw.turnOut(1);
                    stepSix = true;
                }

            }

            if (stepSix) {
                stepFive = false;
                myRobot.claw.turnOut(1);
                myRobot.arm.moveArmVersion2(0);
                //myRobot.slide.moveSlidesVersion2(myRobot.slide.slideStartingPosition);
                telemetry.addData("Slides telemetry", myRobot.slide.getTelemetryForSlides());
                telemetry.update();
                if (myRobot.arm.ArmMotor1.getCurrentPosition() < 10) {
                    myRobot.claw.turnOut(1);
                    //Storing arm position at end of auto
                    myRobot.arm.endAutoArmPosition = myRobot.arm.getCurrentPosition(myRobot.arm.ArmMotor1);

                    break;
                }
            }

        }

    }
}
