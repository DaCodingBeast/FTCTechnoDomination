package org.firstinspires.ftc.teamcode.Subsystems;
//JJ

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.RobotParametersPT;

@Config
public class SlidesT {
    public static double p =0.0, i=0.0, d=0.0,f=0.0;
    private final DcMotorEx SlideMotor1;
    private final DcMotorEx SlideMotor2;
    public State state = State.Basket;
    PIDFController controller=  new PIDFController(p,i,d,f);

    public enum State {
        Basket, Specimen, Hang
    }

    public SlidesT(HardwareMap hardwareMap) {
        SlideMotor1 = hardwareMap.get(DcMotorEx.class, RobotParametersPT.slideMotorName1);
        SlideMotor2 = hardwareMap.get(DcMotorEx.class, RobotParametersPT.slideMotorName2);
        SlideMotor2.setDirection(DcMotor.Direction.REVERSE);
        SlideMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SlideMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    int target = 0;
    public void update() {

        controller.setPIDF(p,i,d,f);

        switch (state) {
            case Basket:
                target = 100;
                break;

            case Hang:
                target = 200;
                break;

            case Specimen:
                target = 0;
                break;
        }


        double power = controller.calculate(SlideMotor1.getCurrentPosition(), target);
        SlideMotor1.setPower(power);
        SlideMotor2.setPower(power);
    }

}
