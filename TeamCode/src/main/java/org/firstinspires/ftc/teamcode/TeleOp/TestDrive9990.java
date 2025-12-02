package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class
TestDrive9990 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap);
        //drivetrain.ResetEncoder(); //set the gear position to 0. Shooter should be facing center of robot.
        boolean PUSH_TOGGLE = false;
        boolean SHOOTER_TOGGLE = false;
        double PUSH_POS = 0;
        double BLOCK_POS = 0;
        double SHOOTER_POWER = 1;
        boolean LEFT_TRIGGER_PRESSED = false;
        boolean RIGHT_TRIGGER_PRESSED = false;
        boolean SHOOTER_RUNNING = false;
        ElapsedTime time = new ElapsedTime();
        drivetrain.intake.setPower(0);
        drivetrain.gears.setPower(0);
        drivetrain.shooter.setPower(0);
        drivetrain.shooter2.setPower(0);


        waitForStart(); //Do not do anything until we hit the start button, do not move on init



        while (opModeIsActive()) {
            //*****************
            //Add telemetry data to driver station screen
            //*****************
            telemetry.addData("9990 Robotics", "On the move!!!");
            telemetry.addData("Left Odometry", drivetrain.bottomRightDriveMotor.getCurrentPosition());
            telemetry.addData("Right Odometry", drivetrain.topLeftDriveMotor.getCurrentPosition());
            telemetry.addData("Back Odometry", drivetrain.topRightDriveMotor.getCurrentPosition());
            telemetry.addData("Push Servo", drivetrain.push.getPosition());
            telemetry.addData("Block Servo", drivetrain.block.getPosition());
            telemetry.addData("Gears Motor", drivetrain.gears.getCurrentPosition());
            telemetry.addData("Shooter Motor Pwr", drivetrain.shooter.getPower());
            telemetry.addData("Intake Motor Pwr", drivetrain.intake.getPower());
            telemetry.addData("Shooter Power Setting", SHOOTER_POWER);
            telemetry.update();



            double lsy = gamepad1.left_stick_y;
            double lsx = gamepad1.left_stick_x;
            double rsx = gamepad1.right_stick_x;
            drivetrain.moveRobot(lsy, lsx, rsx);
            if(gamepad1.left_trigger > 0.1) {
                drivetrain.moveRobot(.3 * lsy, .3 * lsx, .3 * rsx);
            }

            //*****************
            //PUSH - RIGHT BUMPER BUTTON CONTROLS PUSH SERVO
            //*****************
            PUSH_POS = drivetrain.push.getPosition(); //1=down, 0=pushing up
            BLOCK_POS = drivetrain.block.getPosition(); //1==blocking, 0=open
            if (time.seconds()>0.5 & !PUSH_TOGGLE & PUSH_POS != 1) { //PUSH DOWN
                PUSH_TOGGLE = true;
                drivetrain.push.setPosition(1);
                drivetrain.block.setPosition(0);
            }
            if (gamepad1.right_bumper & !PUSH_TOGGLE & PUSH_POS != 0) { //PUSH UP
                time.reset();
                PUSH_TOGGLE = true;
                drivetrain.push.setPosition(0);
                drivetrain.block.setPosition(1);
            }
            if (!gamepad1.right_bumper) {
                PUSH_TOGGLE = false;
            }

            //***********************
            //GET BALLS INTO/OUT OF SHOOTER - INTAKE/OUTPUT
            //***********************
            if(gamepad1.right_trigger > 0.1 && !gamepad1.a && drivetrain.block.getPosition()==0) {
            drivetrain.intake.setPower(0.5);
            }
            else if(gamepad1.right_trigger < 0.1 && gamepad1.a) {
                drivetrain.intake.setPower(-0.5);
            }
            else {
                drivetrain.intake.setPower(0);
            }


            //***********************
            //ROTATE SHOOTER
            //***********************
            if(gamepad1.dpad_left) {
                drivetrain.gears.setPower(0.3);
            }
            else {
                drivetrain.gears.setPower(0);
            }
            if(gamepad1.dpad_right) {
                drivetrain.gears.setPower(-0.3);
            }
            else {
                drivetrain.gears.setPower(0);
            }

            //***********************
            //SPIN UP SHOOTER
            //***********************
            if(gamepad1.left_bumper && !SHOOTER_TOGGLE && drivetrain.shooter.getPower() == 0) {
                drivetrain.shooter.setPower(SHOOTER_POWER);
                drivetrain.shooter2.setPower(-SHOOTER_POWER);
                SHOOTER_TOGGLE = true;
            }
            if(gamepad1.left_bumper && !SHOOTER_TOGGLE && drivetrain.shooter.getPower() > 0) {
                drivetrain.shooter.setPower(0);
                drivetrain.shooter2.setPower(0);
                SHOOTER_TOGGLE = true;
            }
            if(!gamepad1.left_bumper) {
                SHOOTER_TOGGLE = false;
            }


            //***********************
            //INCREASE SHOOTER POWER
            //***********************
            if(gamepad1.dpad_up) {
                if(!RIGHT_TRIGGER_PRESSED) {
                    if(SHOOTER_POWER < 1) {
                        SHOOTER_POWER = SHOOTER_POWER + 0.05;
                        if(drivetrain.shooter.getPower() > 0) {
                            drivetrain.shooter.setPower(SHOOTER_POWER);
                            drivetrain.shooter2.setPower(-SHOOTER_POWER);
                        }
                    }
                }
                RIGHT_TRIGGER_PRESSED = true;
            }
            else {
                RIGHT_TRIGGER_PRESSED = false;
            }

            //***********************
            //DECREASE SHOOTER POWER
            //***********************
            if(gamepad1.dpad_down) {
                if(!LEFT_TRIGGER_PRESSED) {
                    if(SHOOTER_POWER > 0.2) {
                        SHOOTER_POWER = SHOOTER_POWER - 0.05;
                        if(drivetrain.shooter.getPower() > 0) {
                            drivetrain.shooter.setPower(SHOOTER_POWER);
                            drivetrain.shooter2.setPower(-SHOOTER_POWER);
                        }
                    }
                }
                LEFT_TRIGGER_PRESSED = true;
            }
            else {
                LEFT_TRIGGER_PRESSED = false;
            }

        }
    }
}

