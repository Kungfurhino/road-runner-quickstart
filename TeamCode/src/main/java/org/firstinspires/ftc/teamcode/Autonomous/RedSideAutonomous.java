package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Detection.GoldMineralDetector;
import org.firstinspires.ftc.teamcode.Teleop.DuckySpinner;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(group = "drive")
public class RedSideAutonomous extends LinearOpMode {

    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    DuckySpinner duckySpinner = new DuckySpinner(hardwareMap);
    GoldMineralDetector goldMineralDetector = new GoldMineralDetector(hardwareMap, this);

    @Override
    public void runOpMode() throws InterruptedException {

        //sets the starting pose for the robot
        Pose2d startPose = new Pose2d(0,0, Math.toRadians(180));
        drive.setPoseEstimate(startPose);


        //TODO-add detection code and change route to drop into the correct height

        Trajectory driveToSpinner = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(-12, 0), Math.toRadians(180))
                .splineTo(new Vector2d(-12, -24), Math.toRadians(180))
                .build();

        Trajectory driveBack = drive.trajectoryBuilder(new Pose2d())
                .back(5)
                .build();

        Trajectory driveToShippingHub = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(-12, 24), Math.toRadians(180))
                .splineTo(new Vector2d(-20, 24), Math.toRadians(180))
                .build();

        //TODO-ADD DROPPING THE MARKED BLOCK

        Trajectory driveToPark = drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(0, 48), Math.toRadians(90))
                .splineTo(new Vector2d(0, 60), Math.toRadians(90))
                .build();

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectory(driveToSpinner);
        drive.followTrajectory(driveBack);
        duckySpinner.setDuckySpinner(-0.7);
        sleep(2000);
        duckySpinner.setDuckySpinner(0);
        drive.followTrajectory(driveToShippingHub);
        drive.followTrajectory(driveToPark);
    }
}
