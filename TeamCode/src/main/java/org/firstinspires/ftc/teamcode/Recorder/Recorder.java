package org.firstinspires.ftc.teamcode.Recorder;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.ui.UILocation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Recorder {

    public static class Sample
    {
        public String name;
        public double value;

        public Sample(String name, double value)
        {
            this.name = name;
            this.value = value;
        }
    }

    private String filename;
    private Context context;
    private long sampleRateMillis = 50;
    private int playBackIndex = 0;
    private Telemetry telemetry;

    private Map<String, Object> componentMap = new HashMap<>();

    private List<List<Sample>> samples = new ArrayList<>();

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Recorder(String filename, Telemetry telemetry) {
        this.filename = filename;
        this.context = AppUtil.getInstance().getApplication().getApplicationContext();
        this.telemetry = telemetry;
    }

    public void addComponent(String name, Object component)
    {
        componentMap.put(name, component);
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
        /*

        try {
            Files.write(Paths.get(AppUtil.FIRST_FOLDER + filename), text.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to write to file:" + filename, e);
        }
         */
        /*
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(text.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to write to file:" + filename, e);
        }

         */
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
        //scheduler.scheduleAtFixedRate(() -> collectSample(), 0, sampleRateMillis, TimeUnit.MILLISECONDS);
    }

    public void collectSample() {
        List<Sample> sampleList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : componentMap.entrySet())
        {
            telemetry.addData("Collecting Data: ", entry.getKey());
            telemetry.update();
            String name = entry.getKey();
            Object component = entry.getValue();
            if (component instanceof DcMotorEx)
            {
                sampleList.add(new Sample(name, ((DcMotorEx) component).getPower()));
            }
            else if (component instanceof DcMotor)
            {
                sampleList.add(new Sample(name, ((DcMotor) component).getPower()));
            }
            // TODO - add other component types for sample collection
        }
        samples.add(sampleList);
    }

    /*
    0
    leftfront,0.12222
    leftback,0.12222
    1
    leftfront,0.12222
    leftback,0.12222
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void stop() {
        //scheduler.shutdownNow();
        telemetry.addData("Scheduler shutdown", "now");
        telemetry.update();
        String content = "";
        for (int i = 0; i < samples.size(); i++) {
            content += i + System.lineSeparator();
            for(int k = 0; k < samples.get(i).size(); k++){
                content += samples.get(i).get(k).name + "," + samples.get(i).get(k).value + System.lineSeparator();
                telemetry.addData("Sample", samples.get(i).get(k).name + samples.get(i).get(k).value + "");
                telemetry.update();
            }
        }
        telemetry.addData("sample size: ", samples.size());
        telemetry.addData("content: ", content);
        telemetry.update();
        writeToFile(content);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void playback() {
        String content = readFromFile();
        telemetry.addData("content", content);
        telemetry.update();
        samples.clear();

        List<Sample> sampleList = null;
        for (String line : content.split(System.lineSeparator())) {
            if (line.contains(",")) {
                // sample line
                String[] sampleText = line.split(","); // e.g. leftFront,0.12333
                sampleList.add(new Sample(sampleText[0], Double.parseDouble(sampleText[1])));
            } else {
                // index line
                if (sampleList != null)
                {
                    samples.add(new ArrayList<>(sampleList));
                }
                sampleList = new ArrayList<>();
            }
        }
        telemetry.addData("Playing back", "samples now " + samples.size());
        telemetry.update();
        playBackIndex = 0;
        //scheduler = Executors.newSingleThreadScheduledExecutor();
        //scheduler.scheduleAtFixedRate(() -> playSamples(false), 0, sampleRateMillis, TimeUnit.MILLISECONDS);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void loadPlayback() {
        String content = readFromFile();
        telemetry.addData("content", content);
        telemetry.update();
        samples.clear();

        List<Sample> sampleList = null;
        for (String line : content.split(System.lineSeparator())) {
            if (line.contains(",")) {
                // sample line
                String[] sampleText = line.split(","); // e.g. leftFront,0.12333
                sampleList.add(new Sample(sampleText[0], Double.parseDouble(sampleText[1])));
            } else {
                // index line
                if (sampleList != null)
                {
                    samples.add(new ArrayList<>(sampleList));
                }
                sampleList = new ArrayList<>();
            }
        }
        telemetry.addData("Playing back", "samples now " + samples.size());
        telemetry.update();
        playBackIndex = 0;
        loadedPlayBack = true;
    }

    private boolean loadedPlayBack = false;

    public boolean isLoadedPlayback() {
        return loadedPlayBack && samples != null && samples.size() > 0 && playBackIndex < samples.size();
    }

    public void playSamples(boolean isRunningAtCallerThread) {
        if (!isRunningAtCallerThread && playBackIndex >= samples.size()) {
            scheduler.shutdownNow();
        }

        for (Sample sample : samples.get(playBackIndex)) {
            Object component = componentMap.get(sample.name);
            if (component instanceof DcMotorEx)
            {
                ((DcMotorEx) component).setPower(sample.value);
                if(sample.value != 0){
                    telemetry.addData("set velocity to: ", sample.value);
                    telemetry.update();
                }
            }
            else if (component instanceof DcMotor)
            {
                ((DcMotor) component).setPower(sample.value);
            }
        }
        playBackIndex++;
    }

    public long getSamplingRateMillis() {
        return sampleRateMillis;
    }
}
