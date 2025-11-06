package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Config
public class FlyWheel {
   public enum States{
       ON,
       OFF
   }
   States currentState = States.OFF;
    DcMotor flywheel;
    DcMotor flywheel2;
    public static double power = .85;
    public FlyWheel(HardwareMap hardwareMap){
        flywheel = hardwareMap.dcMotor.get("shooter");
        flywheel2 = hardwareMap.dcMotor.get("shooter2");
    }
    public void setState(States newState){
        currentState = newState;
    }
    public States getState(){
        return currentState;
    }
    public void run(){
        switch (currentState){
            case ON:
                flywheel.setPower(power);
                break;
            case OFF:
                flywheel.setPower(0);
                break;
        }
        flywheel2.setPower(-flywheel.getPower());
    }

}
