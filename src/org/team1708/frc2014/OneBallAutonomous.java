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

public class OneBallAutonomous extends Sequence {

    public OneBallAutonomous()
    {      
        //Camera.reset();
        add(new PrintCommand("Start of autonomous"));
///////////////////////Left auto//////////////////////////////
        Parallel StartLeftForward = new Parallel();
            //StartLeftForward.add(Drivetrain.SpinLeft());
            StartLeftForward.add(Drivetrain.Gyro(-20));
            StartLeftForward.add(Claw.TopClawClose());
            StartLeftForward.add(Arm.ArmUp());
            
        Sequence StartAutoLeft = new Sequence();
            StartAutoLeft.add(StartLeftForward);
            StartAutoLeft.add(Arm.ArmShortShot());
            StartAutoLeft.add(Launcher.Fire());

/////////////////////Right Auto////////////////////////////// 
            //SWITCH NUMBERS!!
        Parallel StartRightForward = new Parallel();
            StartRightForward.add(Drivetrain.Gyro(20));
        //StartRightForward.add(Drivetrain.SpinRight());
            StartRightForward.add(Claw.TopClawClose());
            StartRightForward.add(Arm.ArmUp());
            
        Sequence StartAutoRight = new Sequence();
        //for this, we may wanna just turn and shoot1qs 
            StartAutoRight.add(StartRightForward);
            StartAutoRight.add(Arm.ArmShortShot());
            StartAutoRight.add(Launcher.Fire());
            
            add(Drivetrain.LowGear());
            add(Drivetrain.MoveForward(16.5));            
            add(Camera.pause());
            add(Camera.goRight(StartAutoRight, StartAutoLeft));
            add(Camera.pause());
    }
}