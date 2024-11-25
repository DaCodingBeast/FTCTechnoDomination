package org.firstinspires.ftc.teamcode.New.PinpointLocalizer

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import java.util.Locale

@TeleOp(name = "Localizer Test", group = "Linear OpMode") //@Disabled
@Config
class customTest : LinearOpMode(){
    companion object{
        @JvmField var MotorChoice = 0
    }
    override fun runOpMode() {
        telemetry = FtcDashboard.getInstance().telemetry

        val localizer = Localizer(hardwareMap, Localizer.Poses(0.0, 0.0, 0.0))

        waitForStart()
        resetRuntime()

        while (opModeIsActive()) {

            localizer.update()

            if (gamepad1.x) {
                localizer.resetHeading()
            }

            val data = String.format(
                Locale.US,
                "{X: %.3f, Y: %.3f, H: %.3f}",
               Localizer.pose.x,
                Localizer.pose.y,
                Math.toDegrees(Localizer.pose.heading)
            )
            telemetry.addData("Position", data)
            telemetry.update()
        }
    }
}
