package org.firstinspires.ftc.teamcode.opmodes.test;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.subsystems.Intake;


@TeleOp(name= "Intake Transfer Test", group= "Test")
public class IntakeTest extends LinearOpMode {
    private Intake intake;

    @Override
    public void runOpMode() {
        intake = new Intake(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.left_trigger > 0.2) {
                intake.intake();
            } else if (gamepad1.left_bumper) {
                intake.reverse();
            } else {
                intake.stop();
            }

            intake.update();
            intake.updateLight();

            telemetry.addData("Intake State: ", intake.getState());
            telemetry.addData("Current Amps: ", intake.getCurrent());
            telemetry.addData("Estimated Balls: ", intake.getBallCount());
            telemetry.update();
        }
    }
}
