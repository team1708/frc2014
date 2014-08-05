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
public class PrintCommand extends Command {
    
    private final String message;
    
    public PrintCommand(String message) {
        this.message = message;
    }

    protected boolean requirements() {
        return NO_REQUIREMENTS;
    }

    protected void init() {
    }

    protected boolean run() {
        System.out.println(message);
        
        return false;
    }

    protected void cleanup() {
    }
}
