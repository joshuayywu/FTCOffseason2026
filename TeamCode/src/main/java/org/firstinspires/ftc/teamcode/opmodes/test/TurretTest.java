package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.subsystems.Turret;

@TeleOp(name = "TurretTest", group = "Test")
public class TurretTest extends LinearOpMode {
    private Turret turret;

    @Override
    public void runOpMode() throws InterruptedException {
        turret = new Turret(hardwareMap);

        waitForStart();
        while(opModeIsActive()) {
            turret.manual(gamepad2.right_stick_x * 0.2);

            /*
            if (gamepad2.a) {
                turret.setTargetAngle(0);
            }
            if (gamepad2.b) {
                turret.setTargetAngle(30);
            }
            if (gamepad2.x) {
                turret.setTargetAngle(-30);
            }
            */

            turret.resetEncoderWhenHomePressed();
            // turret.update();

            telemetry.addData("Angle", turret.getCurrentAngle());
            telemetry.addData("Pressed", turret.isHomePressed());
            telemetry.update();
        }
    }
}
