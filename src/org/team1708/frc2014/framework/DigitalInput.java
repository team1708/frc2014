/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

/**
 *
 * @author rcahoon
 */
public class DigitalInput extends DigitalSource {

    protected edu.wpi.first.wpilibj.DigitalInput source;
    
    public DigitalInput(int port) {
        source = new edu.wpi.first.wpilibj.DigitalInput(port);
    }
    
    public boolean get() {
        return source.get();
    }
}
