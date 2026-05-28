package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Flywheel Test", group = "Test")
public class FlywheelTest extends LinearOpMode {
    public DcMotorEx flywheelMotorLeft;
    public DcMotorEx flywheelMotorRight;


    @Override
    public void runOpMode() throws InterruptedException {
        flywheelMotorLeft = hardwareMap.get(DcMotorEx.class, "flywheelLeft");
        flywheelMotorRight = hardwareMap.get(DcMotorEx.class, "flywheelRight");

        flywheelMotorLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        flywheelMotorRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        flywheelMotorLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheelMotorRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        flywheelMotorLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        flywheelMotorRight.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        while (opModeIsActive()) {
            if (gamepad2.a) {
                flywheelMotorLeft.setPower(1);
                flywheelMotorRight.setPower(1);
            } else {
                flywheelMotorLeft.setPower(0);
                flywheelMotorRight.setPower(0);
            }

            telemetry.addData("Left Motor RPM", flywheelMotorLeft.getVelocity());
            telemetry.addData("Right Motor RPM", flywheelMotorRight.getVelocity());
            telemetry.update();
        }
    }
}
