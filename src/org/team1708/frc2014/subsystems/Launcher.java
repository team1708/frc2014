package org.team1708.frc2014.subsystems;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import org.team1708.frc2014.framework.Command;
import org.team1708.frc2014.framework.Comparison;
import org.team1708.frc2014.framework.DigitalInput;
import org.team1708.frc2014.framework.MotorMechanism;
import org.team1708.frc2014.framework.MultipleMotors;
import org.team1708.frc2014.framework.PrintCommand;
import org.team1708.frc2014.framework.Sequence;
import org.team1708.frc2014.framework.SolenoidMechanism;
import org.team1708.frc2014.framework.WaitForAnalog;
import org.team1708.frc2014.framework.WaitForDigital;
import org.team1708.frc2014.framework.WaitForSeconds;

public class Launcher {
    private static MotorMechanism winch = new MotorMechanism("winch", new MultipleMotors(new Talon(7), new Talon(8)));
    private static SolenoidMechanism engage = new SolenoidMechanism ("engage", new Solenoid(3));
    private static SolenoidMechanism release  = new SolenoidMechanism ("release", new Solenoid(4));
    private static AnalogChannel lightSensor1 = new AnalogChannel(3);
    private static AnalogChannel lightSensor2 = new AnalogChannel(4);
    
    private static Command Engage;
    
    public static void init() {
        Engage = Fire();
    }
    
    public static void operatorControl()
    {
        Shoot();
        SafeShotForward();
        SafeShotBackward();
    //}
        //if (UI.gamepad2.button(8).starting()){
        //    Shot().starting();
        //}
    /*    
            if(UI.gamepad2.button(8).starting()){
                pneu.set(true);
            }
            if(UI.gamepad2.button(8).ending()){
                pneu.set(false);
        }
      */  
    //    winch.set(UI.gamepad2.getAxis(2));
    }

    public static void Shoot() {
          if (UI.gamepad1.button(8).starting()){  
              
                Engage.start();
                /*
                engage.set(false);
                release.set(true);
                winch.set(1);
          }
                
          if (UI.gamepad2.button(7).ending())
          {
              winch.set(0);
              release.set(false);
          }*/
 
          
        }
    }
    /*
    public static void Shot(){
          if (UI.gamepad2.button(8).starting()){  
              engage.set(true);
              winch.set(-.3);
              try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
              release.set(true);
          }
          if (UI.gamepad2.button(8).ending()){ 
              winch.set(0);
          }
  }
    */
    public static void SafeShotForward(){
          if (UI.gamepad1.button(10).starting()){  
              winch.set(-1);
              engage.set(false);
              release.set(true);
          }
          if (UI.gamepad1.button(10).ending()){
              winch.set(0);
              release.set(false);
          }
    }
    public static void SafeShotBackward(){
          if (UI.gamepad1.button(9).starting()){  
              winch.set(1);
              engage.set(false);
              release.set(true);
          }
          if (UI.gamepad1.button(9).ending()){
              winch.set(0);
              release.set(false);
          }
    }
    
    public static Command EnsureBack()
    {
        Sequence seq = new Sequence();
        seq.add(engage.Set(false));
        seq.add(release.Set(true));
        seq.add(winch.Set(1));
        seq.add(new WaitForAnalog(lightSensor1, Comparison.LESS, 3, 5));
        seq.add(winch.Set(0));
        seq.add(release.Set(false));
        return seq;
    }
    
    public static Command Fire(){
        Sequence Engage = new Sequence();
        Engage.add(Claw.TopClawOpen());
        Engage.add(engage.Set(true));
        Engage.add (winch.Set(-.5));
        Engage.add(new PrintCommand("waiting for disengage"));
       // Engage.add(new WaitForSeconds(.3));
        Engage.add(new PrintCommand("after disengage"));
        Engage.add(release.Set(true));
        Engage.add(winch.Set(0));
        Engage.add(new PrintCommand("waiting for fire"));
        Engage.add(new WaitForSeconds(.4));
        Engage.add(new PrintCommand("after fire"));
       //maybe topClaw close here
        Engage.add(engage.Set(false));
        Engage.add(release.Set(true));
        Engage.add(winch.Set(1));
        Engage.add(new WaitForAnalog(lightSensor1, Comparison.LESS, 3, 5));
        Engage.add(winch.Set(0));
        Engage.add(release.Set(false));
        return Engage;
    }
}