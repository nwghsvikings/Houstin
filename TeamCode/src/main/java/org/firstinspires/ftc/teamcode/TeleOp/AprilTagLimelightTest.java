package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
@TeleOp
public class AprilTagLimelightTest extends OpMode {
    Motors motors = new Motors();
    private Limelight3A limelight;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(8); //april tag 24(RED GOAL)
        motors.init(hardwareMap);
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        LLResult llResult = limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            Pose3D botPose = llResult.getBotpose();
            telemetry.addData("Tx", llResult.getTx());
            telemetry.addData("Ty", llResult.getTy());
            telemetry.addData("Ta", llResult.getTa());
        }

        if (llResult.getTx() > 10) {
            motors.setMotorSpeed(.12);
        }
        if (llResult.getTx() > 7) {
            if (llResult.getTx() < 10) {
                motors.setMotorSpeed(.05);
            }
        }
        if (llResult.getTx() > 4) {
            if (llResult.getTx() < 7) {
                motors.setMotorSpeed(.03);
            }
        }
        if (llResult.getTx() < 3) {
            if (llResult.getTx() > -3) {
                motors.setMotorSpeed(0);
            }
        }
        if (llResult.getTx() < -4) {
            if (llResult.getTx() > -7) {
                motors.setMotorSpeed(-.03);
            }
        }
        if (llResult.getTx() < -7) {
            if (llResult.getTx() > -10) {
                motors.setMotorSpeed(-.05);
            }
        }
        if (llResult.getTx() < -10) {
            motors.setMotorSpeed(-.12);
        }
    }
}