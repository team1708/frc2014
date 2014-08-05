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
public abstract class Polling {
    Polling nextPolling = null;
    
    public Polling()
    {
        Scheduler.add(this);
    }
    
    protected abstract void update();
}
