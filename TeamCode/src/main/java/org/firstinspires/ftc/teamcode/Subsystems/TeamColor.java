package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TeamColor {
    public enum Colors{
        RED,
        BLUE
    }
    Colors color = Colors.RED;
    public void setColor(Colors newColor){
        color = newColor;
    }
    public Colors getColor(){
        return color;
    }
    public void status(Telemetry telemetry){
        telemetry.addData("TeamColor", color);
    }
}
