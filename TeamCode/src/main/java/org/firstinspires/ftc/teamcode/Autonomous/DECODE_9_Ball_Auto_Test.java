package org.firstinspires.ftc.teamcode.Autonomous;
// RR-specific imports

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.TeamColor;

@Config
@Autonomous(name = "AutoRR_Left", group = "Robot")
public class DECODE_9_Ball_Auto_Test extends LinearOpMode {
    public static TeamColor.Colors GOAL = TeamColor.Colors.RED;

    public static int R = 1;
    public static int A = 0;
    public static int A2 = 0;

    @Override
    public void runOpMode() {
        if(GOAL == TeamColor.Colors.BLUE) {R=-1;A=-180;A2=-1*(A/2);}
        if(GOAL == TeamColor.Colors.RED) {R=1;A=0;A2=-1*(A/2);}

        Pose2d initialPose = new Pose2d(-60.4, 37*R, Math.toRadians(90)); //STARTING SPOT
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        DcMotorEx intake = hardwareMap.get(DcMotorEx.class, "intake");
        DcMotorEx flywheel = hardwareMap.get(DcMotorEx.class,"shooter2");
        DcMotorEx flywheel2 = hardwareMap.get(DcMotorEx.class,"shooter");
        DcMotorEx turret = hardwareMap.get(DcMotorEx.class,"gears");
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheel.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel2.setDirection(DcMotorSimple.Direction.REVERSE);
        Servo push = hardwareMap.servo.get("push");
        Servo block=hardwareMap.servo.get("block");
        TeamColor teamColor = new TeamColor();
        Camera camera = new Camera(hardwareMap,teamColor.getColor());


        //THIS STAYS HERE ALWAYS, DO NOT REMOVE

        TrajectoryActionBuilder InitializeWorld = drive.actionBuilder(initialPose)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterActionDOWN(push,block)); //SERVOS IN DEFAULT POSITION - STARTING

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

        TrajectoryActionBuilder Shoot3 = InitializeWorld.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-23.5,23.5*R), Math.toRadians(135+A2))
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.FlyWheelAction(flywheel,flywheel2,0.85))//FLYWHEEL ON
             //   .stopAndAdd(new DECODE_9_Ball_Auto_Test.TurretAction(camera,turret)) //AUTO-AIM
                //   .stopAndAdd(new DECODE_9_Ball_Auto_Test.FlyWheelAction(flywheel,flywheel2,0)) //FLYWHEEL OFF
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterAction(push,block))//LIFT BALL INTO SHOOTER
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .waitSeconds(.2)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0.6))
                .waitSeconds(.2)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterAction(push,block)) //LIFT BALL INTO SHOOTER
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .waitSeconds(.2)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0.6))
                .waitSeconds(.3)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterAction(push,block)) //LIFT BALL INTO SHOOTER
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .waitSeconds(.1)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.FlyWheelAction(flywheel,flywheel2,0)); //FLYWHEEL OFF
               // .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0.7)); //INTAKE POWER ON

        TrajectoryActionBuilder Intake6 = Shoot3.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-11.7,23.5*R), Math.toRadians(90+A))
                .strafeToLinearHeading(new Vector2d(-11.7,53*R), Math.toRadians(90+A));

        TrajectoryActionBuilder Shoot6 = Intake6.endTrajectory().fresh()

                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0)) //INTAKE POWER OFF
                .waitSeconds(.1)
                .strafeToLinearHeading(new Vector2d(-23.55,23.5*R), Math.toRadians(135+A2))
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.FlyWheelAction(flywheel,flywheel2,0.85)) //FLYWHEEL ON
              //  .stopAndAdd(new DECODE_9_Ball_Auto_Test.TurretAction(camera,turret)) //AUTO-AIM
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterAction(push,block))//LIFT BALL INTO SHOOTER
                .waitSeconds(.2)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0.6))
                .waitSeconds(.2)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterAction(push,block)) //LIFT BALL INTO SHOOTER
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .waitSeconds(.2)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0.6))
                .waitSeconds(.3)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterAction(push,block)) //LIFT BALL INTO SHOOTER
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .waitSeconds(.1)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.FlyWheelAction(flywheel,flywheel2,0));

        TrajectoryActionBuilder Intake9 = Shoot6.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(11.7,23.5*R), Math.toRadians(90+A))
                .strafeToLinearHeading(new Vector2d(11.7,60.4*R), Math.toRadians(90+A));


        TrajectoryActionBuilder Shoot9 = Intake6.endTrajectory().fresh()
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0)) //INTAKE POWER OFF
                .waitSeconds(.1)
                .strafeToLinearHeading(new Vector2d(11.7,45*R), Math.toRadians(90+A))
                .strafeToLinearHeading(new Vector2d(-23.55,23.5*R), Math.toRadians(135+A2))
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.FlyWheelAction(flywheel,flywheel2,0.85)) //FLYWHEEL ON
               // .stopAndAdd(new DECODE_9_Ball_Auto_Test.TurretAction(camera,turret)) //AUTO-AIM
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterAction(push,block))//LIFT BALL INTO SHOOTER
                .waitSeconds(.2)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0.6))
                .waitSeconds(.2)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterAction(push,block)) //LIFT BALL INTO SHOOTER
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .waitSeconds(.2)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0.6))
                .waitSeconds(.3)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.ShooterAction(push,block)) //LIFT BALL INTO SHOOTER
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .waitSeconds(.1)
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0))
                .stopAndAdd(new DECODE_9_Ball_Auto_Test.FlyWheelAction(flywheel,flywheel2,0))
                .strafeToLinearHeading(new Vector2d(-10,35*R), Math.toRadians(135+A2));

                //  .setTangent(Math.toRadians(-90))
                //  .splineToLinearHeading(new Pose2d(-23.5,23.5,Math.toRadians(135)),Math.toRadians(180) , new TranslationalVelConstraint(100), new ProfileAccelConstraint(-100,100))
               /* .waitSeconds(3)
                .strafeToLinearHeading(new Vector2d(35.2,23.5), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(35.2,60.4), Math.toRadians(90))
                .waitSeconds(.5)
                .strafeToLinearHeading(new Vector2d(-23.55,23.5), Math.toRadians(135))
                .waitSeconds(3)
                .strafeToLinearHeading(new Vector2d(-10,35), Math.toRadians(135));*/



        //BUILD ALL ACTIONS - PUT TRAJECTORY HERE WITH AN "A" AT THE END
        Action InitializeWorldA = InitializeWorld.build();
        Action Shoot3A = Shoot3.build();
        Action Intake6A = Intake6.build();
        Action Shoot6A = Shoot6.build();
        Action Intake9A = Intake9.build();
        Action Shoot9A = Shoot9.build();
        Action TurretAction = new TurretAction(camera,turret);


        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        TurretAction,
                new SequentialAction( //PUT ALL ACTIONS BELOW
                        InitializeWorldA,
                        Shoot3A,
                        new ParallelAction(
                                (new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0.7)), //INTAKE POWER ON
                                Intake6A
                        ),
                        Shoot6A,
                        new ParallelAction(
                                (new DECODE_9_Ball_Auto_Test.IntakeAction(intake,0.7)), //INTAKE POWER ON
                                Intake9A
                        ),
                        Shoot9A


                )
                )
        );

        //*********************************************************
        //END OF TEAM 9990 AUTO ACTIONS
        //**********************************************************
    }

    public class IntakeAction implements Action {
        DcMotorEx intake;
        double power;
        public IntakeAction(DcMotorEx m, double pow) {
            this.intake = m;
            this.power = pow;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intake.setPower(power);
            return false;
        }
    }


    public class FlyWheelAction implements Action {
        DcMotorEx flywheel;
        DcMotorEx flywheel2;
        double power;
        public FlyWheelAction(DcMotorEx m, DcMotorEx m2, double pow) {
            this.flywheel = m;
            this.flywheel2 = m2;
            this.power = pow;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            flywheel.setPower(power);
            flywheel2.setPower(-flywheel.getPower());
            return false;
        }
    }

    public class ShooterAction implements Action {
        Servo push;
        Servo block;
        long timeSnapshot = System.currentTimeMillis();
        public ShooterAction(Servo s, Servo s2) {
            this.push = s;
            this.block = s2;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            push.setPosition(0);
            block.setPosition(.6-0);
            timeSnapshot = System.currentTimeMillis();
            while (System.currentTimeMillis() < (timeSnapshot+500)) {
                //empty to add a delay
            }
            push.setPosition(1);
            block.setPosition(1-1); //Difficult math problem
            return false;
        }
    }

    public class ShooterActionUP implements Action {
        Servo push;
        Servo block;
        public ShooterActionUP(Servo s, Servo s2) {
            this.push = s;
            this.block = s2;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            push.setPosition(0);
            block.setPosition(.6-0);
            return false;
        }
    }
    public class ShooterActionDOWN implements Action {
        Servo push;
        Servo block;
        public ShooterActionDOWN(Servo s, Servo s2) {
            this.push = s;
            this.block = s2;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            push.setPosition(1);
            block.setPosition(1-1); //Difficult math problem
            return false;
        }
    }

    public class TurretAction implements Action {
        DcMotorEx turret;
        Camera camera;
        long timeSnapshot = System.currentTimeMillis();

        double kP =-.05;
        double maxPower =.3;
        int maxLeft=667;
        int maxRight=-667;
        public TurretAction(Camera c, DcMotorEx m) {
            this.camera = c;
            this.turret = m;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            camera.update(telemetry);
            timeSnapshot = System.currentTimeMillis();
            while (System.currentTimeMillis() < (timeSnapshot+500)) {
                turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                double power = camera.getXError() * kP;
                if (Math.abs(power) > maxPower){
                    power = Math.signum(power) * maxPower;
                }
                if (camera.getXError() > 0 && turret.getCurrentPosition() < maxRight) {
                    power = 0;
                }
                if (camera.getXError() <  0 && turret.getCurrentPosition() > maxLeft) {
                    power = 0;
                }
                turret.setPower(power);
                camera.update(telemetry);
            }
            turret.setPower(0);
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
