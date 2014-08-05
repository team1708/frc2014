/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

class PID extends Polling
{
    final String name;
    
    public PID(String name, PIDOutput output, PIDSource source)
    {
        this.name = name;
        name = name.replace(' ', '-').replace('=', '-');
        
        if (source == null) {
            throw new NullPointerException("Null PIDSource was given");
        }
        if (output == null) {
            throw new NullPointerException("Null PIDOutput was given");
        }

        m_prefPname = name + ".p";
        m_P = Preferences.getInstance().getDouble(m_prefPname, 0);
        Preferences.getInstance().putDouble(m_prefPname, m_P);
        m_prefIname = name + ".i";
        m_I = Preferences.getInstance().getDouble(m_prefIname, 0);
        Preferences.getInstance().putDouble(m_prefIname, m_I);
        m_prefDname = name + ".d";
        m_D = Preferences.getInstance().getDouble(m_prefDname, 0);
        Preferences.getInstance().putDouble(m_prefDname, m_D);
        m_F = 0;
        
        m_prefThresholdName = name + ".threshold";
        m_threshold = Preferences.getInstance().getDouble(m_prefThresholdName, 0.1 / m_P);
        Preferences.getInstance().putDouble(m_prefThresholdName, m_threshold);

        m_pidInput = source;
        m_pidOutput = output;
        
        // this should happen after the preferences have been initalized
        Preferences.getInstance().addPrefListener(listener);
    }
    
    public void setFeedforward(double f)
    {
        m_F = f;
    }
    
    
    private String m_prefPname;
    private String m_prefIname;
    private String m_prefDname;
    private String m_prefThresholdName;
    
    public static final double kDefaultPeriod = .05;
    private double m_P;			// factor for "proportional" control
    private double m_I;			// factor for "integral" control
    private double m_D;			// factor for "derivative" control
    private double m_F;                 // factor for feedforward term
    private boolean m_enabled = false; 			//is the pid controller enabled
    private double m_prevError = 0.0;	// the prior sensor input (used to compute velocity)
    private double m_totalError = 0.0; //the sum of the errors for use in the integral calc
    private double m_setpoint = 0.0;
    private double m_error = 0.0;
    private double m_result = 0.0;
    private double m_period = kDefaultPeriod;
    private double m_lastRun;
    private double m_threshold;
    PIDSource m_pidInput;
    PIDOutput m_pidOutput;
    
    /**
     * Read the input, calculate the output accordingly, and write to the output.
     * This should only be called by the PIDTask
     * and is created during initialization.
     */
    protected void update() {
        if (Timer.getFPGATimestamp() - m_lastRun < m_period)
            return;
        
        if (!m_enabled)
            return;
        
        double input = m_pidInput.pidGet();

        m_error = m_setpoint - input;

        if (m_I != 0)
        {
            double potentialIGain = (m_totalError + m_error) * m_I;
            if (potentialIGain < 1)
            {
                if (potentialIGain > -1) {
                    m_totalError += m_error;
                }
                else {
                    m_totalError = -1 / m_I;
                }
            }
            else
            {
                m_totalError = 1 / m_I;
            }
        }

        m_result = m_P * m_error + m_I * m_totalError + m_D * (m_error - m_prevError) + m_F;
        m_prevError = m_error;

        if (m_result > 1) {
            m_result = 1;
        } else if (m_result < -1) {
            m_result = -1;
        }
        
        System.out.println("PID: error " + m_error + "  P " + (m_P * m_error) + "  I " + (m_I * m_totalError) + "  D " + (m_D * (m_error - m_prevError)) + "  result " + m_result);
        System.out.println("ontarget: " + onTarget());
        
        m_pidOutput.pidWrite(m_result);
    }

    /**
     * Set the PID Controller gain parameters.
     * Set the proportional, integral, and differential coefficients.
     * @param p Proportional coefficient
     * @param i Integral coefficient
     * @param d Differential coefficient
     */
    public void setPID(double p, double i, double d) {
        m_P = p;
        m_I = i;
        m_D = d;
    }

        /**
     * Set the PID Controller gain parameters.
     * Set the proportional, integral, and differential coefficients.
     * @param p Proportional coefficient
     * @param i Integral coefficient
     * @param d Differential coefficient
     * @param f Feed forward coefficient
     */
    public void setPID(double p, double i, double d, double f) {
        m_P = p;
        m_I = i;
        m_D = d;
        m_F = f;
    }
    
    /**
     * Get the Proportional coefficient
     * @return proportional coefficient
     */
    public double getP() {
        return m_P;
    }

    /**
     * Get the Integral coefficient
     * @return integral coefficient
     */
    public double getI() {
        return m_I;
    }

    /**
     * Get the Differential coefficient
     * @return differential coefficient
     */
    public double getD() {
        return m_D;
    }
    
    /**
     * Get the Feed forward coefficient
     * @return feed forward coefficient
     */
    public double getF() {
        return m_F;
    }

    /**
     * Return the current PID result
     * This is always centered on zero and constrained the the max and min outs
     * @return the latest calculated output
     */
    public double get() {
        return m_result;
    }

    /**
     * Set the setpoint for the PIDController
     * @param setpoint the desired setpoint
     */
    public void setSetpoint(double setpoint) {
        m_setpoint = setpoint;
    }

    /**
     * Returns the current setpoint of the PIDController
     * @return the current setpoint
     */
    public double getSetpoint() {
        return m_setpoint;
    }

    /**
     * Return true if the error is within the percentage of the total input range,
     * determined by setTolerance. This assumes that the maximum and minimum input
     * were set using setInput.
     * @return true if the error is less than the tolerance
     */
    public boolean onTarget() {
       return (Math.abs(m_error) < m_threshold);
    }

    /**
     * Begin running the PIDController
     */
    public void enable() {
        if (!m_enabled)
            m_error = Double.POSITIVE_INFINITY;
        m_enabled = true;
    }

    /**
     * Stop running the PIDController, this sets the output to zero before stopping.
     */
    public void disable() {
        if (m_enabled) {
            m_pidOutput.pidWrite(0);
        }
        m_enabled = false;
    }

    /**
     * Return true if PIDController is enabled.
     */
    public boolean isEnable() {
        return m_enabled;
    }

    /**
     * Reset the previous error,, the integral term, and disable the controller.
     */
    public void reset() {
        disable();
        m_prevError = 0;
        m_totalError = 0;
        m_result = 0;
    }
    
    private final ITableListener listener = new ITableListener() {
        public void valueChanged(ITable table, String key, Object value, boolean isNew) {
            try
            {
            if (m_prefPname.equals(key)) {
                m_P = Double.parseDouble((String)value);
                System.out.println("P updated: " + m_P);
            }
            if (m_prefIname.equals(key)) {
                m_I = Double.parseDouble((String)value);
                System.out.println("I updated: " + m_I);
            }
            if (m_prefDname.equals(key)) {
                m_D = Double.parseDouble((String)value);
                System.out.println("D updated: " + m_D);
            }
            if (m_prefThresholdName.equals(key)) {
                m_threshold = Double.parseDouble((String)value);
                System.out.println("threshold updated: " + m_threshold);
            }
            }
            catch(Exception ex)
            {
                System.out.println("Exception caught for table: " + ex);
            }
        }
    };
}