package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Turret {
    public enum States{
        ON,
        OFF
    }
    States currentStates=States.OFF;
    DcMotor turret;
    public double targetPos = 0;
    public static double kP = 0.005;
    public static double sensitivity = 1;

    public Turret(HardwareMap hardwaremap){
        turret=hardwaremap.dcMotor.get("turret");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void changePower(double input){
        targetPos += input * sensitivity;
    }
    public void setState(States newState){
        currentStates = newState;
    }
    public States getState(){
        return currentStates;
    }
    public void run(){
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double power = 0;
        switch (currentStates){
            case OFF:
                power = 0;
                break;
            case ON:
                double error = targetPos - turret.getCurrentPosition();
                power = error * kP;
                break;
        }
        turret.setPower(power);
    }
    public void status(Telemetry telemetry){
        telemetry.addData("turretTarget",targetPos);
        telemetry.addData("turretPos",turret.getCurrentPosition());
        telemetry.addData("turretState",currentStates);
    }
}
