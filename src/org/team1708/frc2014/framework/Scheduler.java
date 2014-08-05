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
public class Scheduler {
    private static Command head = null;
    private static Command exec = null;
    private static Polling headP = null;
    
    public static void add(Polling subsystem)
    {
        subsystem.nextPolling = headP;
        headP = subsystem;
    }
    
    public static void add(Command task)
    {
    	if (task.parentCommand != null)
            throw new IllegalStateException("Scheduler should not manage a parented command");
    	if (task.nextLink != null || task.prevLink != null || task.isQueued || head == task)
            throw new IllegalStateException("Task is already queued");
    	
        if (head != null)
            head.prevLink = task;
        task.nextLink = head;
        task.prevLink = null;
        head = task;
        
        task.isQueued = true;
    }
    
    public static void clear()
    {
        exec = null;
        head = null;
    }
    
    public static void remove(Command task)
    {
        if (exec == task)
            exec = exec.nextLink;
        
        if (head == task)
            head = task.nextLink;
        else if (task.prevLink != null)
            task.prevLink.nextLink = task.nextLink;
        if (task.nextLink != null)
            task.nextLink.prevLink = task.prevLink;
        
        task.nextLink = null;
        task.prevLink = null;
        
        task.isQueued = false;
    }
    
    public static void run()
    {
        exec = head;
        while(exec != null)
        {
            Command cur = exec;
            exec = exec.nextLink;
            cur.start();
        }
        
        Polling current = headP;
        while(current != null)
        {
            current.update();
            current = current.nextPolling;
        }
    }
}
