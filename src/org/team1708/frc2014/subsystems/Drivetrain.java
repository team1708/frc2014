package org.team1708.frc2014.subsystems;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import org.team1708.frc2014.framework.Command;
import org.team1708.frc2014.framework.Encoder;
import org.team1708.frc2014.framework.Gyro;
import org.team1708.frc2014.framework.MultipleMotors;
import org.team1708.frc2014.framework.RobotDrive;
import org.team1708.frc2014.framework.SolenoidMechanism;

public class Drivetrain {
    private static RobotDrive drive = new RobotDrive(new MultipleMotors(new Talon(1), new Talon(2)), new MultipleMotors(new Talon(3), new Talon(4)), new Encoder(1, 2, -.001/*Encoder scale = feet*/), new Encoder(3,4, .001/*Encoder scale = feet*/), new Gyro(1, 0.00168));
    private static SolenoidMechanism shifters = new SolenoidMechanism ("shifters",new Solenoid (8));
    private static AnalogChannel currentControl = new AnalogChannel(2);
    
    public static void init() {
        drive.setLeftReversed(false);
        drive.setRightReversed(false);
        currentControl.setAverageBits((int)(MathUtils.log(currentControl.getModule().getSampleRate() * 1)/MathUtils.log(2)));
    }
    
    public static void operatorControl()
    {
        drive.setArcadeDrive(MathUtils.pow(-UI.gamepad1.getAxis(2), 3), MathUtils.pow(UI.gamepad1.getAxis(3), 3));
        
        //Left
        if (UI.gamepad1.button(5).starting()){
            shifters.set(true);
        }
        //Right
        if (UI.gamepad1.button(6).starting()){
            shifters.set(false);
        }
        /*
        if (currentControl.getAverageVoltage() >= 2){
            shifters.set(true);
        }
        if (currentControl.getAverageVoltage() <= 2 && drive.getSpeed() > 7){
            shifters.set(false);
        }*/
        /*if (UI.gamepad1.button(7).starting()){
            drive.RegulateTurn(-90).start();
        }
        if (UI.gamepad1.button(8).starting()){
            drive.RegulateTurn(90).start();
        }*/
        //System.out.println("Voltage:" + currentControl.getAverageVoltage());
    }
    public static Command MoveForward(double encoder){
        return drive.RegulateDrive(encoder);
    }
    public static Command Gyro(double gyro){
        return drive.RegulateTurn(gyro);
    }
    public static Command StopDrive(){
        return drive.SetArcadeDrive(0, 0);
    }
    public static Command MoveForwardTime(){
        return drive.SetArcadeDrive(.7, 0);
    }    
    public static Command SpinLeft(){
        return drive.SetArcadeDrive(0, -.7);
    }          
    public static Command SpinRight(){
        return drive.SetArcadeDrive(0, .7);
    }
    public static Command LowGear(){
        return shifters.Set(true);
    }
    public static Command HighGear(){
        return shifters.Set(false);
    }

    public static void reset() {
        drive.setArcadeDrive(0, 0);
        shifters.set(false);
    }
}