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
    public boolean doneResetting = true;

    public Turret(HardwareMap hardwaremap) {
        turret = hardwaremap.dcMotor.get("gears");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void run(double xError) {
        double power = 0;
        if (doneResetting) {
            power = xError * kP;
        }else {
            power = turret.getCurrentPosition() * resetPower;
        }
        if (Math.abs(power) > maxPower) {
            power = Math.signum(power) * maxPower;
        }
        if (xError > 0 && turret.getCurrentPosition() < maxRight) {
            power = 0;
        }
        if (xError < 0 && turret.getCurrentPosition() > maxLeft) {
            power = 0;
        }
        if (Math.abs(turret.getCurrentPosition()) < resetTolerance){
            doneResetting = true;
        }

        turret.setPower(power);
    }
    public void status(Telemetry telemetry) {
        telemetry.addData("turretPos", turret.getCurrentPosition());
        telemetry.addData("donereseting",doneResetting);
    }
}
