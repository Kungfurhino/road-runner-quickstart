package org.firstinspires.ftc.teamcode.Recorder;

import android.os.Build;
import android.os.SystemClock;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import androidx.annotation.RequiresApi;

@TeleOp
public class RecordingTeleop extends LinearOpMode {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Recorder recorder = new Recorder("driving" + System.nanoTime() + ".txt", telemetry);
        recorder.addComponent("rightFront", hardwareMap.get(DcMotorEx.class, "rightFront"));
        recorder.addComponent("rightRear", hardwareMap.get(DcMotorEx.class, "rightRear"));
        recorder.addComponent("leftFront", hardwareMap.get(DcMotorEx.class, "leftFront"));
        recorder.addComponent("leftRear", hardwareMap.get(DcMotorEx.class, "leftRear"));
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        recorder.start();
        while (!isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );
            recorder.collectSample();
            sleep(10);

            if(gamepad1.a){
                recorder.stop();
            }else if(gamepad1.y){
                recorder.loadPlayback();
            }

            if (recorder.isLoadedPlayback())
            {
                recorder.playSamples(true);
                //sleep(recorder.getSamplingRateMillis());
            }
        }

    }
}
