package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Config
public class Intake {
    DcMotor intake;
    double power = .5;
    public static double powerSensitivity = 0.5;
    public void setPower(double input){
        power = powerSensitivity * input;
    }
    public Intake(HardwareMap hardwareMap){
        intake = hardwareMap.dcMotor.get("intake");
    }
    public void run(){
        intake.setPower(power);
    }

}
