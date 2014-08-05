/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.client.NetworkTableClient;
import edu.wpi.first.wpilibj.networktables2.connection.NetworkTableConnection;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author team1708
 */
public class WaitWhileNT extends Command {
    
    private final String varName;
    private final String value;
    private final double timeout;;
    
    public WaitWhileNT(String varName, String value)
    {
        this(varName, value, Double.POSITIVE_INFINITY);
    }
    
    public WaitWhileNT(String varName, String value, double timeout)
    {
        this.varName = varName;
        this.value = value;
        this.timeout = timeout;
    }
    
    protected boolean requirements() {
        return NO_REQUIREMENTS;
    }
    
    protected void init() {
    }

    protected boolean run() {
        String ntvalue = SmartDashboard.getString(varName);
        System.out.println("FID: " + ntvalue + " " + value);
        for(int i=0; i < ntvalue.length(); ++i)
        {
            System.out.print((int)ntvalue.charAt(i) + "/" + (int)value.charAt(i) + " ");
        }
        System.out.println(value.equals(ntvalue));
        return value.equals(SmartDashboard.getString(varName, null)) && (timeElapsed() < timeout);
    }
    
    protected void cleanup() {
    }
}
