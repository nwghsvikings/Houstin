package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Turret {
    DcMotor turret;
    public static double kP =-.05;
    public static double maxPower =.3;
    public static int maxLeft=667;
    public static int maxRight=-667;
    public Turret(HardwareMap hardwaremap){
        turret=hardwaremap.dcMotor.get("gears");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void run(double xError){

        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double power = xError * kP;
        if (Math.abs(power) > maxPower){
            power = Math.signum(power) * maxPower;
        }
        if (xError > 0 && turret.getCurrentPosition() < maxRight) {
            power = 0;
        }
        if (xError <  0 && turret.getCurrentPosition() > maxLeft) {
            power = 0;
        }
        turret.setPower(power);
    }
    public void reset()
    {
        double curPos= turret.getCurrentPosition();
        double power=0;
        while (curPos < -20 || curPos > 20)
        {
            if (curPos>0)
            {
                power=-curPos/667;
            }
            else
            {
                power=curPos/667;
            }
            turret.setPower(power);

        }

    }
    public void status(Telemetry telemetry){
        telemetry.addData("turretPos",turret.getCurrentPosition());
    }
}
