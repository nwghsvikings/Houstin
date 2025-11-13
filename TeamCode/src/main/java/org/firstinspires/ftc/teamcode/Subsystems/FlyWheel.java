package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
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
    public static double maxPower = .85;
    public static double targetVelocity=1000;
    public static double kP = 0.005;
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
    public void run(){
        double power=0.0;
        double error=targetVelocity-flywheel.getVelocity();
        switch (currentState){
            case ON:
                power = kP * error;
                break;
            case OFF:
               power = 0;
                break;
        }
        flywheel.setPower(power);
        flywheel2.setPower(-flywheel.getPower());
    }
    public void status(Telemetry telemetry){
        telemetry.addData("CurrentVelocity",flywheel.getVelocity());
    }

}
