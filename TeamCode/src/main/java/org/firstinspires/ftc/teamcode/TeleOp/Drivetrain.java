package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Drivetrain extends LinearOpMode {
    public DcMotor topLeftDriveMotor;
    public DcMotor bottomLeftDriveMotor;
    public DcMotor topRightDriveMotor;
    public DcMotor bottomRightDriveMotor;
    public double ticksperinch = 30.83333333;
    public double ticksperdegree = 7.5;
    public DcMotor intake;
    public DcMotor shooter;
    public DcMotor shooter2;
    public DcMotor gears;
    public Servo push;
    public Servo block;
    public int hangConstant;
    HardwareMap hwMap;

    public void runOpMode() {

    }


    public void init(HardwareMap ahwMap) {

        /**
         * Assigns the parent hardware map to local ArtemisHardwareMap class variable
         * **/
        hwMap = ahwMap;

        /**
         * Hardware initialized and String Names are in the Configuration File for Hardware Map
         * **/

        // Control Hub
        topLeftDriveMotor = hwMap.get(DcMotor.class, "frontleft"); //Mecanum Wheel
        bottomLeftDriveMotor = hwMap.get(DcMotor.class, "backleft"); //Mecanum Wheel
        topRightDriveMotor = hwMap.get(DcMotor.class, "frontright"); //Mecanum Wheel
        bottomRightDriveMotor = hwMap.get(DcMotor.class, "backright"); //Mecanum Wheel
        push = hwMap.servo.get("push"); //SERVO that pushes ball into shooter
        block = hwMap.servo.get("block"); //SERVO blocks next ball from getting into shooter
        shooter = hwMap.get(DcMotor.class, "shooter"); //Ball shooter motor
        shooter2 = hwMap.get(DcMotor.class, "shooter2"); //Ball shooter motor2
        gears = hwMap.get(DcMotor.class, "gears"); //Turn ball shooter for aiming
        intake = hwMap.get(DcMotor.class, "intake"); //Ball intake from ground, spinning rubber bands



        /*
        armmotorLeft = hwMap.dcMotor.get("amleft");
        armmotorRight = hwMap.dcMotor.get("amright");
        armmotorThird = hwMap.dcMotor.get("amthird");
        armslider = hwMap.dcMotor.get("amslider");

        //set up driving mode
        topLeftDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bottomLeftDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        topRightDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bottomRightDriveMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         */

        //gears.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //Do not allow for aiming mechanism to spin all the way around

        //MOTOR ORIENTATION
        topLeftDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        topRightDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        bottomLeftDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        bottomRightDriveMotor.setDirection(DcMotor.Direction.FORWARD);

        // Brake
        topLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        topRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomLeftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bottomRightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //gears.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        /**
         *The 4 mecanum wheel motors, intake, conveyor, and shooter motor/servo are set to 0 power to keep it from moving when the user presses the INIT button
         * **/
        topLeftDriveMotor.setPower(0);
        bottomLeftDriveMotor.setPower(0);
        topRightDriveMotor.setPower(0);
        bottomRightDriveMotor.setPower(0);

    }

    public void moveRobot(double leftStickY, double leftStickX, double rightStickX) {
        /**
         * Wheel powers calculated using gamepad 1's inputs leftStickY, leftStickX, and rightStickX
         * **/
        double ly = -leftStickY * 1; //LEFT JOYSTICK Y AXIS
        double lx = leftStickX * 1.1; //LEFT JOYSTICK X AXIS
        double rx = rightStickX * 1; //RIGHT JOYSTICK X AXIS
        double denominator = Math.max(Math.abs(ly) + Math.abs(lx) + Math.abs(rx), 1);
        double frontLeftPower = (ly + lx + rx) / denominator;
        double backLeftPower = (ly - lx + rx) / denominator;
        double frontRightPower = (ly - lx - rx) / denominator;
        double backRightPower = (ly + lx - rx) / denominator;
        topLeftDriveMotor.setPower(frontLeftPower);
        bottomLeftDriveMotor.setPower(backLeftPower);
        topRightDriveMotor.setPower(frontRightPower);
        bottomRightDriveMotor.setPower(backRightPower);
    }

}











