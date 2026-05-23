package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Shooter;

@TeleOp (name = "MainTeleOp", group = "TeleOp")
public class Tele extends LinearOpMode {

    private Intake intake;
    private Shooter shooter;

    @Override
    public void runOpMode() throws InterruptedException {

        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.left_trigger > 0.2) {
                intake.intake();
            } else if (gamepad1.left_bumper) {
                intake.reverse();
            } else {
                intake.stop();
            }

            if (gamepad1.right_bumper) {
                shooter.requestSpinUp(1600);
            }
            if (gamepad1.x) {
                shooter.requestFeed();
            }
            if (gamepad1.a) {
                shooter.requestStop();
            }

            shooter.update();
            intake.update();
            intake.updateLight();

            telemetry.addData("Intake State: ", intake.getState());
            telemetry.addData("Current Amps: ", intake.getCurrent());
            telemetry.addData("Estimated Balls: ", intake.getBallCount());
            telemetry.update();
        }
    }
}
