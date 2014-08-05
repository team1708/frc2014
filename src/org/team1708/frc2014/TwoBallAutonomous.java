package org.team1708.frc2014;
import org.team1708.frc2014.framework.Parallel;
import org.team1708.frc2014.framework.PrintCommand;
import org.team1708.frc2014.framework.Sequence;
import org.team1708.frc2014.framework.WaitForSeconds;
import org.team1708.frc2014.subsystems.Arm;
import org.team1708.frc2014.subsystems.Camera;
import org.team1708.frc2014.subsystems.Claw;
import org.team1708.frc2014.subsystems.Drivetrain;
import org.team1708.frc2014.subsystems.Launcher;
/**
 *
 * @author team1708
 */
public class TwoBallAutonomous extends Sequence { 
    public TwoBallAutonomous() {
        add(new PrintCommand("Start of autonomous"));
///////////////////////////////////////////////LEFT TWOBALL AUTONOMOUS////////////////////////////////////////////////
        Parallel StartLeftForward = new Parallel();
            //StartLeftForward.add(Drivetrain.SpinLeft());
            StartLeftForward.add(Drivetrain.Gyro(-15));
            StartLeftForward.add(Claw.TopClawClose());
            StartLeftForward.add(Arm.ArmUp());
  
            
        Parallel LeftBallPickUp = new Parallel();
            LeftBallPickUp.add(Arm.ArmPickup());
            LeftBallPickUp.add(Claw.TopClawClose());
            LeftBallPickUp.add(Claw.IntakeSet());
            
/////////////////////////////////////////////////////////////     
        Sequence StartAutoLeft = new Sequence();
            //StartAutoLeft.add(Drivetrain.MoveForward(1));
            StartAutoLeft.add(StartLeftForward);
            StartAutoLeft.add(Arm.ArmShortShot());
            StartAutoLeft.add(Launcher.Fire());
            
            //StartAutoLeft.add(Drivetrain.SpinRight());
            StartAutoLeft.add(Drivetrain.Gyro(110));
            StartAutoLeft.add(new WaitForSeconds(1));
            StartAutoLeft.add(LeftBallPickUp);
            StartAutoLeft.add(Drivetrain.MoveForwardTime());
            StartAutoLeft.add(new WaitForSeconds(1.5));
            StartAutoLeft.add(Drivetrain.StopDrive());
            StartAutoLeft.add(Claw.IntakeStop());
            
            StartAutoLeft.add(Drivetrain.SpinLeft());
            StartAutoLeft.add(new WaitForSeconds(.65));
            //StartAutoLeft.add(Drivetrain.Gyro(-160));
            StartAutoLeft.add(Drivetrain.StopDrive());
            
            StartAutoLeft.add(Arm.ArmShortShot());
            StartAutoLeft.add(Launcher.Fire());
            StartAutoLeft.add(Drivetrain.MoveForward(3));
//////////////////////////////////////LEFT TWOBALL AUTONOMOUS////////////////////////////////////////////////            

            
//////////////////////////////////////RIGHT TWOBALL AUTONOMOUS////////////////////////////////////////////////                    
        Parallel StartRightForward = new Parallel();
            StartRightForward.add(Drivetrain.SpinRight());
            // StartRightForward//add(Drivetrain.Gyro(15));
            StartRightForward.add(Claw.TopClawOpen());
            StartRightForward.add(Arm.ArmUp());
  
            
        Parallel RightBallPickUp = new Parallel();
            RightBallPickUp.add(Arm.ArmPickup());
            RightBallPickUp.add(Claw.TopClawClose());
            RightBallPickUp.add(Claw.IntakeSet());
            RightBallPickUp.add(Drivetrain.MoveForward(1));
        
        Parallel ShootRight = new Parallel();    
            ShootRight.add(Arm.ArmShortShot());
            ShootRight.add(Drivetrain.MoveForward(1));  
/////////////////////////////////////////////////////////////              
        Sequence StartAutoRight = new Sequence();
            //StartAutoRight.add(Drivetrain.MoveForward(1));
            StartAutoRight.add(StartRightForward);
            StartAutoRight.add(Arm.ArmShortShot());
            StartAutoRight.add(Launcher.Fire());
            
            //StartAutoRight.add(Drivetrain.SpinRight());
            StartAutoRight.add(Drivetrain.Gyro(105));
            StartAutoRight.add(new WaitForSeconds(1));
            StartAutoRight.add(RightBallPickUp);
            StartAutoRight.add(Drivetrain.MoveForwardTime());
            StartAutoRight.add(new WaitForSeconds(1.5));
            StartAutoRight.add(Drivetrain.StopDrive());
            StartAutoRight.add(Claw.IntakeStop());
            
            StartAutoRight.add(Drivetrain.SpinLeft());
            StartAutoRight.add(new WaitForSeconds(.65));
            //StartAutoRight.add(Drivetrain.Gyro(-160));
            StartAutoRight.add(Drivetrain.StopDrive());
            
            StartAutoRight.add(Arm.ArmShortShot());
            StartAutoRight.add(Launcher.Fire());
            StartAutoRight.add(Drivetrain.MoveForward(3));
            
            add(Camera.pause());
            add(Camera.goRight(StartAutoRight, StartAutoLeft));
            add(Camera.pause());
//////////////////////////////////////RIGHT TWOBALL AUTONOMOUS////////////////////////////////////////////////                    
    }
}