/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team1708.frc2014.framework;

/**
 *
 * @author team1708
 */
public class WaitForDigital extends Command {
    
    private final DigitalSource source;
    private final boolean invert;
    private final double timeout;
    
    public WaitForDigital(DigitalSource source)
    {
        this(source, false);
    }
    
    public WaitForDigital(DigitalSource source, double timeout)
    {
        this(source, false, timeout);
    }
    
    public WaitForDigital(DigitalSource source, boolean invert)
    {
        this(source, invert, Double.POSITIVE_INFINITY);
    }
    
    public WaitForDigital(DigitalSource source, boolean invert, double timeout)
    {
        this.source = source;
        this.invert = invert;
        this.timeout = timeout;
    }
    
    protected boolean requirements() {
        return NO_REQUIREMENTS;
    }

    protected void init() {
    }

    protected boolean run() {
        return !(invert ^ source.get()) && (timeElapsed() < timeout);
    }

    protected void cleanup() {
    }
}
