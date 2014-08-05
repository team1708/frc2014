/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author rcahoon
 */
public class SolenoidMechanism extends Subsystem {
    final String name;
    private final Solenoid ss;
    private final DoubleSolenoid ds;
    
    public SolenoidMechanism(String name, int port)
    {
        this(name, new Solenoid(port));
    }
    public SolenoidMechanism(String name, int port1, int port2)
    {
        this(name, new DoubleSolenoid(port1, port2));
    }
    public SolenoidMechanism(String name, Solenoid ss)
    {
        this.name = name;
        this.ss = ss;
        this.ds = null;
    }
    public SolenoidMechanism(String name, DoubleSolenoid ds)
    {
        this.name = name;
        this.ss = null;
        this.ds = ds;
        ds.set(DoubleSolenoid.Value.kReverse);
    }
    
    private double timeForward = 0.5, timeReverse = 0.5;
    
    public void setTiming(double time)
    {
        this.timeForward = time;
        this.timeReverse = time;
    }
    
    private class Set extends Command
    {
        private final boolean direction;
        private boolean skip;
        
        public Set(boolean direction)
        {
            this.direction = direction;
        }

        protected boolean requirements() {
            return requires(SolenoidMechanism.this);
        }
        
        protected void init() {
            skip = (direction == get());
        }

        protected boolean run() {
            set(direction);
            
            return !skip && (timeElapsed() < (direction ? timeForward : timeReverse));
        }

        protected void cleanup() {
        }
    }
    
    public Command Set(boolean direction)
    {
        return new Set(direction);
    }
    
    public boolean get() {
        if (ss != null)
            return ss.get();
        if (ds != null)
            return (ds.get() == DoubleSolenoid.Value.kForward);
        return false;
    }
    
    public void set(boolean direction)
    {
        if (ss != null)
            ss.set(direction);
        if (ds != null)
            ds.set(direction ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }
}
