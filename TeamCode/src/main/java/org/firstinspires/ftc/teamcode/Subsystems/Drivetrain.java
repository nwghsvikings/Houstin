package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Drivetrain {
    DcMotor fL;
    DcMotor fR;
    DcMotor bL;
    DcMotor bR;
    public Drivetrain(HardwareMap hardwareMap){
        fL = hardwareMap.dcMotor.get("frontleft");
        fR = hardwareMap.dcMotor.get("frontright");
        bR = hardwareMap.dcMotor.get("backright");
        bL = hardwareMap.dcMotor.get("backleft");

        fR.setDirection(DcMotorSimple.Direction.REVERSE);
        bR.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void run(double x, double y, double rx, double sensitivity){
        x=-x;
        rx = -rx;
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator * sensitivity;
        double backLeftPower = (y - x + rx) / denominator * sensitivity;
        double frontRightPower = (y - x - rx) / denominator * sensitivity;
        double backRightPower = (y + x - rx) / denominator * sensitivity;
        fL.setPower(frontLeftPower);
        bL.setPower(backLeftPower);
        fR.setPower(frontRightPower);
        bR.setPower(backRightPower);
    }
    public void status(Telemetry telemetry) {
        telemetry.addData("Left Odometry (BL)?", bL.getCurrentPosition());
        telemetry.addData("Right Odometry (FR)?", fR.getCurrentPosition());
        telemetry.addData("Back Odometry (BR)?", bR.getCurrentPosition());
    }

}
