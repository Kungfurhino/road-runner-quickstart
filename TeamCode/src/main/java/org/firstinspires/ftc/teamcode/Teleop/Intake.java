package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotor intake;

    public Intake(HardwareMap hardwareMap){
        intake = hardwareMap.get(DcMotor.class, "intake");
    }

    public void setIntake(double power){
        intake.setPower(power);
    }
}
