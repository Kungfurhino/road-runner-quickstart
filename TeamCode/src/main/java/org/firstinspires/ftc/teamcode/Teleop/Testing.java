package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.TestingConfig;

@TeleOp
public class Testing extends LinearOpMode {

    private double motorPower = 0.5;
    private double servoPosition = 0.5;
    private TestingConfig testingConfig;

    @Override
    public void runOpMode() throws InterruptedException {
        testingConfig = new TestingConfig();
        testingConfig.init(hardwareMap);

        while(opModeIsActive()){
            if(gamepad1.a){
                motorPower -= 0.1;
            }else if(gamepad1.y){
                motorPower += 0.1;
            }else if(gamepad1.x){
                testingConfig.motor.setPower(motorPower);
            }else if(gamepad1.b){
                testingConfig.motor.setPower(-motorPower);
            }else if(gamepad1.dpad_down){
                servoPosition -= 0.1;
            }else if(gamepad1.dpad_up){
                servoPosition += 0.1;
            }else if(gamepad1.dpad_left){
                testingConfig.servo.setPosition(servoPosition);
            }else if(gamepad1.dpad_right){
                testingConfig.servo.setPosition(-servoPosition);
            }else{
                testingConfig.motor.setPower(0);
            }
        }
    }
}
