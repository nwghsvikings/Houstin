package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {
    public DcMotor gears;
    public void init(HardwareMap hwMap){
        gears = hwMap.get(DcMotor.class, "gears");
        gears.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void setMotorSpeed(double speed) {
        gears.setPower(speed);
    }
}
