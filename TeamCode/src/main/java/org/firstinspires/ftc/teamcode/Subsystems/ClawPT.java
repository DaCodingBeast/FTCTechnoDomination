package org.firstinspires.ftc.teamcode.Subsystems;
//JJ

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.RobotParametersPT;

public class ClawPT {
    private final Servo ClawServo1;
    private final CRServo ClawServo2;
    public State state = State.IN;

    public enum State {
        IN, OUT, STOP
    }

    public ClawPT(HardwareMap hardwareMap) {
        ClawServo1 = hardwareMap.get(Servo.class, RobotParametersPT.ClawServoName1);
        ClawServo2 = hardwareMap.get(CRServo.class, RobotParametersPT.ClawServoName2);
    }

    public void update(double power) {
        switch (state) {
            case IN:
                ClawServo1.setPosition(0);
                break;

            case OUT:
                ClawServo1.setPosition(1);
                ClawServo2.setPower(power);
                break;

            case STOP:
                ClawServo1.setPosition(0.5);
                ClawServo2.setPower(0);
                break;
        }
    }

}
