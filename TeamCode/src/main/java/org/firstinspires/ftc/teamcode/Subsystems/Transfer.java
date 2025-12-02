package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config
public class Transfer {
    public enum States {
        RESTING,
        SHOOTING
    }
    States currentState = States.RESTING;
    long timeSnapshot = System.currentTimeMillis();
    public static long waitTime = 400;
    public void setState(States newState) {
        if (newState == States.SHOOTING && currentState != States.SHOOTING){
            timeSnapshot = System.currentTimeMillis();
        }
        currentState = newState;


    }
    public States getStates()
    {
        return currentState;
    }
    Servo push;
    Servo block;
    public Transfer(HardwareMap hardwareMap)
    {
        push = hardwareMap.servo.get("push");
        block=hardwareMap.servo.get("block");
    }
    //Push positions. Block supplementary
    public static double restingPos = 1;
    public static double shootingPos = 0;
    public void run(Telemetry telemetry){
        switch (currentState){
            case RESTING:
                push.setPosition(restingPos);
                block.setPosition(1-restingPos);
                break;
            case SHOOTING:
                push.setPosition(shootingPos);
                block.setPosition(.6-shootingPos);
                if (System.currentTimeMillis() - timeSnapshot > waitTime){
                    currentState = States.RESTING;
                }
                break;
        }
        telemetry.addData("pushPos",push.getPosition());
        telemetry.addData("blockPos",block.getPosition());
        telemetry.addData("Transfer State:", currentState);
    }


}
