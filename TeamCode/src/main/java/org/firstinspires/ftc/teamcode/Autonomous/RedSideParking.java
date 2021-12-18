package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Teleop.DuckySpinner;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class RedSideParking extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        drive.setMotorPowers(1,1,1,1);
        sleep(700);
    }
}
