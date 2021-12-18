package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Detection.GoldMineralDetector;
import org.firstinspires.ftc.teamcode.Detection.SkystoneDeterminationPipeline;
import org.firstinspires.ftc.teamcode.Teleop.DuckySpinner;
import org.firstinspires.ftc.teamcode.Teleop.Lift;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(group = "drive")
public class RedSideAutonomous extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        DuckySpinner duckySpinner = new DuckySpinner(hardwareMap);
        Lift lift = new Lift(hardwareMap);


        waitForStart();

        // leftFront.setPower(v);
        // leftRear.setPower(v1);
        // rightRear.setPower(v2);
        // rightFront.setPower(v3);
        drive.setMotorPowers(0.3, 0.3, 0.3, 0.3);
        sleep(600);
        drive.setMotorPowers(-0.7, 0.7, -0.7, 0.7);
        sleep(1200);
        drive.setMotorPowers(-0.3, -0.3, -0.3, -0.3);
        sleep(500);
        duckySpinner.setDuckySpinner(-0.4);
        sleep(3500);
        drive.setMotorPowers(0.4, 0.4, 0.4, 0.4);
        sleep(530);
        /*
        SkystoneDeterminationPipeline.SkystonePosition position = goldMineralDetector.Detect();
        //move diagonally

        //left = bottom
        //center = middle
        //right = top
        //use lift to drop block and then lower lift
        if(position == SkystoneDeterminationPipeline.SkystonePosition.CENTER) {
            telemetry.addData("Posistion", "center");
        }else if(position == SkystoneDeterminationPipeline.SkystonePosition.LEFT){
            telemetry.addData("Posistion", "Left");
        }else if(position == SkystoneDeterminationPipeline.SkystonePosition.RIGHT){
            telemetry.addData("Posistion", "Right");
        }else{
            telemetry.addData("Position", "not found");
        }
         */
    }
}
