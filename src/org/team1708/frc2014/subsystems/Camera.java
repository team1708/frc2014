/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team1708.frc2014.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1708.frc2014.framework.Command;
import org.team1708.frc2014.framework.SwitchOnNT;
import org.team1708.frc2014.framework.WaitForNT;
import org.team1708.frc2014.framework.WaitWhileNT;


/**
 *
 * @author team1708
 */
public class Camera {
    //public static Command Read(Command Left, Command Right){
      //  return new SwitchOnNT("/SmartDashboard/FIDUCIAL_NAME", "Left", Left, Right);
    public static void restart(){
        SmartDashboard.putString("FIDUCIAL_NAME" , "\\pause.gif");
    }
    public static Command goRight(Command StartAutoRight,Command StartAutoLeft){
        return new SwitchOnNT("FIDUCIAL_NAME", "\\right.gif", StartAutoRight, StartAutoLeft );
    }   
    //public static Command twoBall(Command StartAutoRight,Command StartTwoBall){
        //return new SwitchOnNT("FIDUCIAL_NAME", "\\right.gif", StartAutoRight, StartAutoLeft );    
    public static Command pause (){
        return new WaitWhileNT("FIDUCIAL_NAME", "\\pause.gif");
    }
}
