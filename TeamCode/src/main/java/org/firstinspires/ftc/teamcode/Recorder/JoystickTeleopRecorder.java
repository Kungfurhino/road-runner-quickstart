package org.firstinspires.ftc.teamcode.Recorder;

import android.os.Build;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

@TeleOp
public class JoystickTeleopRecorder extends LinearOpMode {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        JoystickRecorder recorder = new JoystickRecorder("drivingTest" + ".txt", telemetry);
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
            recorder.collectSample(collectJoystickValues());

            if(gamepad1.a){
                recorder.stop();
                break;
            }
        }
   }

   public List<Double> collectJoystickValues(){
        List <Double> joystickValues = new ArrayList<Double>();
        joystickValues.add((double)-gamepad1.left_stick_y);
        joystickValues.add((double)-gamepad1.left_stick_x);
        joystickValues.add((double)-gamepad1.right_stick_x);
        return joystickValues;
   }
}
