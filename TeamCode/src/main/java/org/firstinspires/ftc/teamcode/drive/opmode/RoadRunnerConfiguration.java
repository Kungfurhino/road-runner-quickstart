package org.firstinspires.ftc.teamcode.drive.opmode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.teamcode.util.AxesSigns;
import org.firstinspires.ftc.teamcode.util.BNO055IMUUtil;


public class RoadRunnerConfiguration{

    //Motor
    public static final String MOTOR_FR = "wheelFR";
    public static final String MOTOR_FL = "wheelFL";
    public static final String MOTOR_BR = "wheelBR";
    public static final String MOTOR_BL = "wheelBL";
    public static final String DUCKY_SPINNER = "duckySpinner";
    public static final String VERTICAL_LIFT = "verticalLift";
    public static final String HORIZONTAL_LIFT = "horizontalLift";
    public static final String CLAW = "claw";
    public static final String INTAKE = "intake";
    public DcMotorEx motorFR;
    public DcMotorEx motorFL;
    public DcMotorEx motorBR;
    public DcMotorEx motorBL;
    public DcMotor duckySpinner;
    public DcMotor verticalLift;
    public DcMotor horizontalLift;
    public Servo claw;
    public DcMotor intake;

    HardwareMap hwMap           =  null;

    public BNO055IMU imu;

    public static RoadRunnerConfiguration newConfig(HardwareMap hardwareMap) {

        RoadRunnerConfiguration config = new RoadRunnerConfiguration();
        config.init(hardwareMap);
        return config;
    }

    protected void init(HardwareMap hardwareMap) {

        hwMap = hardwareMap;

        motorFR = hwMap.get(DcMotorEx.class, "rightFront");
        motorFL = hwMap.get(DcMotorEx.class, "leftFront");
        motorBR = hwMap.get(DcMotorEx.class, "rightRear");
        motorBL = hwMap.get(DcMotorEx.class, "leftRear");

        duckySpinner = hwMap.get(DcMotor.class, "duckySpinner");

        horizontalLift = hwMap.get(DcMotor.class, "horizontalLift");
        verticalLift = hwMap.get(DcMotor.class,"verticalLift");
        claw = hwMap.get(Servo.class, "claw");

        intake = hwMap.get(DcMotor.class, "intake");

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
        BNO055IMUUtil.remapAxes(imu, AxesOrder.XYZ, AxesSigns.NPN);
    }
}
