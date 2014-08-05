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
public class Parallel extends CommandGroup {

    protected void init() {
        Command current = head;
        while(current != null)
        {
        	current.isQueued = current._start();
            current = current.nextLink;
        }
    }

    protected boolean run() {
        boolean cont = false;
        
        Command current = head;
        while(current != null)
        {
            if (current.isQueued && current._start())
                cont = true;
            else
        		current.isQueued = false;
            
            current = current.nextLink;
        }
        
        return cont;
    }
}
