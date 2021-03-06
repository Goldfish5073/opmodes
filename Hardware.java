package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Jachzach on 10/18/2015.
 */

//TODO: register ColorSensor sensorRGB in here
import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.Range;

public class Hardware extends OpMode {

    //VARIABLES
    final double diameter = 4;

   /* double tab_slapper_in = 0.9D;
    double tab_slapper_out = 0.3D;*/

    final double climber_dropper_in = 0.0D;
    final double climber_dropper_out = 1.0D;
    final double climber_dropper_mid = 0.4D;
    final double climber_dropper_pusher_in = 0.0D;
    final double climber_dropper_pusher_out = 1.0D;

    final double hook_in = 0.1D;
    final double hook_out = 0.9D;
    double hook_increment = 0.05D;

    final double hoop_in = 0.2D;
    final double hoop_out = 0.6D;

    final double debris_in = 0.1D;
    final double debris_out = 0.5D;

    final double ratchet_deployed = 0.6D;
    final double ratchet_released = 0.2D;

    final double debris_mid = 0.45D;
    final double debris_low = 0.05D; //TODO - double check these values, tube shifted?
    final double debris_high = 0.9D;
    final double debris_increment = 0.05D;


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //HARDWARE INITIALIZATION

    private DcMotor v_motor_left_drive;

    private DcMotor v_motor_left_drive_back;

    private DcMotor v_motor_right_drive;

    private DcMotor v_motor_right_drive_back;

    private DcMotor v_first_arm;

    private DcMotor v_second_arm;

    private DcMotor v_claw;


    private DcMotor v_winch;

    public Servo v_hook;

    public Servo v_tab_slapper;

    public Servo v_climber_dropper;

    public Servo v_ratchet;

    public Servo v_dank_debris_dropper;

    public Servo v_hoop;



    public Hardware() {
    }


    @Override
    public void init()

    {
        //WARNING MESSAGE INTIALIZATION
        v_warning_generated = false;
        v_warning_message = "Can't map; ";



        //DRIVE MOTORS
        try {
            v_motor_left_drive = hardwareMap.dcMotor.get ("left_drive");
        } catch (Exception p_exception) {
            m_warning_message ("left_drive");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_motor_left_drive = null;
        }
        try {
            v_motor_left_drive_back = hardwareMap.dcMotor.get ("left_drive_back");
        } catch (Exception p_exception) {
            m_warning_message("left_drive_back");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_motor_left_drive_back = null;
        }try {
            v_motor_right_drive = hardwareMap.dcMotor.get ("right_drive");
            v_motor_right_drive.setDirection (DcMotor.Direction.REVERSE);
        } catch (Exception p_exception) {
            m_warning_message ("right_drive");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_motor_right_drive = null;
        }
        try {
            v_motor_right_drive_back = hardwareMap.dcMotor.get ("right_drive_back");
        } catch (Exception p_exception) {
            m_warning_message ("right_drive_back");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_motor_right_drive_back = null;
        }


        //ARM MOTORS
        try {
            v_first_arm = hardwareMap.dcMotor.get ("first_arm");
        } catch (Exception p_exception) {
            m_warning_message ("first_arm");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_first_arm = null;
        }
        try {
            v_second_arm = hardwareMap.dcMotor.get ("second_arm");
        } catch (Exception p_exception) {
            m_warning_message ("second_arm");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_second_arm = null;
        }



        //CLIMBING MOTORS
        try {
            v_claw = hardwareMap.dcMotor.get("claw");
        } catch (Exception p_exception) {
            m_warning_message ("claw");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_claw = null;
        }
        try {
            v_winch = hardwareMap.dcMotor.get ("winch");
        } catch (Exception p_exception) {
            m_warning_message ("winch");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_winch = null;
        }



        //SERVOS
        try {
            v_dank_debris_dropper = hardwareMap.servo.get ("debris");
            v_dank_debris_dropper.setPosition(debris_mid);
        } catch (Exception p_exception) {
            m_warning_message ("debris");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_dank_debris_dropper = null;
        }

        try {
            v_hook = hardwareMap.servo.get ("hook");
            v_hook.setPosition(hook_in);
        } catch (Exception p_exception) {
            m_warning_message ("hook");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_hook = null;
        }
        /*try
        {
            v_tab_slapper = hardwareMap.servo.get ("tab_slapper");
            v_tab_slapper.setPosition(tab_slapper_in);
        } catch (Exception p_exception) {
            m_warning_message ("tab_slapper");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_tab_slapper = null;
        }*/
        try
        {
            v_climber_dropper = hardwareMap.servo.get ("climber_dropper");
            v_climber_dropper.setPosition(climber_dropper_in);
        } catch (Exception p_exception) {
            m_warning_message ("climber_dropper");
            DbgLog.msg (p_exception.getLocalizedMessage ());
            v_climber_dropper = null;
        }
        try {
            v_ratchet = hardwareMap.servo.get("ratchet");
            v_ratchet.setPosition(ratchet_released);
        } catch (Exception p_exception) {
            m_warning_message("ratchet");
            DbgLog.msg(p_exception.getLocalizedMessage());
            v_ratchet = null;
        }
        try {
            v_hoop = hardwareMap.servo.get("hoop");
            v_hoop.setPosition(hoop_in);
        } catch (Exception p_exception) {
            m_warning_message("ratchet");
            DbgLog.msg(p_exception.getLocalizedMessage());
            v_hoop = null;
        }

        reset_drive_encoders ();
        run_using_encoders ();
    } // init


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //SET DRIVE POWERS
    void set_drive_power(double p_left_power, double p_right_power) {
        set_left_power(p_left_power);
        set_right_power(p_right_power);
    }
    void set_back_power(double p_left_power, double p_right_power){
        if (v_motor_right_drive_back != null) {
            v_motor_right_drive_back.setPower (p_right_power);
        }
        if (v_motor_left_drive_back != null) {
            v_motor_left_drive_back.setPower (-p_left_power);
        }
    }

    void set_front_power (double p_left_power, double p_right_power){
        if (v_motor_right_drive != null) {
            v_motor_right_drive.setPower (p_right_power);
        }
        if (v_motor_left_drive != null) {
            v_motor_left_drive.setPower (p_left_power);
        }
    }

    void set_right_power (double p_right_power) {
        if (v_motor_right_drive != null) {
            v_motor_right_drive.setPower (p_right_power);
        }
        if (v_motor_right_drive_back != null) {
            v_motor_right_drive_back.setPower (p_right_power);
        }
    }

    void set_left_power (double p_left_power) {
        if (v_motor_left_drive != null) {
            v_motor_left_drive.setPower (p_left_power);
        }
        if (v_motor_left_drive_back != null) {
            v_motor_left_drive_back.setPower (-p_left_power);
        }
    }


    //SET ARM POWERS
    void set_arm_power (double p_first_power, double p_second_power) {
        if (v_first_arm != null) {
            v_first_arm.setPower(p_first_power);
        }
        if (v_second_arm != null) {
            v_second_arm.setPower (p_second_power);
        }
    }

    void set_first_arm_power (double p_first_power) {
        if (v_first_arm != null) {
            v_first_arm.setPower(p_first_power);
        }
    }

    void set_second_arm_power (double p_second_power) {
        if (v_second_arm != null) {
            v_second_arm.setPower (p_second_power);
        }
    }

    void set_winch_power(double p_power) {
        if(v_winch != null) {
            v_winch.setPower(p_power);
        }
    }

    void set_claw_power(double p_power) {
        if(v_claw != null) {
            v_claw.setPower(p_power);
        }
    }


    //////////////////////////////////////////////////////////////////////////////
    //DEBRIS
    void debris_lower() {
        if (!(v_dank_debris_dropper.getPosition() < debris_low)) {
            v_dank_debris_dropper.setPosition(v_dank_debris_dropper.getPosition() - debris_increment);
        }
    }

    void debris_higher() {
        if (!(v_dank_debris_dropper.getPosition() > debris_high )) {
            v_dank_debris_dropper.setPosition(v_dank_debris_dropper.getPosition() + debris_increment);
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    //HOOK
    void hook_out (){
        if (v_hook != null) {
            if (v_hook.getPosition() < hook_out) {
                v_hook.setPosition(v_hook.getPosition() + hook_increment);
            }
        }
    }
    void hook_in (){
        if (v_hook.getPosition() > hook_in) {
            v_hook.setPosition (v_hook.getPosition() - hook_increment);
        }
    }


    //////////////////////////////////////////////////////////////////////////////
    // TAB SLAPPER
   /* void tab_slapper_out (){
        if(v_tab_slapper != null) {
            if (v_tab_slapper.getPosition() > tab_slapper_out) {
                v_tab_slapper.setPosition(tab_slapper_out);
            }
        }
    }
    void tab_slapper_in(){
        if (v_tab_slapper != null) {
            if (v_tab_slapper.getPosition() < tab_slapper_in) {
                v_tab_slapper.setPosition(tab_slapper_in);
            }
        }
    }*/


    /////////////////////////////////////////////////////////////////////////////
    //HOOP HOPPER
    void hoop_in (){
        if(v_hoop != null) {
            if (v_hoop.getPosition() > hoop_in) {
                v_hoop.setPosition(hoop_in);
            }
        }
    }
    void hoop_out(){
        if (v_hoop != null) {
            if (v_hoop.getPosition() < hoop_out) {
                v_hoop.setPosition(hoop_out);
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    //RATCHET
    void ratchet_deploy() {
        if (v_ratchet != null && v_ratchet.getPosition() < ratchet_deployed) {
            v_ratchet.setPosition(ratchet_deployed);
        }
    }
    void ratchet_release() {
        if(v_ratchet != null && v_ratchet.getPosition() > ratchet_released) {
            v_ratchet.setPosition(ratchet_released);
        }
    }



    /////////////////////////////////////////////////////////////////////////////
    //SECOND ARM
    void arm_up(int encodercount) {

        set_second_arm_power(0.3);
    }

    /////////////////////////////////////////////////////////////////////////////
    //CLIMBER DROPPER
    void climber_dropper_in() {
        if(v_climber_dropper.getPosition() > climber_dropper_in){
            v_climber_dropper.setPosition(climber_dropper_in);
        }
    }

    void climber_dropper_mid() {
        v_climber_dropper.setPosition(climber_dropper_mid);
    }

    void climber_dropper_out(){
        if(v_climber_dropper.getPosition() < climber_dropper_out){
            v_climber_dropper.setPosition(climber_dropper_out);
        }
    }
    void climber_dropper_pusher_in(){
        if(v_climber_dropper.getPosition() > climber_dropper_pusher_in){
            v_climber_dropper.setPosition(climber_dropper_pusher_in);
        }
    }
    void climber_dropper_pusher_out(){
        if(v_climber_dropper.getPosition() < climber_dropper_pusher_out){
            v_climber_dropper.setPosition(climber_dropper_pusher_out);
        }
    }





    ////////////////////////////////////////////////////////////////////////////////////////////////
    //TELEMETRY
    public void set_first_message (String p_message) {
        telemetry.addData("0000", p_message);
    }

    public void set_error_message (String p_message) {
        set_first_message("ERROR: " + p_message);
    }

    public void update_telemetry () {
        if (a_warning_generated ()) {
            set_first_message (a_warning_message ());
        }

        telemetry.addData("0001", "HARDWARE----------------------");
        if(v_motor_left_drive != null) {
            telemetry.addData("001", "Left Drive: " + a_left_drive_power() + ", " + a_left_encoder_count());
        }
        if(v_motor_right_drive != null) {
            telemetry.addData("002", "Right Drive: " + a_right_drive_power() + ", " + a_right_encoder_count());
        }
        if(v_first_arm != null){
            telemetry.addData("003", "First Arm: " + v_first_arm.getPower());
        }
        if(v_second_arm != null) {
            telemetry.addData("004", "Second Arm: " + v_second_arm.getPower());
        }
        if(v_winch != null) {
            telemetry.addData("005", "Winch Drive: " + a_winch_power());
        }
        if(v_claw != null){
            telemetry.addData("006", "Claw: " + a_claw_power());
        }
       /* if(v_tab_slapper != null) {
            telemetry.addData("007", "Tab Slapper: " + v_tab_slapper.getPosition());
        }*/
        if(v_climber_dropper != null) {
            telemetry.addData("008", "Climber Dropper: " + v_climber_dropper.getPosition());
        }
        if (v_dank_debris_dropper != null) {
            telemetry.addData("009", "Debris Dropper: " + v_dank_debris_dropper.getPosition());
        }
    }

    public void update_gamepad_telemetry () {
        telemetry.addData("100", "GAMEPAD 1-------------------------------");
        if(gamepad1 != null) {
            telemetry.addData("101", "GP1 LJoy: " + -gamepad1.left_stick_y);
            telemetry.addData("102", "GP1 RJoy: " + -gamepad1.right_stick_y);
            telemetry.addData("103", "GP1 LT: " + gamepad1.left_trigger + " (claw)");
            telemetry.addData("104", "GP1 LB: " + gamepad1.left_bumper + " (claw)");
            telemetry.addData("105", "GP1 RT: " + gamepad1.right_trigger);
            telemetry.addData("106", "GP1 RB: " + gamepad1.right_bumper);
            telemetry.addData("107", "GP1 A: " + gamepad1.a);
            telemetry.addData("108", "GP1 B: " + gamepad1.b);
            telemetry.addData("109", "GP1 DPad LEFT: " + gamepad1.dpad_left);
            telemetry.addData("110", "GP1 DPad RIGHT: " + gamepad1.dpad_right);

        }
        if(gamepad2 != null) {
            telemetry.addData("200", "GAMEPAD 2-------------------------------");
            telemetry.addData("201", "GP2 LJoy: " + -gamepad2.left_stick_y);
            telemetry.addData("202", "GP2 RJoy: " + -gamepad2.right_stick_y);
            telemetry.addData("203", "GP2 LT: " + gamepad2.left_trigger);
            telemetry.addData("204", "GP2 LB: " + gamepad2.left_bumper);
            telemetry.addData("205", "GP2 RT: " + gamepad2.right_trigger);
            telemetry.addData("206", "GP2 RB: " + gamepad2.right_bumper);
            telemetry.addData("207", "GP2 X: " + gamepad2.x);
            telemetry.addData("208", "GP2 Y: " + gamepad2.y);
            telemetry.addData("209", "GP2 START: " + gamepad2.start);
            telemetry.addData("210", "GP2 BACK: " + gamepad2.back);
        }
    } // update_gamepad_telemetry

    double a_left_drive_power () {
        double l_return = 0.0;
        if (v_motor_left_drive != null) {
            l_return = v_motor_left_drive.getPower ();
        }
        return l_return;
    } // a_left_drive_power

    double a_right_drive_power () {
        double l_return = 0.0;
        if (v_motor_right_drive != null) {
            l_return = v_motor_right_drive.getPower ();
        }
        return l_return;
    } // a_right_drive_power

    double a_claw_power () {
        double l_return = -999;
        if (v_claw != null) {
            l_return = v_claw.getPower();
        }
        return l_return;
    } // a_claw_power

    double a_winch_power() {
        double l_return = -999;
        if (v_winch != null) {
            l_return = v_winch.getPower();
        }
        return l_return;
    } // a_winch_power


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //WARNING MESSAGES - PREVENT CRASH
    void m_warning_message (String p_exception_message) {
        if (v_warning_generated) {
            v_warning_message += ", ";
        }
        v_warning_generated = true;
        v_warning_message += p_exception_message;
    }
    private boolean v_warning_generated = false;
    private String v_warning_message;
    String a_warning_message () { //goes into telemetry to report
        return v_warning_message;
    }
    boolean a_warning_generated () {
        return v_warning_generated;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    //START
    @Override public void start () {
        //will be overriden
    } // start

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //LOOP
    @Override public void loop () {
        //will be overriden
    } // loop

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //LOOP
    @Override public void stop () {
        //called once when OpMode disabled, can be overridden
    } // stop




    ///////////////////////////////////////////////////////////////////////////////////////////////
    //ENCODER SETUP

    //RUN WITH
    public void run_using_left_drive_encoder () {
        if (v_motor_left_drive_back != null) {
            v_motor_left_drive_back.setChannelMode( DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    } // run_using_left_drive_encoder

    public void run_using_right_drive_encoder () {
        if (v_motor_right_drive_back != null) {
            v_motor_right_drive_back.setChannelMode( DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    } // run_using_right_drive_encoder

    public void run_using_second_arm_encoder() {
        if (v_second_arm != null) {
            v_second_arm.setChannelMode( DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    }

    public void run_using_encoders () {
        run_using_left_drive_encoder ();
        run_using_right_drive_encoder ();
    } // run_using_encoders



    //RUN WITHOUT ENCODERS
    public void run_without_left_drive_encoder () {
        if (v_motor_left_drive_back != null) {
            if (v_motor_left_drive_back.getChannelMode () == DcMotorController.RunMode.RESET_ENCODERS) {
                v_motor_left_drive_back.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            }
        }
    } // run_without_left_drive_encoder

    public void run_without_right_drive_encoder () {
        if (v_motor_right_drive_back != null) {
            if (v_motor_right_drive_back.getChannelMode () == DcMotorController.RunMode.RESET_ENCODERS) {
                v_motor_right_drive_back.setChannelMode( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            }
        }
    } // run_without_right_drive_encoder

    public void run_without_second_arm_encoder() {
        if (v_second_arm != null) {
            if (v_second_arm.getChannelMode() == DcMotorController.RunMode.RESET_ENCODERS) {
                v_second_arm.setChannelMode( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            }
        }
    }

    public void run_without_drive_encoders () {
        run_without_left_drive_encoder();
        run_without_right_drive_encoder();
    } // run_without_drive_encoders



    //RESET
    public void reset_left_drive_encoder () {
        if (v_motor_left_drive_back != null) {
            v_motor_left_drive_back.setChannelMode( DcMotorController.RunMode.RESET_ENCODERS);
        }
    } // reset_left_drive_encoder

    public void reset_right_drive_encoder () {
        if (v_motor_right_drive_back != null) {
            v_motor_right_drive_back.setChannelMode( DcMotorController.RunMode.RESET_ENCODERS);
        }
    } // reset_right_drive_encoder

    public void reset_second_arm_encoder () {
        if (v_second_arm != null) {
            v_second_arm.setChannelMode( DcMotorController.RunMode.RESET_ENCODERS);
        }
    } // reset_right_drive_encoder

    public void reset_drive_encoders () {
        reset_left_drive_encoder();
        reset_right_drive_encoder();
    } // reset_drive_encoders



    //HAVE RESET
    boolean has_left_drive_encoder_reset () {
        boolean l_return = false;
        if (a_left_encoder_count () == 0) {
            l_return = true;
        }
        return l_return;
    } // has_left_drive_encoder_reset

    boolean has_right_drive_encoder_reset () {
        boolean l_return = false;
        if (a_right_encoder_count() == 0) {
            l_return = true;
        }
        return l_return;
    } // has_right_drive_encoder_reset

    boolean has_second_arm_encoder_reset () {
        boolean l_return = false;
        if (a_second_arm_count() == 0) {
            l_return = true;
        }
        return l_return;
    } // has_second_arm_encoder_reset

    boolean have_drive_encoders_reset () {
        boolean l_return = false;
        if (has_left_drive_encoder_reset() && has_right_drive_encoder_reset ()) {
            l_return = true;
        }
        return l_return;
    } // have_drive_encoders_reset



    //ENCODER COUNTS
    int a_left_encoder_count () {
        int l_return = 0;
        if (v_motor_left_drive_back != null) {
            l_return = (int) (Math.abs (v_motor_left_drive_back.getCurrentPosition ()) * (diameter * Math.PI) / 1120);
        }
        return l_return;
    } // a_left_encoder_count

    int a_right_encoder_count () {
        int l_return = 0;
        if (v_motor_right_drive_back != null) {
            l_return = (int) (Math.abs(v_motor_right_drive_back.getCurrentPosition ()) * (diameter * Math.PI) / 1120);
        }
        return l_return;
    } // a_right_encoder_count

    int a_second_arm_count () {
        int l_return = 0;
        if (v_second_arm != null) {
            l_return = (int) Math.abs(v_second_arm.getCurrentPosition ());
        }
        return l_return;
    } // a_right_encoder_count


    //HAVE REACHED
    boolean has_left_drive_encoder_reached (double p_count) {
        boolean l_return = false;
        if (v_motor_left_drive_back != null){
            if (Math.abs (v_motor_left_drive_back.getCurrentPosition () * (diameter * Math.PI) / 1120) > p_count) {
                l_return = true;
            }
        }
        return l_return;
    } // has_left_drive_encoder_reached

    boolean has_right_drive_encoder_reached (double p_count) {
        boolean l_return = false;
        if (v_motor_right_drive_back != null) {
            if (Math.abs (v_motor_right_drive_back.getCurrentPosition () * (diameter * Math.PI) / 1120) > p_count) {
                l_return = true;
            }
        }
        return l_return;
    } // has_right_drive_encoder_reached

    boolean has_second_arm_encoder_reached (double p_count) {
        boolean l_return = false;
        if (v_second_arm != null) {
            if (Math.abs(v_second_arm.getCurrentPosition ()) > p_count) {
                l_return = true;
            }
        }
        return l_return;
    } // has_right_drive_encoder_reached

    boolean have_drive_encoders_reached (double p_left_count, double p_right_count) {
        boolean l_return = false;
        if (has_left_drive_encoder_reached(p_left_count) && has_right_drive_encoder_reached (p_right_count)) {
            l_return = true;
        }
        return l_return;
    } // have_encoders_reached



    //DRIVE USING ENCODERS
    boolean drive_using_encoders( double p_left_power, double p_right_power, double p_left_count, double p_right_count) {
        boolean l_return = false;
        run_using_encoders ();
        set_drive_power (p_left_power, p_right_power);
        if (have_drive_encoders_reached (p_left_count, p_right_count)) {
            reset_drive_encoders ();
            set_drive_power (0.0f, 0.0f);
            l_return = true;
        }
        return l_return;
    } // drive_using_encoders

} // hardware


