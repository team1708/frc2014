package org.team1708.frc2014.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import org.team1708.frc2014.framework.Command;
import org.team1708.frc2014.framework.MotorMechanism;
import org.team1708.frc2014.framework.SolenoidMechanism;

public class Claw {
    private static MotorMechanism intake = new MotorMechanism("intake", new Talon(9));
    private static SolenoidMechanism topClaw = new SolenoidMechanism("topClaw", new Solenoid(5));

    public static void init() {     
    }
    
    public static void operatorControl() {
        if (UI.gamepad2.button(2).starting()){
            topClaw.set(!topClaw.get());
        }
        if (UI.gamepad2.button(3).starting()) {
            intake.set(1);
        } 
        if (UI.gamepad2.button(3).ending()) {
            intake.set(0);
        }
        if (UI.gamepad2.button(1).starting()) {
            intake.set(-1);
        }
        if (UI.gamepad2.button(1).ending()) {
            intake.set(0);
        }
        if (UI.gamepad2.getAxis(2) > .1){
            topClaw.set(false);
        }     
        ////////////LIGHT SWITCH STUFF//////////////
        DriverStation.getInstance().getDashboardPackerLow().addBoolean(topClaw.get());;
        ////////////END LIGHT SWITCH STUFF//////////////
    }
    public static Command IntakeSet(){
        return intake.Set(-1);
    }  
    public static Command IntakeStop(){
        return intake.Set(0);
    }  
    public static Command TopClawOpen(){
        return topClaw.Set(true);
    }
    public static Command TopClawClose(){
        return topClaw.Set(false);
    }

    public static void reset() {
        intake.set(0);
    }
}