package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Detection.GoldMineralDetector;
import org.firstinspires.ftc.teamcode.Teleop.DuckySpinner;
import org.firstinspires.ftc.teamcode.Teleop.Lift;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class BlueSideAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        DuckySpinner duckySpinner = new DuckySpinner(hardwareMap);

        waitForStart();

        /*
        leftFront.setPower(v);
        leftRear.setPower(v1);
        rightRear.setPower(v2);
        rightFront.setPower(v3);
         */

        drive.setMotorPowers(0.1, 0.1, -7, -7);
        sleep(250);
        drive.setMotorPowers(0,0,-0.6,-0.63);
        sleep(200);
        drive.setMotorPowers(0,0, 0,0);
        sleep(200);
        drive.setMotorPowers(-0.2,-0.2,0,0);
        duckySpinner.setDuckySpinner(0.4);
        sleep(3000);
        drive.setMotorPowers(0.2,0.2, 0.3,0.3);
        sleep(600);
        drive.setMotorPowers(0.5, -0.5, 0.5, -0.7);
        sleep(1200);
        drive.setMotorPowers(-0.05,-0.2,-0.2,-0.05);
        sleep(800);
        /*
        drive.setMotorPowers(-0.2,-0.2,-0.2,-0.2);
        sleep(400);
        drive.setMotorPowers(-0.7, 0.7, -0.7, 0.7);
        sleep(600);
        drive.setMotorPowers(0,0,0,0);
        duckySpinner.setDuckySpinner(0.4);
        sleep(3000);

         */

    }
}
