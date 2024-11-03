package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.ClawPT;

public class TestClaw extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        //initalization phase

        ClawPT claw = new ClawPT(hardwareMap);

        waitForStart();
        while(opModeInInit() && !isStopRequested()){

            if(gamepad1.y){
                claw.state = ClawPT.State.IN;
            }
            if(gamepad1.a){
                claw.state = ClawPT.State.OUT;
            }


            claw.update(.5);

        }




    }
}
