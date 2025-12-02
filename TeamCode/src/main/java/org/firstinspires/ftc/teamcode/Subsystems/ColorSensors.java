package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.acmerobotics.dashboard.config.Config;
@Config
public class ColorSensors {

    ColorSensor color1;
    ColorSensor color2;
    public ColorSensors(HardwareMap hardwaremap){
        // Get the color sensor from hardwareMap
        color1 = hardwaremap.get(ColorSensor.class, "Color1");
        color2 = hardwaremap.get(ColorSensor.class, "Color2");
    }
    public boolean BallDetected() {

        if(color1.red()+color1.green()+color1.blue() > 600 | color2.red()+color2.green()+color2.blue() > 600) {
            return true;
        }
        else { return false; }
    }
    public void status(Telemetry telemetry){
        telemetry.addData("Color1:","-R:" + color1.red() + "-G:" + color1.green() + "-B:" + color1.blue());
        telemetry.addData("Color2:","-R:" + color2.red() + "-G:" + color2.green() + "-B:" + color2.blue());
    }

}
