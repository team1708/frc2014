package org.team1708.frc2014.subsystems;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import org.team1708.frc2014.framework.Command;
import org.team1708.frc2014.framework.Parallel;
import org.team1708.frc2014.framework.SolenoidMechanism;

public class Arm {
//    private static AnalogPotentiometer pot = new AnalogPotentiometer(2);
//    private static MotorMechanism arm = new MotorMechanism("arm", new Victor(5), pot);
    
    private static SolenoidMechanism armShort = new SolenoidMechanism ("armShort", new Solenoid(2));
    private static SolenoidMechanism armLong  = new SolenoidMechanism ("armLong", new Solenoid(1));
    
    public static void init() {    
    }  
    public static void operatorControl()
    {
//ARM PNEUMATICS
     //UP
     if (UI.gamepad2.getAxis(2) < -.1){
            armShort.set(false);
            armLong.set(false);
        }
     //LONG SHOT
     if (UI.gamepad2.getAxis(1) <- .1){
            armShort.set(false);
            armLong.set(true);
        }       
     //SHORT SHOT
     if (UI.gamepad2.getAxis(1) > .1){
            armShort.set(true);
            armLong.set(false);
        }    
     //PICK UP
     if (UI.gamepad2.getAxis(2) > .1){
            armShort.set(true);
            armLong.set(true);
            
        }
        ///////driver station stuff////////
        DriverStation.getInstance().getDashboardPackerLow().addBoolean(armShort.get());
        DriverStation.getInstance().getDashboardPackerLow().addBoolean(armLong.get());
        ///////driver station stuff////////
    }
    
    public static Command ArmShortShot(){
       Parallel pneumatics = new Parallel();
            pneumatics.add(armLong.Set(false));
            pneumatics.add(armShort.Set(true)); 
            return pneumatics;
    }
    public static Command ArmLongShot(){
       Parallel pneumatics = new Parallel();
            pneumatics.add(armLong.Set(true));
            pneumatics.add(armShort.Set(false)); 
            return pneumatics;
    }
    
    public static Command ArmPickup(){
       Parallel pneumatics = new Parallel();
            pneumatics.add(armLong.Set(true));
            pneumatics.add(armShort.Set(true)); 
            return pneumatics;
    }
    public static Command ArmUp(){
       Parallel pneumatics = new Parallel();
            pneumatics.add(armLong.Set(false));
            pneumatics.add(armShort.Set(false)); 
            return pneumatics;
    }
    
    
//ARM CHAIN AND MOTOR
     /*
         if (UI.gamepad2.getAxis(2) > .01){
             arm.set(-.9);
         }
         else if (UI.gamepad2.getAxis(2) < -.01){
             arm.set(.5);
         }
         else {rn arm.Set(-1); 
    }
             arm.set(0);
         }
     */

    /*         
    public static Command ArmDown(){
       return arm.Set(1); 
    }
    public static Command ArmUp(){
       retu
    public static Command StopArm() {
       return arm.Set(0);
    }
    */
    
}