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
public class Sequence extends CommandGroup {
    
    private Command exec = null;
    
    protected void init() {
        exec = head;
    }

    protected boolean run() {
        if (exec == null) {
            return false;
        }
        
        if (exec._start())
        {
            exec.isQueued = true;
        }
        else
        {
            exec.isQueued = false;
            exec = exec.nextLink;
        }
        
        return true;
    }
}
