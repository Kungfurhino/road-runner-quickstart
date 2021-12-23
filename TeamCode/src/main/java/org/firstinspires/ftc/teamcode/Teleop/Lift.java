package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {

    public DcMotor verticalLift;
    private DcMotor horizontalLift;
    private Servo claw;

    public Lift(HardwareMap hardwareMap){
        verticalLift = hardwareMap.get(DcMotor.class, "verticalLift");
        horizontalLift = hardwareMap.get(DcMotor.class, "horizontalLift");
        claw = hardwareMap.get(Servo.class, "claw");
    }

    public void setVerticalLift(double power){
        verticalLift.setPower(power);
    }

    public void setHorizontalLift(double power){
        horizontalLift.setPower(power);
    }

    public void openClaw(){
        claw.setPosition(0.86);
    }

    public void topLevel(){
        verticalLift.setTargetPosition(500);
        verticalLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void closeClaw(){
        claw.setPosition(0.1);
    }
}
