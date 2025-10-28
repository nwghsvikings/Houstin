package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlyWheel {
   public enum States{
       ON,
       OFF
   }
   States currentState = States.OFF;
    DcMotor flywheel;
    public FlyWheel(HardwareMap hardwareMap){
        flywheel = hardwareMap.dcMotor.get("flywheel");
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
                flywheel.setPower(1);
                break;
            case OFF:
                flywheel.setPower(0);
                break;
        }
    }

}
