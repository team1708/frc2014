/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author team1708
 */
public class WaitForAnalog extends Command {
    
    private final AnalogChannel source;
    private final double threshold;
    private final Comparison comparison;
    private final double timeout;
    
    public WaitForAnalog(AnalogChannel source, Comparison comparison, double threshold)
    {
        this(source, comparison, threshold, Double.POSITIVE_INFINITY);
    }
    
    public WaitForAnalog(AnalogChannel source, Comparison comparison, double threshold, double timeout)
    {
        this.source = source;
        this.comparison = comparison;
        this.threshold = threshold;
        this.timeout = timeout;
    }
    
    protected boolean requirements() {
        return NO_REQUIREMENTS;
    }

    protected void init() {
    }

    protected boolean run() {
        System.out.println("Wait analog " + source.getAverageVoltage() + " <> " + threshold);
        return !comparison.compare(source.getAverageVoltage(), threshold) && (timeElapsed() < timeout);
    }

    protected void cleanup() {
    }
}
