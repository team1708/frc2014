package org.team1708.frc2014.framework;

abstract class CommandGroup extends Command {

    protected Command head = null;
    protected Command tail = null;
    
    public void add(Command command)
    {
    	if (command.parentCommand != null)
    		throw new IllegalStateException("Task already has a parent command");
    	if (command.nextLink != null || command.prevLink != null || command.isQueued)
    		throw new IllegalStateException("Task is already queued");
    	if (getRoot(this) == command)
    		throw new IllegalStateException("Adding this task would create a parent loop");
    	
        command.setParent(this);
        if (tail != null)
            tail.nextLink = command;
        tail = command;
        if (head == null)
            head = command;
    }
    
    protected boolean requirements() {
        return true;
    }

    protected void cleanup() {
    }

}
