/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team1708.frc2014;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.io.IOException;
import org.team1708.frc2014.framework.Command;
import org.team1708.frc2014.framework.Scheduler;
import org.team1708.frc2014.subsystems.Arm;
import org.team1708.frc2014.subsystems.Claw;
import org.team1708.frc2014.subsystems.Drivetrain;
import org.team1708.frc2014.subsystems.ExtraAir;
import org.team1708.frc2014.subsystems.Launcher;
import org.team1708.frc2014.subsystems.Lights;
import org.team1708.frc2014.subsystems.UI;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends SimpleRobot {

    Command autonomous;
    
    static
    {
        NetworkTable.setClientMode();
        NetworkTable.setIPAddress("10.17.8.5");
        try {
            NetworkTable.initialize();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        UI.init();
        
        Drivetrain.init();
        Arm.init();
        Claw.init();
        Launcher.init();
        ExtraAir.init();
        //Lights.init();
        
        System.out.println("Robot started.");
    }
    
    public void test() {
        while(isTest()) {
            LiveWindow.run();
        }
    }
    
    public void disabled() {
        
    }
    
    public void autonomous() {
        try
        {
            Scheduler.clear();
            autonomous = new OneBallAutonomous();
            //autonomous = Test.testTurn();
            System.out.println("Autonomous begin");
            autonomous.start();
            while(isAutonomous() && isEnabled()) {
                Scheduler.run();
                Thread.yield();
            }
            autonomous.cancel();
        }
        catch(Exception e)
        {
            System.out.println("EXCEPTION!!!!: " + e);
            e.printStackTrace();
        }
    }

    public void operatorControl() {
        Drivetrain.reset();
        Claw.reset();
        
        Scheduler.clear();
        
        Launcher.EnsureBack().start();
        
        while(isOperatorControl() && isEnabled())
        {
            try
            {
                //if (m_ds.isNewControlData())
                {
                    Drivetrain.operatorControl();
                    Arm.operatorControl();
                    Claw.operatorControl();
                    Launcher.operatorControl();
                }

                Scheduler.run();
                
                DriverStation.getInstance().getDashboardPackerLow().commit();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }
}
