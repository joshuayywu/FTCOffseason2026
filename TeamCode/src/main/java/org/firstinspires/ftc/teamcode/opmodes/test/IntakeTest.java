package org.firstinspires.ftc.teamcode.opmodes.test;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.subsystems.Intake;


@TeleOp(name = "Intake Transfer Test", group = "Test")
public class IntakeTest extends LinearOpMode {
    private Intake intake;

    private Servo gate;
    private boolean gateOpen = false;
    private boolean lastI = false; // last intake

    @Override
    public void runOpMode() {
        intake = new Intake(hardwareMap);
        gate = hardwareMap.get(Servo.class, "gate");

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad2.left_trigger > 0.2) {
                intake.intake();
            } else if (gamepad2.left_bumper) {
                intake.reverse();
            } else {
                intake.stop();
            }

            boolean iPressed = gamepad2.y;
            if (iPressed && !lastI) {
                gateOpen = !gateOpen; // toggle
            }
            lastI = iPressed;
            if (gateOpen) {
                gate.setPosition(0.5);
            } else {
                gate.setPosition(0.0);
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
