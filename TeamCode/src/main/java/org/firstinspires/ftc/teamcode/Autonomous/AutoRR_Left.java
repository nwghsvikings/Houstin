package org.firstinspires.ftc.teamcode.Autonomous;
// RR-specific imports

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "AutoRR_Left", group = "Robot")
public class AutoRR_Left extends LinearOpMode {



    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-11.77, -61.1, Math.toRadians(90)); //STARTING SPOT
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Servo Claw = hardwareMap.servo.get("claw1");
        DcMotorEx arm = hardwareMap.get(DcMotorEx.class, "arm");
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
        new armResetPosition(arm); //THIS STAYS HERE ALWAYS, DO NOT REMOVE
        // . ARM MUST BE DOWN IN FRONT ALL THE WAY WITH CLAW OPEN ON GROUND.

        TrajectoryActionBuilder InitializeWorld = drive.actionBuilder(initialPose)
                .stopAndAdd(new ArmAction(arm,0,0.7)) //ARM INIT - KEEP THIS AT THE START - DO NOT REMOVE
                .stopAndAdd(new ServoAction(Claw,0)); //CLAW INIT - KEEP THIS AT THE START - DO NOT REMOVE

        //**********************************************************
        //CODE ACTIONS START HERE - PLACE TEAM 9990 AUTO LOGIC HERE - LOGIC FOR AUTO LEFT
        //
        // YOU MAY ALSO GO BACK AND CHANGE THE INITIAL POSE POSITION A FEW ROWS BACK
        // STEP1: TUNE ROBOT PER ROADRUNNER TUNING FOR MECANUM DRIVE 3 DEAD WHEELS IF HARDWARE WAS CHANGED
        // STEP2: CHANGE INITIAL POSE POSITION (RUN MEEPMEEP, ROBOT STARTS IN CENTER OF FIELD)
        // STEP3: MODIFY TRAJECTORYACTIONBUILDER (TAB) SECTIONS. EACH ONE HAS A UNIQUE NAME
        //        THIS IS A NAME YOU COME UP WITH. EACH TAB IS SET EQUAL TO THE PREVIOUS
        //        TAB. NON-MOVEMENT ACTIONS MUST COME BEFORE MOVEMENTS. IF A NON-MOVEMENT
        //        SUCH AS CLAW OR ARM NEEDS TO HAPPEN, IT NEEDS TO START A NEW TAB IF IT NEEDS
        //        TO OCCUR AFTER AN ACTION. LAST ACTION IN EACH TAB GETS A SEMI-COLON, ALL OTHERS
        //        DO NOT.
        // STEP4: BUILD ALL ACTIONS
        // STEP5: PUT ALL SECTIONS IN RUNBLOCKING (COMMAS AFTER ALL EXCEPT LAST)
        // NOTE: MEEPMEEP IS TO TEST MOVEMENT, NOT OTHER ITEMS SUCH AS CLAW/ARM.
        //**********************************************************

        TrajectoryActionBuilder DriveToBlock = InitializeWorld.endTrajectory().fresh()
                .stopAndAdd(new ServoAction(Claw,1)) //OPEN CLAW
                .stopAndAdd(new ArmAction(arm,-300,0.7))
                .strafeTo(new Vector2d(-35,-35));

        TrajectoryActionBuilder PickupBlock1 = DriveToBlock.endTrajectory().fresh()
                .stopAndAdd(new ArmAction(arm,-48,0.2)) //ARM DOWN BLOCK
                .stopAndAdd(new ServoAction(Claw,0)) //CLOSE CLAW
                .waitSeconds(0.5) //CLAW ALWAYS NEEDS DELAY AFTER CLOSING
                .stopAndAdd(new ArmAction(arm,-300,0.7)); //ARM UP

        TrajectoryActionBuilder MoveToCorner1 = PickupBlock1.endTrajectory().fresh()
                .strafeTo(new Vector2d(-55,-50))
                .turn(Math.toRadians(180))
                .setTangent(Math.toRadians(90))
                .lineToY(-60);


        //BUILD ALL ACTIONS - PUT TRAJECTORY HERE WITH AN "A" AT THE END
        Action InitializeWorldA = InitializeWorld.build();
        Action DriveToBlockA = DriveToBlock.build();
        Action PickupBlock1A = PickupBlock1.build();
        Action MoveToCorner1A = MoveToCorner1.build();

        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction( //PUT ALL ACTIONS BELOW
                        InitializeWorldA,
                        DriveToBlockA,
                        PickupBlock1A,
                        MoveToCorner1A
                )
        );

        //*********************************************************
        //END OF TEAM 9990 AUTO ACTIONS
        //**********************************************************
    }

    public class armResetPosition implements Action {
        private boolean initialized = false;
        DcMotorEx arm;
        public armResetPosition(DcMotorEx m) {
            this.arm = m;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); //set the ARM position to 0. Arm should start claw open, and down on the ground in front of robot
                initialized = true;
            }
            double pos = arm.getCurrentPosition();
            return false;
        }
    }

    public class ArmAction implements Action {
        DcMotorEx arm;
        int position;
        double power;
        public ArmAction(DcMotorEx m, int pos, double pow) {
            this.arm = m;
            this.position = pos;
            this.power = pow;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            arm.setTargetPosition(position);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setPower(power);
            while (arm.isBusy()) {
                arm.setPower(power);
            }
            arm.setPower(0);
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            return false;
        }
    }

    public class ServoAction implements Action {
        Servo servo;
        int position;
        public ServoAction(Servo s, int pos) {
            this.servo = s;
            this.position = pos;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            servo.setPosition(position);
            return false;
        }
    }

}



/*
SAMPLE CODE
                //START OF AUTO RIGHT
                .splineToConstantHeading(new Vector2d(7,-36),Math.toRadians(90))
                //GET FIRST BLOCK
                .strafeTo(new Vector2d(35,-36))
                .setTangent(Math.toRadians(-90))
                .lineToY(-13)
                .strafeTo(new Vector2d(47,-13))
                .setTangent(Math.toRadians(90))
                .lineToY(-50)

                .lineToY(-13) //GET SECOND BLOCK
                .strafeTo(new Vector2d(56,-13))
                .setTangent(Math.toRadians(90))
                .lineToY(-50)

                .lineToY(-13) //GET THIRD BLOCK
                .strafeTo(new Vector2d(61,-13))
                .setTangent(Math.toRadians(90))
                .lineToY(-57)
                .lineToY(-56)

                .splineToConstantHeading(new Vector2d(-3,-34),Math.toRadians(90)) //Score on Chamber
                .lineToY(-35)
                .strafeTo(new Vector2d(61,-57))
                .lineToY(-56)
                .splineToConstantHeading(new Vector2d(0,-34),Math.toRadians(90))
                .lineToY(-35)
                .strafeTo(new Vector2d(61,-57))
                .lineToY(-56)
                .splineToConstantHeading(new Vector2d(3,-34),Math.toRadians(90))
                .lineToY(-35)

                .strafeTo(new Vector2d(61,-60)) //Park
 */
