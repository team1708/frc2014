/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author rcahoon
 */
public class Gyro implements PIDSource {
    
    public edu.wpi.first.wpilibj.Gyro source;
    
    public Gyro(int channel)
    {
        this(channel, 1);
    }
    
    public Gyro(int channel, double sensitivity)
    {
        source = new edu.wpi.first.wpilibj.Gyro(channel);
        source.setSensitivity(sensitivity);
    }
    
    public void reset() {
        source.reset();
    }
    
    public double pidGet() {
        return source.pidGet();
    }
}
