/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author rcahoon
 */
public class MultipleMotors implements SpeedController {

    private final SpeedController[] devices;
    
    public MultipleMotors(SpeedController device1)
    {
        devices = new SpeedController[] { device1 };
    }
    public MultipleMotors(SpeedController device1, SpeedController device2)
    {
        devices = new SpeedController[] { device1, device2 };
    }
    public MultipleMotors(SpeedController device1, SpeedController device2, SpeedController device3)
    {
        devices = new SpeedController[] { device1, device2, device3 };
    }
    
    public double get() {
        if (devices.length == 0)
            return 0;
        else
            return devices[0].get();
    }

    public void set(double d, byte b) {
        for(int i=0; i < devices.length; ++i)
            devices[i].set(d, b);
    }

    public void set(double d) {
        for(int i=0; i < devices.length; ++i)
            devices[i].set(d);
    }

    public void disable() {
        for(int i=0; i < devices.length; ++i)
            devices[i].disable();
    }

    public void pidWrite(double d) {
        for(int i=0; i < devices.length; ++i)
            devices[i].pidWrite(d);
    }
    
}
