package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class FlyWheel {
   public enum States{
       ON,
       OFF
   }
   States currentState = States.OFF;
    DcMotorEx flywheel;
    DcMotorEx flywheel2;
    public static double maxPower = .5;
    public static double increment = .05;
    public FlyWheel(HardwareMap hardwareMap){
        flywheel = hardwareMap.get(DcMotorEx.class,"shooter2");
        flywheel2 = hardwareMap.get(DcMotorEx.class,"shooter");
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel2.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setState(States newState){
        currentState = newState;
    }
    public States getState(){
        return currentState;
    }
    public void add(){
        if (maxPower <= .95 )
        {
            maxPower += increment;
        }
        else{
            maxPower = 1;
        }
    }
    public void sub(){
        if(maxPower >=.05)
        {
            maxPower -= increment;
        }
        else {
            maxPower = 0;
        }
    }
    public void run() {
        double power = 0.5;

        switch (currentState) {
            case ON:
                power = maxPower;
                break;
            case OFF:
                power = 0;
                break;
        }


        if(Math.abs(power)>maxPower)
        {
            power=Math.signum(power)*maxPower;
        }
        flywheel.setPower(power);
        flywheel2.setPower(-flywheel.getPower());
    }
    public void status(Telemetry telemetry){
        telemetry.addData("CurrentVelocity",flywheel.getVelocity());
        telemetry.addData("Power",flywheel.getPower());
        telemetry.addData("MaxPower",maxPower);
    }

}

