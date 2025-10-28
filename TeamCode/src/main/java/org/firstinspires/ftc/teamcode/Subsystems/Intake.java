package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
   public enum States{
       ON,
       OFF
   }
   States currentState = States.OFF;
    DcMotor intake;
    public Intake(HardwareMap hardwareMap){
        intake = hardwareMap.dcMotor.get("intake");
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
                intake.setPower(1);
                break;
            case OFF:
                intake.setPower(0);
                break;
        }
    }

}
