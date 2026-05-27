package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Turret {
    private DcMotorEx turret;
    private TouchSensor turretLimitSwitch; // Magnetic Limit Switch
    private boolean wasPressed = false;

    private double targetAngle = 0;

    public static double kp = 0.035;
    public static double ki = 0.002;
    public static double kd = 0.0005;
    private static final double GOAL_X = 0.0;
    private static final double GOAL_Y = 144.0;
    private static final double MIN_ANGLE = -180;
    private static final double MAX_ANGLE = 180;
    private static final double TURRET_OFFSET_DEG = 0.0; // TUNE PLS

    public Turret(HardwareMap hardwareMap) {
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        turretLimitSwitch = hardwareMap.get(TouchSensor.class, "homeSwitch");

        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turret.setDirection(DcMotor.Direction.FORWARD);
    }

    public double getCurrentAngle() {
        // encoder ticks to degrees
        int ticks = turret.getCurrentPosition();
        return ticks / 1281.67 * 360.0;
    }

    public double getTargetAngle() {
        return targetAngle;
    }

    public double getError() {
        return targetAngle - getCurrentAngle();
    }

    public void setTargetAngle(double angle) {
        // LIMITS
        angle = normalizeDegrees(angle);

        if (angle > MAX_ANGLE) {
            angle = MAX_ANGLE;
        }

        if (angle < MIN_ANGLE) {
            angle = MIN_ANGLE;
        }

        // save target
        targetAngle = angle;
    }

    public void update() {
        // compare target vs current
        // set motor power
        double currentAngle = getCurrentAngle();
        double error = targetAngle - currentAngle;

        double power = error * kp;

        if (Math.abs(error) < 1) {
            power = 0;
        }

        // clamp, speed limits
        if (power > 0.6) {
            power = 0.6;
        }
        if (power < -0.6) {
            power = -0.6;
        }

        turret.setPower(power);
    }

    public boolean isHomePressed() {
        return turretLimitSwitch.isPressed();
    }

    public void resetEncoderWhenHomePressed() {
        boolean pressed = turretLimitSwitch.isPressed();

        if (pressed && !wasPressed) {
            turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        wasPressed = pressed;
    }
    public double autoAim(double robotX, double robotY, double robotHeadingDeg) {
        double dx = GOAL_X - robotX;
        double dy = GOAL_Y - robotY;

        double fieldAngleToGoal = Math.toDegrees(Math.atan2(dy, dx));

        double turretAngle = fieldAngleToGoal - robotHeadingDeg + TURRET_OFFSET_DEG;

        return normalizeDegrees(turretAngle);
    }

    public double normalizeDegrees(double angle) {
        // keep the angle between -180 and 180 degrees
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }

    public void manual(double power) {
        turret.setPower(power);
    }
}