/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author rcahoon
 */
public class MotorMechanism extends Subsystem {
    final String name;
    private final SpeedController device;
    private final PID controller;
    private boolean reversed;
    
    public MotorMechanism(String name, SpeedController device)
    {
        this(name, device, null);
    }
    
    public MotorMechanism(final String name, final SpeedController device, final PIDSource sensor)
    {
        this.name = name;
        
        if (sensor != null)
        {
            controller = new PID(name, new PIDOutput() {
                public void pidWrite(double d) {
                    device.pidWrite(reversed ? -d : d);
                }
            }, sensor);
        }
        else
        {
            controller = null;
        }
        
        this.device = device;
    }

    public void setFeedforward(double f)
    {
        if (controller != null)
        {
            controller.setFeedforward(f);
        }
    }
    
    public void setReversed(boolean reversed)
    {
        this.reversed = reversed;
    }
    
    private class Regulate extends Command
    {
        private final double setpoint;
        
        public Regulate(double setpoint)
        {
            if (controller == null)
                throw new IllegalStateException("Cannot regulate motor " + MotorMechanism.this.name + " because it has no sensor attached");
            
            this.setpoint = setpoint;
        }

        protected boolean requirements() {
            return requires(MotorMechanism.this);
        }
        
        protected void init() {
            controller.setSetpoint(setpoint);
            controller.enable();
        }

        protected boolean run() {
            return !controller.onTarget();
        }

        protected void cleanup() {
        }
    }

    private class Set extends Command
    {
        private final double command;
        
        public Set(double command)
        {
            this.command = command;
        }

        protected boolean requirements() {
            return requires(MotorMechanism.this);
        }
        
        protected void init() {
        }

        protected boolean run() {
            set(command);
            
            return false;
        }

        protected void cleanup() {
        }
    }
    
    public Command Regulate(double setpoint)
    {
        return new Regulate(setpoint);
    }
    
    public void regulate(double setpoint)
    {
        if (controller == null)
            throw new IllegalStateException("Cannot regulate motor " + MotorMechanism.this.name + " because it has no sensor attached");
        controller.setSetpoint(setpoint);
        controller.enable();
    }
    
    public Command Hold()
    {
        return new Regulate(controller.m_pidInput.pidGet());
    }
    
    public void hold()
    {
        regulate(controller.m_pidInput.pidGet());
    }
    
    public Command Set(double command)
    {
        return new Set(command);
    }
    
    public void set(double command)
    {
        device.set(reversed ? -command : command);
        if (controller != null)
        {
            controller.disable();
            controller.setSetpoint(command);
        }
    }
}
