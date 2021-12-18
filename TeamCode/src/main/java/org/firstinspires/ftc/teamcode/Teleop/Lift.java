package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {

    private DcMotor verticalLift;
    private DcMotor horizontalLift;
    private Servo claw;
    private Servo vertServo;
    private Servo horiServo;

    public Lift(HardwareMap hardwareMap){
        verticalLift = hardwareMap.get(DcMotor.class, "verticalLift");
        horizontalLift = hardwareMap.get(DcMotor.class, "horizontalLift");
        claw = hardwareMap.get(Servo.class, "claw");
        vertServo = hardwareMap.get(Servo.class, "vertServo");
        horiServo = hardwareMap.get(Servo.class, "horiServo");
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

    public void closeClaw(){
        claw.setPosition(0.3);
    }

    public void setVertServo(double position){
        vertServo.setPosition(position);
    }

    public void setHoriServo(double position){
        horiServo.setPosition(position);
    }
}
