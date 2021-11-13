package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DuckySpinner {

    private DcMotor duckySpinner;

    public DuckySpinner(HardwareMap hardwareMap){
        duckySpinner = hardwareMap.get(DcMotor.class, "duckySpinner");
    }

    public void setDuckySpinner(double power){
        duckySpinner.setPower((float)power);
    }
}
