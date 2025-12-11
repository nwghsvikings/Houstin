package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Turret {
    DcMotor turret;
    public static double kP = -.02;
    public static double maxPower = .4;
    public static int maxLeft = 667;
    public static int maxRight = -667;
    public static double resetPower = -0.005;
    public static int resetTolerance = 20;
    public static double mp = 0.4;
    public boolean doneResetting = true;

    public Turret(HardwareMap hardwaremap) {
        turret = hardwaremap.dcMotor.get("gears");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void run(double xError) {

        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double power = xError * kP;
        if (Math.abs(power) > maxPower) {
            power = Math.signum(power) * maxPower;
        }
        if (xError > 0 && turret.getCurrentPosition() < maxRight) {
            power = 0;
        }
        if (xError < 0 && turret.getCurrentPosition() > maxLeft) {
            power = 0;
        }
        turret.setPower(power);
    }
public void reset2() {
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turret.setTargetPosition(0);
        turret.setPower(.2);
        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
}
    public void reset() {
        if (doneResetting){
            return;
        }

        double curPos = turret.getCurrentPosition();
        if (curPos < resetTolerance){
            doneResetting = true;
        }
        double power = 0;
            curPos = turret.getCurrentPosition();;
            power = curPos * resetPower;
            if (Math.abs(power) > mp) {
                power = Math.signum(power) * mp;
            }
            turret.setPower(power);
    }

    public void status(Telemetry telemetry) {
        telemetry.addData("turretPos", turret.getCurrentPosition());
        telemetry.addData("test", turret.getTargetPosition());
    }
}
