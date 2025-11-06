package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Turret {
    DcMotor turret;
    public static double kP =-.07;
    public static double maxPower =.3;
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
        turret.setPower(power);
    }
    public void status(Telemetry telemetry){
        telemetry.addData("turretPos",turret.getCurrentPosition());
    }
}
