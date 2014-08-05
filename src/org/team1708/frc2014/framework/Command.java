/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author rcahoon
 */
public abstract class Command {
    Command nextLink = null;
    Command prevLink = null;
    boolean isRunning, isQueued;
    Command parentCommand = null;
    private boolean canInterrupt = true;
    private double m_startTime;
    
    public void setInterruptible(boolean v)
    {
        canInterrupt = v;
    }
    
    protected static final boolean NO_REQUIREMENTS = true;
    
    static Command getRoot(Command cmd)
    {
    	if (cmd.parentCommand == null)
    		return cmd;
    	return getRoot(cmd.parentCommand);
    }
    
    protected boolean requires(Subsystem subsystem)
    {
        if (isRunning)
        {
            if (subsystem.currentCommand != null)
                subsystem.currentCommand.cancel();
            subsystem.currentCommand = this;
            return true;
        }
        else
        {
            if (subsystem.currentCommand == this)
                subsystem.currentCommand = null;
            return (subsystem.currentCommand == null ||
                (subsystem.currentCommand.isInterruptible() && getRoot(this) != getRoot(subsystem.currentCommand)));
        }
    }
    
    protected abstract boolean requirements();
    
    protected abstract void init();
    
    protected abstract boolean run();
    
    protected abstract void cleanup();
    
    public final double timeElapsed() {
        return Math.max(0, Timer.getFPGATimestamp() - m_startTime);
    }
    
    boolean _start()
    {
        if (!isRunning)
        {
            if (!requirements())
            {
                if (!isQueued)
                    System.out.println("Requirements conflict");
                return true;
            }
            isRunning = true;
            requirements();
        	
            this.init();
            m_startTime = Timer.getFPGATimestamp();
        }
        if (this.run())
        {
            return true;
        }
        else
        {
            isRunning = false;
            cleanup();
            
            requirements();
            
            return false;
        }
    }
    
    public void start()
    {
        if (parentCommand != null)
            throw new IllegalStateException("Should not directly run a parented command");
        
        boolean isInit = !isRunning;
        
        if (_start())
        {
            if (isInit && !isQueued)
                Scheduler.add(this);
        }
        else
        {
            if (!isInit)
                Scheduler.remove(this);
        }
    }

    private boolean isInterruptible()
    {
        if (!canInterrupt)
            return false;
        if (parentCommand != null &&
            !parentCommand.isInterruptible())
            return false;
        return true;
    }
    
    public void cancel() {
        if (parentCommand != null)
            parentCommand.cancel();
        if (!isRunning)
            return;
        if (!canInterrupt)
            throw new IllegalStateException("Cannot interrupt this command");
        if (parentCommand != null)
        	Scheduler.remove(this);
        isRunning = false;
        cleanup();
    }
    
    void setParent(Command parent)
    {
        if (isRunning)
            throw new IllegalStateException("This command cannot be parented - it is already runnning");
        if (parent != null)
        {
            if (parentCommand != null)
                throw new IllegalStateException("This command already has a parent");
            if (!canInterrupt)
                parent.canInterrupt = false;
        }
        parentCommand = parent;
    }
}
