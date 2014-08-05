/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * @author rcahoon
 */
public class Joystick extends Polling {
    
    static final double deadZone = .05;
    static final int kNumButtons = 12;
    static final int kNumAxes = DriverStation.kJoystickAxes;
    protected DigitalSource[] buttons = new DigitalSource[kNumButtons];
    protected boolean[] buttonVals = new boolean[kNumButtons];
    protected DigitalSource[] cmdAxes = new DigitalSource[kNumAxes];
    protected double[] axes = new double[kNumAxes];
    protected edu.wpi.first.wpilibj.Joystick source;
    
    public Joystick(int port) {
        source = new edu.wpi.first.wpilibj.Joystick(port);
        
        for(int i=0; i < buttons.length; ++i) {
            final int button = i;
            buttons[i] = new DigitalSource() {
                public boolean get() {
                    return buttonVals[button];
                }
            };
        }
        for(int i=0; i < cmdAxes.length; ++i) {
            final int axis = i+1;
            cmdAxes[i] = new DigitalSource() {
                public boolean get() {
                    return Math.abs(getAxis(axis)) > deadZone;
                }
            };
        }
    }
    
    public double getAxis(int axis) {
        return checkedAccess(axes, axis);
    }
    
    private static double checkedAccess(double[] arr, int index) {
        if (index < 1 || index > arr.length)
            throw new IndexOutOfBoundsException(index + " references an invalid joystick member");
        return arr[index-1];
    }
    
    private static DigitalSource checkedAccess(DigitalSource[] arr, int index) {
        if (index < 1 || index > arr.length)
            throw new IndexOutOfBoundsException(index + " references an invalid joystick member");
        return arr[index-1];
    }
    
    public DigitalSource commandedAxis(int axis) {
        return checkedAccess(cmdAxes, axis);
    }
    
    public DigitalSource button(int button) {
        return checkedAccess(buttons, button);
    }

    protected void update() {
        for(int i=0; i < axes.length; ++i) {
            final int axis = i+1;
            axes[i] = source.getRawAxis(axis);
        }
        for(int i=0; i < buttonVals.length; ++i) {
            final int button = i+1;
            buttonVals[i] = source.getRawButton(button);
        }
    }
}
