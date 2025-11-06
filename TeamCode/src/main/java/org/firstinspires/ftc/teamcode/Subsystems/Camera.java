package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Camera {
    Limelight3A limelight;
    double xError = 0;
    double area = 0;
    public double getXError(){
        return xError;
    }
    public double getArea(){
        return area;
    }
    TeamColor.Colors teamColor;

    public Camera(HardwareMap hardwareMap, TeamColor.Colors inputtedColor) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!
        limelight.pipelineSwitch(8); // Switch to pipeline number
        teamColor = inputtedColor;
    }
    public void setColor(TeamColor.Colors inputtedColor){
        teamColor = inputtedColor;
    }
    public TeamColor.Colors getTagColor(String code){
        if (code.equals("20")){
            return TeamColor.Colors.BLUE;
        }else if (code.equals("24")){
            return TeamColor.Colors.RED;
        }
        return TeamColor.Colors.RED;
    }
    public void update(Telemetry telemetry){
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
          //  for (int i = 0; i < result.getBarcodeResults().size(); i++){
          //      String code = result.getBarcodeResults().get(i).getData();
            //    if ((code.equals("20")  || code.equals("24")) && teamColor.equals(getTagColor(code))){
                    double tx = result.getTx(); // How far left or right the target is (degrees)
                    double ty = result.getTy(); // How far up or down the target is (degrees)
                    double ta = result.getTa(); // How big the target looks (0%-100% of the image)
                    xError = tx;
                    area = ta;
            //    }
        //    }

        } else {
            xError = 0;
            area = 0;
            telemetry.addData("Limelight", "No Targets");
        }
        telemetry.addData("Target X", xError);
        telemetry.addData("Target Area", area);
    }

}
