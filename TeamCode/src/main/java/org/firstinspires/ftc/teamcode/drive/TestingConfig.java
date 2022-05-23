package org.firstinspires.ftc.teamcode.drive;

import static org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot.MID_SERVO;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TestingConfig {
    /* Public OpMode members. */
    public DcMotor motor   = null;
    public Servo servo    = null;

    /* local OpMode members. */
    HardwareMap hwMap =  null;

    /* Constructor */
    public TestingConfig(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        motor  = hwMap.get(DcMotor.class, "motor");
        servo  = hwMap.get(Servo.class, "servo");
    }
}
