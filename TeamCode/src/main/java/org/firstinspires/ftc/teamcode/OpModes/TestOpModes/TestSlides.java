package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.ClawPT;
import org.firstinspires.ftc.teamcode.Subsystems.SlidesT;

public class TestSlides extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        //initalization phase

        SlidesT slide = new SlidesT(hardwareMap);

        waitForStart();
        while(opModeInInit() && !isStopRequested()){

            if(gamepad1.y){
                slide.state = SlidesT.State.Basket;
            }
            if(gamepad1.a){
                slide.state = SlidesT.State.Specimen;
            }

            slide.update();

        }




    }
}
