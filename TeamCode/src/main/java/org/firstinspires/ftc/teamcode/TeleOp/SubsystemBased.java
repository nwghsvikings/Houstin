package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Camera;
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
        Camera camera = new Camera(hardwareMap);

        if (isStopRequested()) return;
        Gamepad previousGamepad1 = new Gamepad();
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double rx = gamepad1.right_stick_x * 1.1; // Counteract imperfect strafing
            boolean RB = gamepad1.right_bumper && !previousGamepad1.right_bumper;
            boolean LB = gamepad1.left_bumper && !previousGamepad1.left_bumper;
            boolean RT = gamepad1.right_trigger > 0.1 && previousGamepad1.right_trigger < 0.1;
            boolean LT = gamepad1.left_trigger > 0.1 && previousGamepad1.left_trigger < 0.1;
            boolean x = gamepad1.x && !previousGamepad1.x;
            previousGamepad1.copy(gamepad1);
            if (x){
                switch (teamColor.getColor()){
                    case RED:
                        teamColor.setColor(TeamColor.Colors.BLUE);
                        break;
                    case BLUE:
                        teamColor.setColor(TeamColor.Colors.RED);
                        break;
                }
            }
            drivetrain.run(gamepad1.left_stick_x,gamepad1.left_stick_y, gamepad1.right_stick_x,1-(gamepad1.left_trigger/2));
            if (LB){
                switch (flyWheel.getState()){
                    case OFF:
                        flyWheel.setState(FlyWheel.States.ON);
                        break;
                    case ON:
                        flyWheel.setState(FlyWheel.States.OFF);
                        break;
                }
            }
            if (RB){
                transfer.setState(Transfer.States.SHOOTING);
            }
            intake.setPower(gamepad1.right_trigger);

            flyWheel.run();
            camera.update(telemetry);
            turret.run(camera.getXError());
            turret.status(telemetry);
            intake.run();
            transfer.run(telemetry);
            teamColor.status(telemetry);
            telemetry.addData("y",y);
            telemetry.addData("rx",rx);
            telemetry.update();
        }
    }
}