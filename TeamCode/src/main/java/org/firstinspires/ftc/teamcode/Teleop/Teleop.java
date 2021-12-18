package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
@TeleOp(group = "drive")
public class Teleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        DuckySpinner duckySpinner = new DuckySpinner(hardwareMap);
        Lift lift = new Lift(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (!isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            if (gamepad2.b) {
                lift.openClaw();
            }else if(gamepad2.a){
                lift.setHoriServo(1.1);
            }else if(gamepad2.y){
                lift.setHoriServo(0.25);
            }else if(gamepad2.x){
                lift.closeClaw();
            }else if(gamepad2.dpad_left){
                lift.setVertServo(0.82);
            }else if(gamepad2.dpad_right){
                lift.setVertServo(1);
            }else if(gamepad2.right_trigger == 1){
                duckySpinner.setDuckySpinner(-0.6);
            }else if(gamepad2.left_trigger == 1){
                duckySpinner.setDuckySpinner(0.6);
            }else if(gamepad2.dpad_up){
                lift.setVerticalLift(1);
            }else if(gamepad2.dpad_down){
                lift.setVerticalLift(-1);
            }else if(gamepad1.dpad_left){
                lift.setHorizontalLift(-0.7);
            }else if(gamepad1.dpad_right){
                lift.setHorizontalLift(0.7);
            }else if(gamepad2.left_bumper){
                intake.setIntake(-1);
            }else if(gamepad2.right_bumper){
                intake.setIntake(1);
            }else{
                lift.setHorizontalLift(0);
                lift.setVerticalLift(0.05);
                intake.setIntake(0);
                duckySpinner.setDuckySpinner(0);
            }
        }
    }
}
