/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author rcahoon
 */
public abstract class DigitalSource extends Polling {
    private boolean prevState;
    private final Hashtable onStart = new Hashtable();
    private final Hashtable onStop = new Hashtable();
    
    public abstract boolean get();
    
    public boolean starting() {
        return get() && !prevState;
    }
    
    public boolean ending() {
        return !get() && prevState;
    }
    
    protected void update() {
        if (starting()) {
            for(Enumeration cmds = onStart.keys(); cmds.hasMoreElements(); ) {
                ((Command)cmds.nextElement()).start();
            }
        }
        if (ending()) {
            for(Enumeration cmds = onStop.keys(); cmds.hasMoreElements(); ) {
                ((Command)cmds.nextElement()).start();
            }
        }
        prevState = get();
    }
    
    public void onStart(Command command) {
        onStart.put(command, null);
    }
    
    public void onStop(Command command) {
        onStop.put(command, null);
    }
}
