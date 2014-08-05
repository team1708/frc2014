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
public class Encoder implements PIDSource {

    public edu.wpi.first.wpilibj.Encoder encoder;
    public edu.wpi.first.wpilibj.Counter counter;
    
    public Encoder(int channelA, int channelB)
    {
        this(channelA, channelB, 1);
    }
    
    public Encoder(int channelA, int channelB, double distancePerPulse)
    {
        encoder = new edu.wpi.first.wpilibj.Encoder(channelA, channelB);
        encoder.setDistancePerPulse(distancePerPulse);
        encoder.setMaxPeriod(.1);
        encoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
        encoder.start();
    }
    
    public Encoder(int channel)
    {
        this(channel, 1.0);
    }
    
    public Encoder(int channel, double distancePerPulse)
    {
        counter = new edu.wpi.first.wpilibj.Counter(channel);
        counter.setDistancePerPulse(distancePerPulse);
        counter.setMaxPeriod(.1);
        encoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
        counter.start();
    }
    
    public void reset() {
        if (encoder != null)
            encoder.reset();
        if (counter != null)
            counter.reset();
    }
    
    public double pidGet() {
        if (encoder != null)
            return encoder.pidGet();
        if (counter != null)
            return counter.pidGet();
        return 0.0;
    }
    
    public double getVelocity() {
        return encoder.getRate();
    }

    void setReverseDirection(boolean reversed) {
        if (encoder != null)
            encoder.setReverseDirection(reversed);
        if (counter != null)
            counter.setReverseDirection(reversed);
    }
}
