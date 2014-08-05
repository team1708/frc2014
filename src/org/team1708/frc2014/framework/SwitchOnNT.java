/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author team1708
 */
public class SwitchOnNT extends CommandGroup {
    
    private final String varName;
    private final String value;
    private final Command trueCmd;
    private final Command falseCmd;
    private Command exec;
    
    public SwitchOnNT(String varName, String value, Command trueCommand, Command falseCommand)
    {
        this.varName = varName;
        this.value = value;
        this.trueCmd = trueCommand;
        this.falseCmd = falseCommand;
        
        super.add(trueCommand);
        super.add(falseCommand);
    }
    
    public void add(Command command)
    {
        throw new IllegalStateException("Should not use .add() with SwitchOnNT");
    }
    
    protected void init() {
        exec = value.equals(SmartDashboard.getString(varName)) ? trueCmd : falseCmd;
    }

    protected boolean run() {
        if (exec == null) {
            return false;
        }
        
        if (exec._start())
        {
            exec.isQueued = true;
        }
        else
        {
            exec.isQueued = false;
            exec = exec.nextLink;
        }
        
        return true;
    }
}
