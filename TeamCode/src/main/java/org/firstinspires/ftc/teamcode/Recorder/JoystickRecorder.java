package org.firstinspires.ftc.teamcode.Recorder;

import android.content.Context;
import android.os.Build;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import androidx.annotation.RequiresApi;

public class JoystickRecorder {

    private String filename;
    private Context context;
    private Telemetry telemetry;

    private List<List<Double>> samples = new ArrayList<>();

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public JoystickRecorder(String filename, Telemetry telemetry) {
        this.filename = filename;
        this.context = AppUtil.getInstance().getApplication().getApplicationContext();
        this.telemetry = telemetry;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void writeToFile(String text) {
        File file = new File(AppUtil.FIRST_FOLDER, filename);
        try {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(text.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file:" + filename, e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String readFromFile()
    {
        StringBuilder content = new StringBuilder();
        File file = new File(AppUtil.FIRST_FOLDER, filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                content.append(sCurrentLine).append(System.lineSeparator());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read from file:" + filename, e);
        }
        return content.toString();
    }

    public void start()
    {
        AppUtil.getInstance().showToast(UILocation.BOTH, "Start Recording to " + AppUtil.FIRST_FOLDER + filename);
        telemetry.addData( "recording to : ", new File(AppUtil.FIRST_FOLDER, filename));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void stop() {
        telemetry.addData("Writing file", "now");
        telemetry.update();
        String content = "";
        for (int i = 0; i < samples.size(); i++) {
            content += i + System.lineSeparator();
            content += samples.get(i).get(0) + "," + samples.get(i).get(1) + "," + samples.get(i).get(2) + System.lineSeparator();
        }
        telemetry.addData("sample size: ", samples.size());
        telemetry.addData("content: ", content);
        telemetry.update();
        writeToFile(content);
    }

    public void collectSample(List<Double> sampleList) {
        samples.add(sampleList);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadPlayback() {
        String content = readFromFile();
        telemetry.addData("content", content);
        telemetry.update();
        samples.clear();

        List<Double> sampleList = null;
        for (String line : content.split(System.lineSeparator())) {
            if (line.contains(",")) {
                // sample line
                String[] sampleText = line.split(","); // e.g. leftFront,0.12333
                sampleList.add(Double.parseDouble(sampleText[0]));
                sampleList.add(Double.parseDouble(sampleText[1]));
                sampleList.add(Double.parseDouble(sampleText[2]));
            } else {
                // index line
                if (sampleList != null)
                {
                    samples.add(new ArrayList<Double>(sampleList));
                }
                sampleList = new ArrayList<>();
            }
        }
        telemetry.addData("Playing back", "samples now " + samples.size());
        telemetry.update();
    }

    public void playSamples(SampleMecanumDrive drive) {
        for(int i = 0; i < samples.size(); i++){
            drive.setWeightedDrivePower(new Pose2d(
                    samples.get(i).get(0),
                    samples.get(i).get(1),
                    samples.get(i).get(2)
            ));
        }
    }
}
