package org.team1708.frc2014;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1708.frc2014.framework.Parallel;
import org.team1708.frc2014.framework.PrintCommand;
import org.team1708.frc2014.framework.Sequence;
import org.team1708.frc2014.framework.WaitForSeconds;
import org.team1708.frc2014.subsystems.Arm;
import org.team1708.frc2014.subsystems.Camera;
import org.team1708.frc2014.subsystems.Claw;
import org.team1708.frc2014.subsystems.Drivetrain;
import org.team1708.frc2014.subsystems.Launcher;

public class PushyAutonomous extends Sequence {

    public PushyAutonomous()
    {
        //Camera.reset();
        add(new PrintCommand("Start of autonomous"));
///////////////////////Left auto//////////////////////////////
        
/////////////////////Right Auto////////////////////////////// 
            //SWITCH NUMBERS!!
        Parallel StartForward = new Parallel();
        //StartRightForward.add(Drivetrain.SpinRight());
            StartForward.add(Claw.TopClawClose());
            StartForward.add(Arm.ArmUp());
            StartForward.add(Drivetrain.LowGear());
        
        Parallel StartBackwards = new Parallel();
        //StartRightForward.add(Drivetrain.SpinRight());
            StartBackwards.add(Claw.TopClawClose());
            StartBackwards.add(Arm.ArmPickup());
            StartBackwards.add(Drivetrain.MoveForward(-3.5));
        
        add(Camera.pause());
        add(StartForward);
        add(Drivetrain.MoveForward(20));
        add(Arm.ArmShortShot());
        add(Launcher.Fire());
        
        
    }
}