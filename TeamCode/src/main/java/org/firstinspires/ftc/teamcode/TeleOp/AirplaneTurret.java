package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class
AirplaneTurret extends LinearOpMode {
    public DcMotor MotorLeftRight;
    public DcMotor MotorUpDown;
    public Servo Launcher;
    HardwareMap hwMap;

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;
        // Control Hub
        MotorLeftRight = hwMap.get(DcMotor.class, "MotorLeftRight");
        MotorUpDown = hwMap.get(DcMotor.class, "MotorUpDown");
        Launcher = hwMap.servo.get("Launcher");
    }

    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        MotorLeftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorUpDown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorLeftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorUpDown.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        boolean KEY1 = false;
        boolean KEY2 = false;
        boolean KEY3 = false;
        boolean KEY4 = false;
        boolean KEY5 = false;
        boolean KEY6 = false;

        boolean LEFT_BUMPER_OS = false;
        boolean LEFT_TRIGGER_OS = false;
        boolean RIGHT_TRIGGER_OS = false;
        boolean LEFT_BUMPER_FIRED = false;
        boolean LEFT_TRIGGER_FIRED = false;
        boolean RIGHT_TRIGGER_FIRED = false;

        waitForStart(); //Do not do anything until we hit the start button, do not move on init

        while (opModeIsActive()) {
            //*****************
            //Add telemetry data to driver station screen
            //*****************
            telemetry.addData("Team 9990 RoboVikings","");
            telemetry.addData("MotorLeftRight:", MotorLeftRight.getCurrentPosition());
            telemetry.addData("MotorUpDown:", MotorUpDown.getCurrentPosition());
            telemetry.addData("Launcher:", Launcher.getPosition());
            if(KEY1 & KEY2 & KEY3 & KEY4 & KEY5 & KEY6) {
                telemetry.addData("SECRET KEY MODE ACTIVE!!!","");
            }
            telemetry.update();

            double lsy = gamepad1.left_stick_y;
            double lsx = gamepad1.left_stick_x;
            double rsx = gamepad1.right_stick_x;
            if(LEFT_BUMPER_FIRED) {
            LEFT_BUMPER_OS = false;
            }
            if(LEFT_TRIGGER_FIRED) {
                LEFT_TRIGGER_OS = false;
            }
            if(RIGHT_TRIGGER_FIRED) {
                RIGHT_TRIGGER_OS = false;
            }
            if(!gamepad1.left_bumper) {
                LEFT_BUMPER_FIRED = false;
            }
            if(gamepad1.left_trigger < 0.2) {
                LEFT_TRIGGER_FIRED = false;
            }
            if(gamepad1.right_trigger < 0.2) {
                RIGHT_TRIGGER_FIRED = false;
            }
            if (gamepad1.left_bumper & !LEFT_BUMPER_FIRED) {
                LEFT_BUMPER_FIRED = true;
                LEFT_BUMPER_OS = true;
            }
            if (gamepad1.left_trigger > 0.5 & !LEFT_TRIGGER_FIRED) {
                LEFT_TRIGGER_FIRED = true;
                LEFT_TRIGGER_OS = true;
            }
            if (gamepad1.right_trigger > 0.5 & !RIGHT_TRIGGER_FIRED) {
                RIGHT_TRIGGER_FIRED = true;
                RIGHT_TRIGGER_OS = true;
            }



            //CODE ENTERING
            if(LEFT_BUMPER_OS & !KEY1 & !KEY2 & !KEY3 & !KEY4 & !KEY5 & !KEY6) {
                KEY1 = true;
            }
            else if ((LEFT_TRIGGER_OS | RIGHT_TRIGGER_OS) & !KEY1 & !KEY2 & !KEY3 & !KEY4 & !KEY5 & !KEY6) {
                KEY1 = false;
                KEY2 = false;
                KEY3 = false;
                KEY4 = false;
                KEY5 = false;
                KEY6 = false;
            }
            if(LEFT_TRIGGER_OS & KEY1 & !KEY2 & !KEY3 & !KEY4 & !KEY5 & !KEY6) {
                KEY2 = true;
            }
            else if ((LEFT_BUMPER_OS | RIGHT_TRIGGER_OS) & KEY1 & !KEY2 & !KEY3 & !KEY4 & !KEY5 & !KEY6) {
                KEY1 = false;
                KEY2 = false;
                KEY3 = false;
                KEY4 = false;
                KEY5 = false;
                KEY6 = false;
            }
            if(RIGHT_TRIGGER_OS & KEY1 & KEY2 & !KEY3 & !KEY4 & !KEY5 & !KEY6) {
                KEY3 = true;
            }
            else if ((LEFT_TRIGGER_OS | RIGHT_TRIGGER_OS) & KEY1 & KEY2 & !KEY3 & !KEY4 & !KEY5 & !KEY6) {
                KEY1 = false;
                KEY2 = false;
                KEY3 = false;
                KEY4 = false;
                KEY5 = false;
                KEY6 = false;
            }
            if(RIGHT_TRIGGER_OS & KEY1 & KEY2 & KEY3 & !KEY4 & !KEY5 & !KEY6) {
                KEY4 = true;
            }
            else if ((LEFT_TRIGGER_OS | RIGHT_TRIGGER_OS) & KEY1 & KEY2 & KEY3 & !KEY4 & !KEY5 & !KEY6) {
                KEY1 = false;
                KEY2 = false;
                KEY3 = false;
                KEY4 = false;
                KEY5 = false;
                KEY6 = false;
            }
            if(LEFT_TRIGGER_OS & KEY1 & KEY2 & KEY3 & KEY4 & !KEY5 & !KEY6) {
                KEY5 = true;
            }
            else if ((LEFT_BUMPER_OS | RIGHT_TRIGGER_OS) & KEY1 & KEY2 & KEY3 & KEY4 & !KEY5 & !KEY6) {
                KEY1 = false;
                KEY2 = false;
                KEY3 = false;
                KEY4 = false;
                KEY5 = false;
                KEY6 = false;
            }
            if(LEFT_BUMPER_OS & KEY1 & KEY2 & KEY3 & KEY4 & KEY5 & !KEY6) {
                KEY6 = true;
            }
            else if ((LEFT_TRIGGER_OS | RIGHT_TRIGGER_OS) & KEY1 & KEY2 & KEY3 & KEY4 & KEY5 & !KEY6) {
                KEY1 = false;
                KEY2 = false;
                KEY3 = false;
                KEY4 = false;
                KEY5 = false;
                KEY6 = false;
            }






            double MotorPowerModifier = 1; //Motor power modifier if right bumper is pressed
            if(gamepad1.right_bumper) {
                MotorPowerModifier = 2;
            }
            else {
                MotorPowerModifier = 1;
            }

            if((gamepad1.dpad_down | gamepad1.left_stick_y > 0.5) & (MotorUpDown.getCurrentPosition() < 680)) { //MOVE TURRET UP AND DOWN
                MotorUpDown.setPower(0.1*MotorPowerModifier); //DOWN
            }
            else if((gamepad1.dpad_up | gamepad1.left_stick_y < -0.5) & (MotorUpDown.getCurrentPosition() > 0)) {
                MotorUpDown.setPower(-0.1*MotorPowerModifier); //UP
            }
            else {
                MotorUpDown.setPower(0);
            }


            if((gamepad1.dpad_right | gamepad1.left_stick_x > 0.5) & (MotorLeftRight.getCurrentPosition() <= 350)) { //TURN TURRET LEFT AND RIGHT
                MotorLeftRight.setPower(0.05*MotorPowerModifier); //RIGHT
            }
            else if((gamepad1.dpad_left | gamepad1.left_stick_x < -0.5) & (MotorLeftRight.getCurrentPosition() >= -350)) {
                MotorLeftRight.setPower(-0.05*MotorPowerModifier); //LEFT
            }
            else {
                MotorLeftRight.setPower(0);
            }

            if(gamepad1.x) {
                Launcher.setPosition(1);
            }
            if(gamepad1.y) {
                Launcher.setPosition(0);
            }


        }
    }
}

