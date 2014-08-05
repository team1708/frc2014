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
public class WaitForSeconds extends Command {

    private final double duration;
    
    public WaitForSeconds(double duration)
    {
        this.duration = duration;
    }
    
    protected boolean requirements() {
        return NO_REQUIREMENTS;
    }

    protected void init() {
    }

    protected boolean run() {
        return timeElapsed() < duration;
    }

    protected void cleanup() {
    }
    
}
