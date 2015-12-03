package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;


/**
 * Created by Jachzach on 10/21/2015.
 */
//TODO: register colorsensor in hardware, see why threw uncaught exception can't find hardware device "mr"
public class Autonomous extends Hardware
{
    String step;
    FtcConfig ftcConfig=new FtcConfig();
    float gyroTurnSpeed = 0.3f;
    float greyValue = 5f;
    float whiteLimit = 10f;

    public Autonomous()
    {
    }

    private int v_state = 1;

    private String beaconPosition;
    private String color;

    private ColorSensor sensorRGB;
    private GyroSensor sensorGyro;
    private OpticalDistanceSensor sensorODS;


    //  hardwareMap.logDevices();

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F,0F,0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;
    int xVal, yVal, zVal = 0;
    int heading = 0;
    boolean teamColorBlue;

    ReadBeacon readBeacon1 = new ReadBeacon();

    MoveArm moveArm1 = new MoveArm();
    MoveArm moveArm2 = new MoveArm();

    PressButton pressButton1 = new PressButton();

    Delay delay1 = new Delay();
    Delay delayM = new Delay();

    Turn turn1 = new Turn();

    //TODO - at some point make these drive names actually descriptive

    Drive drive1 = new Drive();
    Drive drive2 = new Drive();
    Drive drive3 = new Drive();
    Drive drive4 = new Drive();
    Drive drive5 = new Drive();
    Drive drive6 = new Drive();
    Drive drive7 = new Drive();
    Drive drive8 = new Drive();
    Drive driveM = new Drive();
    Drive driveM2 = new Drive();
    Drive driveMBack = new Drive();

    Drive driveButton1 = new Drive();
    Drive driveButton2 = new Drive();

    ODSReverse odsReverse1 = new ODSReverse();

    GyroTurn gyroTurn1 = new GyroTurn();
    GyroTurn gyroTurn2 = new GyroTurn();
    GyroTurn gyroTurnM = new GyroTurn();

    ODSTurn odsTurn1 = new ODSTurn();

    Pause pauseGyro = new Pause();
    Pause pause1 = new Pause();
    Pause pause2 = new Pause();
    Pause pause3 = new Pause();
    Pause pause4 = new Pause();
    Pause pause5 = new Pause();
    Pause pauseM = new Pause();
    Pause pauseM2 = new Pause();

    Stop stop1 = new Stop();

    DropClimbers dropClimbers = new DropClimbers();
    DriveStraight driveStraight1 = new DriveStraight();

    @Override public void start ()
    {
        //TODO - have phone ask red or blue during init and save as variable
        //Hardware start method
        super.start();
        reset_drive_encoders();
        step = "start";
        beaconPosition = "unknown";



        try {
            sensorRGB = hardwareMap.colorSensor.get("mr");
        } catch (Exception p_exception) {
            m_warning_message("color sensor");
            DbgLog.msg(p_exception.getLocalizedMessage());
            sensorRGB = null;
        }


        try {
            sensorGyro = hardwareMap.gyroSensor.get("gyro");
        } catch (Exception p_exception) {
            m_warning_message("gyro sensor");
            DbgLog.msg(p_exception.getLocalizedMessage());
            sensorGyro = null;
        }
        try {
            sensorODS = hardwareMap.opticalDistanceSensor.get("ODS");
        } catch (Exception p_exception) {
            m_warning_message("ods sensor");
            DbgLog.msg(p_exception.getLocalizedMessage());
            sensorODS = null;
        }


    ftcConfig.init(hardwareMap.appContext, this);

    // sensorRGB = hardwareMap.colorSensor.get("mr");
        sensorGyro.calibrate();


        readBeacon1.reset();

        moveArm1.reset();
        moveArm2.reset();
        pressButton1.reset();
        delay1.reset();

        delayM.reset();

        turn1.reset();

        drive1.reset();
        drive2.reset();
        drive3.reset();
        drive4.reset();
        drive5.reset();
        drive6.reset();
        drive7.reset();
        drive8.reset();

        driveM.reset();
        driveM2.reset();
        driveMBack.reset();

        odsReverse1.reset();

        gyroTurn1.reset();
        gyroTurn2.reset();
        gyroTurnM.reset();

        odsTurn1.reset();

        pause1.reset();
        pause2.reset();
        pause3.reset();
        pause4.reset();
        pause5.reset();
        pauseM.reset();
        pauseM2.reset();

        pauseGyro.reset();

        driveButton1.reset();
        driveButton2.reset();

        dropClimbers.reset();

        driveStraight1.reset();



    } // start

    @Override public void loop ()

    {


        telemetry.clearData();
        // can use configured variables here
        telemetry.addData("ColorIsRed", Boolean.toString(ftcConfig.param.colorIsRed));
        telemetry.addData("DelayInSec", Integer.toString(ftcConfig.param.delayInSec));
        telemetry.addData("AutonType", ftcConfig.param.autonType);
        telemetry.addData("Step: ", step);
        telemetry.addData("Heading: ", heading);
        telemetry.addData("ods",sensorODS.getLightDetected() * 100 );
        telemetry.addData("beacon position", beaconPosition);
        telemetry.addData("color", color);



       /* if (delay1.action()) {
        } else if (gyroTurn1.action(1.0f, 60)){
        } else if (drive1.action(1.0f, 10)){
        } else if (turn1.action(1.0f, 10)){
        } else if (readBeacon1.action()) {
        } else if (moveArm1.action()) {
        } else if (pressButton1.action()) {
        }/*/
        if (ftcConfig.param.autonType == ftcConfig.param.autonType.GO_FOR_BEACON){

            if (moveArm1.action()){
                step = "move arm 1 to reset";
            } else if (driveStraight1.action(0.3f, 86)) { //driveStraight automatically makes go backward
                step = "drive straight 1";
            } else if (gyroTurn1.action(-0.3f, 320)){
                step = "gyro to push away debris";
            } else if (drive2.action(0.3f, 20)){
                step = "drive to push away debris";
            } else if(odsReverse1.action(-0.3f)){
                step = "ods reverse";
            }else if (drive3.action(0.3f, 3)) {
                step = "drive forward a little bit to align";
            }else if (pause1.action(3)){
                step = "pause after drive 1";
            }else if (gyroTurn2.action(0.3f, 85)){
                step = "gyro to face beacon";
            } else if (pause2.action(5)) {
                step = "pause after turn 2";
            }else if ( drive4.action(0.3f, 17)){
                step = "drive forward to read beacon";
            }else if (pause3.action(1)){
                step = "pause to read beacon";
            }else if (readBeacon1.action()) {
                step = "read beacon";
            } else if (drive5.action(-0.3f, 8)){
                step = "back up to move arm";
            } else if (moveArm2.action()){
                step = "move arm for beacon";
            } else if (drive6.action(0.3f, 17)) { // goes forward to press button
                step = "press the button!!!";
            } else if (drive7.action(-0.3f, 3)) { // goes backward to prepare to drop climbers
                step = "back up for climbers";
            } else if (pause4.action(2)){
                step = "pause before climbers";
            }else if (dropClimbers.action()) { // TODO make it only when we know the color
                step = "drop climbers";
            } else if (pause5.action(1)) {
                step = "lase pause";
            } else if (drive8.action(-0.3f, 10)) {
                step = "last drive back!";
            }

            //back up
            //move arm
            //go forward
            //back up a little bit
            //climbers






            /*if (delay1.action()) {
                step = "delay";
            } else if (drive1.action(0.5f, 30)) {
                step = "drive";
            } else if (pause1.action(1)) {
                step = "pause";
            } else if (gyroTurn1.action(gyroTurnSpeed, 40)) {
                step = "gyro turn";
                ///encoder in inches?
            } else if (drive2.action(0.5f, 60)) {
                step = "drive 2";
            } else if (pause2.action(1)){
                step = "pause 2";
            } else if (gyroTurn2.action(gyroTurnSpeed, 40)){
                step = "gyro turn2";
            }  else if (drive3.action(0.2f, 8)){
                step = "drive 3";
            } /*
            else if (readBeacon1.action()) {
                step = "read beacon";
            } else if (moveArm1.action()) {
                step = "move arm";
            } //else if (pressButton1.action()) {}
            else if (driveButton1.action(0.5f, 2) && !beaconPosition.equals("unknown")) {
                step = "drive button";
            } else if (driveButton2.action(-0.5f, 2)){
                step = "drive button back";
            }*/
        }
        else if (ftcConfig.param.autonType == ftcConfig.param.autonType.GO_FOR_MOUNTAIN) {
            if (delayM.action()) {
                step = "delayM";
            } else if (driveM.action(-0.3f, 40)) {
                step = "driveM";
            } else if (pauseM.action(1)) {
                step = "pauseM";
            } else if (driveMBack.action(0.3f, 5)) {
                step = "pauseMBack";
            } else if (pauseM2.action(1)) {
                step = "pauseM2";
            }else if (gyroTurnM.action(gyroTurnSpeed, 80)) {
                step = "gyroTurnM";
                ///encoder in inches?
            } else if (driveM2.action(-0.5f, 25)) {
                step = "drive M2";
            }
        }

            //
            // Send telemetry data to the driver station.
            //
            color();
            update_telemetry(); // Update common telemetry

    } // loop




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //HELPER METHODS

    public void color()
    {

        sensorRGB.enableLed(false);

        // convert the RGB values to HSV values.
        //Color.RGBToHSV((sensorRGB.red() * 8), (sensorRGB.green() * 8), (sensorRGB.blue() * 8), hsvValues);
        Color.RGBToHSV(sensorRGB.red() * 8, sensorRGB.green() * 8, sensorRGB.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.
        telemetry.addData("Clear", sensorRGB.alpha());
        telemetry.addData("Red  ", sensorRGB.red());
        telemetry.addData("Green", sensorRGB.green());
        telemetry.addData("Blue ", sensorRGB.blue());
        telemetry.addData("Hue", hsvValues[0]);


    }
    // finds color of the beacon
    private class ReadBeacon
    {
        //0 = unknown
        //1 = blue
        //-1 = red
        int state;

        ReadBeacon()
        {
            state = -1;
            color = "unknown";
            beaconPosition = "unknown";
        }

        void reset()
        {
            state = -1;
            color = "unknown";
            beaconPosition = "unknown";
        }

        boolean action()
        {
            if (state == 1) {
                return false;
            }

            color();

            if (hsvValues[0] > 50)
            {
                color = "blue";
            } else if (sensorRGB.red() > 0)
            {
                color = "red";
            } else
            {
                color = "unknown";
            }

            if (ftcConfig.param.colorIsRed && color.equals("red")) {
                beaconPosition = "left";
            } else if (ftcConfig.param.colorIsRed && color.equals("blue"))
            {
                beaconPosition = "right";
            } else if (!ftcConfig.param.colorIsRed && color.equals("blue"))
            {
                beaconPosition = "left";
            } else if (!ftcConfig.param.colorIsRed && color.equals("red"))
            {
                beaconPosition = "right";
            } else
            {
                beaconPosition = "unknown";
            }

            state = 1;

            return true;

        }
    }
    // moves arm so able to press beacon
    private class MoveArm {
        int state;
        MoveArm(){
            state = -1;
        }
        void reset() {
            state = -1;
        }
        boolean action(){
            if (state == 1) {
                return false;
            }
            if(!beaconPosition.equals("unknown")){
                push_beacon(beaconPosition.equals("left"));
            }
            else{
                push_beacon_up();
            }

            state = 1;

            return true;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
//DRIVE STRAIGHT
    private class DriveStraight {
        int state;
        String drift;
        long startTime;

        DriveStraight() {
            state = -1;
            drift = "none";
        }
        void reset() {
            state = -1;
            drift = "none";
            reset_drive_encoders();
            if(sensorGyro != null){
                sensorGyro.resetZAxisIntegrator();
            }
        }
        boolean action(float speed, int encoderCount) {
            if (state == 1) {
                return false;
            }

            telemetry.addData("17", "State: " + state);

            xVal = sensorGyro.rawX();
            yVal = sensorGyro.rawY();
            zVal = sensorGyro.rawZ();

            heading = sensorGyro.getHeading();

            if (state == -1) {
                reset_drive_encoders();
                sensorGyro.resetZAxisIntegrator();
                startTime = System.currentTimeMillis();
                state = 3;
                drift = "none";
                return true;
            }
            if (state == 3) {
                if (have_drive_encoders_reset() && (System.currentTimeMillis() > startTime + 2 * 1000) && heading == 0) {
                    state = 0;
                }
                return true;
            }
            if (state == 2) {
                if (have_drive_encoders_reset()) {
                    state = 1;
                }
                return true;
            }


            //should be between 358 and 2
            float rightSpeed = speed;
            float leftSpeed = speed;

            run_using_encoders();
            if (drift.equals("none")){
                if (heading < 180 && heading > 1) {
                    drift = "right";
                } else if (heading > 180 && heading < 359) {
                    drift = "left";
                }
                set_drive_power(speed, speed);
            } else if (drift.equals("right")) {
                if (heading > 359 || heading < 10) {
                    leftSpeed += 0.1D;
                } else {
                    drift = "none";
                }
            } else if (drift.equals("left")) {
                if (heading > 300 || heading < 1) {
                    rightSpeed += 0.1D;
                } else {
                    drift = "none";
                }
            }
            set_drive_power(-leftSpeed, -rightSpeed);

            telemetry.addData("Drift", drift);
            //if it recognizes we need a correction - saves the heading, goes until it's at the opposite heading
            //THEN straightens out

            //use some cosine or sine function to scale the speed change to the degree off?
            //cosine - speed * cos(difference)

            if (have_drive_encoders_reached(encoderCount, encoderCount)) {
                reset_drive_encoders();
                sensorGyro.resetZAxisIntegrator();
                set_drive_power(0.0f, 0.0f);
                state = 2;
            }

            return true;
        }

    }


    private class DropClimbers {
        int state;
        DropClimbers(){
            state = -1;}
        void reset(){
            state = -1;
        }
        boolean action(){
            if (state == 1){
                return false;
            }
            if ( color != "unknown"){
                climber_dropper_out();
            }
            state = 1;
            return true;
        }
    }



    private class Delay {
        int state;
        long startTime;
        Delay() {
            state = -1;
        }
        void reset() { state = -1; }
        boolean action() {
            if (state == -1){
                startTime = System.currentTimeMillis();
                state = 0;
            }
            if (state == 1) {
                return false;
            }


            if (System.currentTimeMillis() > (startTime + ftcConfig.param.delayInSec * 1000)) {
                state = 1;
            }

            return true;
        }
    }

    private class Pause {
        int state;
        long startTime;
        Pause() {
            state = -1;
        }
        void reset() { state = -1; }
        boolean action(int seconds) {
            if (state == -1){
                startTime = System.currentTimeMillis();
                state = 0;
            }
            if (state == 1) {
                return false;
            }
            if (System.currentTimeMillis() > (startTime + seconds * 1000)) {
                state = 1;
            }
            return true;
        }
    }

    private class Stop {
        Stop () {
        }
        boolean action() {
            reset_drive_encoders();
            set_drive_power (0.0f, 0.0f);
            return true;
        }
    }


    private class Drive {
        int state;
        float  b = 0;

        Drive() {
            state = -1;
            // run_using_encoders();
        }

        void reset() {
            reset_drive_encoders();
            state = -1;
        }

        boolean action(float speed, int encoderCount) {
            speed = -speed; //robot is backwards

            if (state == 1) {
                b = 3;
                return false;
            }

           // set_drive_power(speed, speed);
            if (state == -1){
                reset_drive_encoders();
                b = speed;
                state = 3;
                return true;
            }

            if (state == 3){
                if (have_drive_encoders_reset()){
                    state = 0;
                }
                return true;
            }

            if (state == 2) {
                b = 2;
                // reset_drive_encoders();
                if (have_drive_encoders_reset()){
                    b = 3;
                    state = 1;
                }
                return true;
            }


            run_using_encoders();
            set_drive_power(speed, speed);
            b = speed;


            if (have_drive_encoders_reached (encoderCount, encoderCount)) {
                reset_drive_encoders ();
                b = 4;
                set_drive_power (0.0f, 0.0f);
                state = 2;
            }

            telemetry.addData("17", "State: " + state);
            telemetry.addData("0000", "b: " + b);

            return true;
        }
    }
    private class ODSReverse {
        int state;
        int pass;
        long startTime;
        double currentValue;

        ODSReverse(){
            state = -1;
            pass = -1;
        }
        void reset() {
            state = -1;
            pass = -1;
        }
        boolean action (float speed) {
            speed = -speed;

            if (state == 1) {
                return false;
            }

            currentValue = (sensorODS.getLightDetected() * 100);

            if (state == -1) {
                reset_drive_encoders();
                state = 3;
                return true;
            }
            if (state == 3) {
                if (have_drive_encoders_reset()) {
                    state = 0;
                    startTime = System.currentTimeMillis();
                }
                return true;
            }

            if (state == 2) {
                // set_drive_power(0.0f, 0.0f);
                //reset_drive_encoders();
                if (have_drive_encoders_reset()) {
                    state = 1;
                }
                return true;
            }
            if (state == 0) {
                run_using_encoders();
                set_drive_power(speed, speed);

            }
            if (currentValue > greyValue && pass == -1){
                pass = 0;
            }
            else if (currentValue <greyValue && pass == 0){
                pass = 1;
            }
            if (((pass == 1 || !ftcConfig.param.colorIsRed) && currentValue > greyValue) || System.currentTimeMillis() > (startTime + 30 * 1000)) {
                state = 2;
                reset_drive_encoders();
                set_drive_power(0.0f, 0.0f);
            }

            return true;
        }


    }


    private class Turn {
        int state;

        Turn() {
            state = -1;
            run_using_encoders();
        }

        void reset() {
            state = -1;
        }

        boolean action(float speed, int encoderCount) {
            speed = -speed; //robot is backwards

            if (state == -1){
                if (ftcConfig.param.colorIsRed){
                    set_drive_power(speed, -speed);
                    state = 0;
                }
                else{
                    set_drive_power(-speed, speed);
                    state = 0;
                }
                //if blue turn one way if red turn other

            }

            if (state == 1)
            {
                return false;
            }

            state = 0;

            if (have_drive_encoders_reached (encoderCount, encoderCount))
            {
                reset_drive_encoders ();
                set_drive_power (0.0f, 0.0f);
                state = 1;
            }


            return true;
        }
    }

    private class ODSTurn {
        int state;
        long startTime;
        double currentValue;

        ODSTurn(){
            state = -1;
        }
        void reset() {
            state = -1;
        }
        boolean action (float speedFast, float speedSlow) {
            speedFast = -speedFast;
            speedSlow = -speedSlow;

            if (state == 1) {
                return false;
            }

            currentValue = (sensorODS.getLightDetected() * 100);

            if (state == -1) {
                reset_drive_encoders();
                state = 3;
                return true;
            }
            if (state == 3) {
                if (have_drive_encoders_reset()) {
                    state = 0;
                    startTime = System.currentTimeMillis();
                }
                return true;
            }

            if (state == 2) {
                // set_drive_power(0.0f, 0.0f);
                //reset_drive_encoders();
                if (have_drive_encoders_reset()) {
                    state = 1;
                }
                return true;
            }
            if (state == 0) {
                run_using_encoders();
                if (!ftcConfig.param.colorIsRed) {
                    set_back_power(-speedFast, speedSlow);
                } else {
                    set_back_power(speedSlow, -speedFast);
                }


            }
            if (currentValue > whiteLimit || System.currentTimeMillis() > (startTime + 30 * 1000)) {
                state = 2;
                reset_drive_encoders();
                set_back_power(0.0f, 0.0f);
            }

            telemetry.addData("Desired Heading ODS", currentValue);
            return true;
        }


    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //GYRO TURN
    private class GyroTurn {
        int state;
        long startTime;

        GyroTurn() {
            state = -1;
            // run_using_encoders();
        }

        void reset() {
            state = -1;
            if(sensorGyro != null){
                sensorGyro.resetZAxisIntegrator();
            }
        }

        boolean action(float speed, int desiredHeading) {
            speed = -speed; //robot is backwards

            if (state == 1){
                return false;
            }

            xVal = sensorGyro.rawX();
            yVal = sensorGyro.rawY();
            zVal = sensorGyro.rawZ();

            heading = sensorGyro.getHeading();

            if (state == -1){
                //pauseGyro.reset(); //resets abort code
                reset_drive_encoders();
                sensorGyro.resetZAxisIntegrator();
                state = 3;
                return true;
            }

            if (state == 3) {
                if (have_drive_encoders_reset() && heading == 0){
                    state = 0;
                    startTime = System.currentTimeMillis();
                }
                return true;
            }

            if (state == 2) {
                // set_drive_power(0.0f, 0.0f);
                //reset_drive_encoders();
                if (have_drive_encoders_reset()) {
                    state = 1;
                }
                return true;
            }


            if (state == 0) {
                run_using_encoders();
                if (!ftcConfig.param.colorIsRed) {
                    set_drive_power(-speed, speed);
                } else {
                    set_drive_power(speed, -speed);
                }

                if (ftcConfig.param.colorIsRed) {
                    desiredHeading = 360 - desiredHeading;
                }



                if (Math.abs(heading - desiredHeading) < 5 || System.currentTimeMillis() > (startTime + 10 * 1000)) {
                    state = 2;
                    sensorGyro.resetZAxisIntegrator();
                    reset_drive_encoders();
                    set_drive_power(0.0f, 0.0f);
                }
            }



            telemetry.addData("Desired Heading", desiredHeading);
            telemetry.addData("Color", ftcConfig.param.colorIsRed);

//abs of current - desired
            //if ^ is less than a given approximate range 2
            //then stop!

         return true;
        }
    }






///////////////////////////////////////////////////////////////////////////////////////////////////
// not sure needed


    void drive (float speed, int encoderCount)
    {
        speed = -speed; //robot is backwards

        run_using_encoders();
        set_drive_power(speed, speed);
        if (have_drive_encoders_reached (encoderCount, encoderCount))
        {
            reset_drive_encoders ();
            set_drive_power (0.0f, 0.0f);
            v_state++;
        }
    }
    private class PressButton {
        int state;
        PressButton() {
            state = -1;
        }
        void reset()
        {
            state = -1;
        }
        boolean action(){
            if (state == 1){
                return false;
            }

            drive(1.0f, 2);

            state = 1;
            return true;
        }
    }
    //read beacon
    //move arm
    //move forward to press
}



