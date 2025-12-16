package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.TeamColor;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;


@TeleOp
public class SubsystemBased extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        waitForStart();
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        FlyWheel flyWheel = new FlyWheel(hardwareMap);
        Turret turret = new Turret(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Transfer transfer = new Transfer(hardwareMap);
        TeamColor teamColor = new TeamColor();
        Camera camera = new Camera(hardwareMap,teamColor.getColor());
        ColorSensors colorsensors = new ColorSensors(hardwareMap);
        if (isStopRequested()) return;
        Gamepad previousGamepad1 = new Gamepad();
        boolean ai = true;
        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double rx = gamepad1.right_stick_x * 1.1; // Counteract imperfect strafing
            boolean RB = gamepad1.right_bumper && !previousGamepad1.right_bumper;
            boolean LB = gamepad1.left_bumper && !previousGamepad1.left_bumper;
            boolean RT = gamepad1.right_trigger > 0.1 && previousGamepad1.right_trigger < 0.1;
            boolean LT = gamepad1.left_trigger > 0.1 && previousGamepad1.left_trigger < 0.1;
            boolean x = !previousGamepad1.xWasPressed() && gamepad1.xWasPressed();
            boolean back = gamepad1.back && !previousGamepad1.back;
            boolean b = gamepad1.b;
            boolean YB = gamepad1.y;
            boolean AB = gamepad1.a;
            boolean RSB = gamepad1.right_stick_button && !previousGamepad1.right_stick_button;

            previousGamepad1.copy(gamepad1);
            if (back) {
                switch (teamColor.getColor()) {
                    case RED:
                        teamColor.setColor(TeamColor.Colors.BLUE);
                        break;
                    case BLUE:
                        teamColor.setColor(TeamColor.Colors.RED);
                        break;
                }
            }
            drivetrain.run(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 1 - (gamepad1.left_trigger / 2));
            if (LB) {
                switch (flyWheel.getState()) {

                    case ON:
                        flyWheel.setState(FlyWheel.States.OFF);
                        break;
                    case OFF:
                        flyWheel.setState(FlyWheel.States.ON);
                        break;
                }
            }
            if (YB && !previousGamepad1.aWasPressed()) {
                flyWheel.add();
            }
            if (AB && !previousGamepad1.aWasPressed()) {

                flyWheel.sub();
            }
            if (RB || (gamepad1.right_trigger > 0.1 && colorsensors.BallDetected() && flyWheel.getState() == FlyWheel.States.ON)) {
                transfer.setState(Transfer.States.SHOOTING);
            }
            if ((transfer.getStates() != Transfer.States.SHOOTING) & !b) {
                intake.setPower(gamepad1.right_trigger);
            } else if (b & gamepad1.right_trigger < 0.1) {
                intake.setPower(-1.0);
            } else {
                intake.setPower(0);
            }
            if (x) {
                ai = !ai;
            }
            if(ai)
            {
                turret.run(camera.getXError());
            }
            else {
                turret.run(0);
            }
            if (RSB) {
                turret.doneResetting = !turret.doneResetting;
            }
            flyWheel.run();
            camera.update(telemetry);
            camera.setColor(teamColor.getColor());
            turret.status(telemetry);
            intake.run();
            transfer.run(telemetry);
            teamColor.status(telemetry);
            flyWheel.status(telemetry);
            telemetry.addData("y",y);
            telemetry.addData("rx",rx);
            colorsensors.status(telemetry);
            telemetry.addData("Ball Detected:", colorsensors.BallDetected());
            telemetry.addData("AI",ai);
            drivetrain.status(telemetry);
            telemetry.update();
        }
    }
}