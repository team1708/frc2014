/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.framework;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author rcahoon
 */
public class RobotDrive extends Subsystem {
    final String name;
    private final SpeedController leftMotor, rightMotor;
    private final PID tc;
    private final Encoder leftEncoder, rightEncoder;
    private final Gyro gyro;
    private boolean leftReversed = false, rightReversed = false;
    
    private double driveCommand, turnCommand;
    
    public RobotDrive(SpeedController left, SpeedController right)
    {
        this(left, right, null, null, null);
    }
    
    public RobotDrive(final SpeedController left, final SpeedController right, final Encoder leftEncoder, final Encoder rightEncoder, final Gyro gyro)
    {
        this.name = "Drive";
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.gyro = gyro;
        this.leftMotor = left;
        this.rightMotor = right;
        
        if (/*(leftEncoder != null && rightEncoder != null) ||*/ gyro != null)
        {
            tc = new PID("Turn Controller", new PIDOutput() {
                public void pidWrite(double d) {
                    turnCommand = d;
                    updateMotorCommand();
                }
            }, new PIDSource() {
                public double pidGet() {
                    return gyro.pidGet();
                }
            });
            tc.disable();
        }
        else
        {
            tc = null;
        }
    }
    
    public void setLeftReversed(boolean reversed)
    {
        this.leftReversed = true;
        if (leftEncoder != null)
            leftEncoder.setReverseDirection(reversed);
    }
    
    public void setRightReversed(boolean reversed)
    {
        this.rightReversed = reversed;
        if (rightEncoder != null)
            rightEncoder.setReverseDirection(reversed);
    }
    
    public double getSpeed()
    {
        return Math.max(Math.abs(leftEncoder.getVelocity()),  Math.abs(rightEncoder.getVelocity()));
    }
    
    private class Drive extends Command
    {
        private static final double OUTPUT_RAMPUP_TIME = 1.5;
        private static final double OUTPUT_RAMPDOWN_DISTANCE = 4.0;
        private static final double OUTPUT_THRESHOLD_DISTANCE = 1.5/12.0;
        
        private final double setpoint;
        
        public Drive(double setpoint)
        {
            if (leftEncoder == null && rightEncoder == null)
                throw new IllegalStateException("Cannot regulate " + RobotDrive.this.name + " because it has no sensor attached");
            
            this.setpoint = setpoint;
        }

        protected boolean requirements() {
            return requires(RobotDrive.this);
        }
        
        private int state;
        
        protected void init() {
            state = 0;
            driveCommand = 0;
            turnCommand = 0;
            if (!tc.isEnable()) {
                gyro.reset();
                tc.setSetpoint(0);
            }
            if (leftEncoder != null)
                leftEncoder.reset();
            if (rightEncoder != null)
                rightEncoder.reset();
            tc.enable();
        }

        double getMeasure()
        {
            double measure = 0;
            if (leftEncoder != null)
            {
                double tmeasure = leftEncoder.pidGet();
                if (Math.abs(measure) < Math.abs(tmeasure))
                    measure = tmeasure;
            }
            if (rightEncoder != null)
            {
                double tmeasure = rightEncoder.pidGet();
                if (Math.abs(measure) < Math.abs(tmeasure))
                    measure = tmeasure;
            }
            System.out.println("Encoders left: " + leftEncoder.pidGet() + " right: " + rightEncoder.pidGet());
            return measure;
        }
        
        protected boolean run() {
            double measure = getMeasure();
            System.out.println("Running Drive " + state + " measure " + measure);
            switch(state)
            {
                case 0:
                    // ramp up
                    double time = timeElapsed();
                    driveCommand = (setpoint / Math.abs(setpoint)) * time / OUTPUT_RAMPUP_TIME;
                    if (time > OUTPUT_RAMPUP_TIME)
                        state = 1;
                    break;
                case 1:
                    // steady
                    driveCommand = (setpoint / Math.abs(setpoint)) * 1;
                    if (Math.abs(setpoint - measure) < OUTPUT_RAMPDOWN_DISTANCE)
                        state = 2;
                    break;
                case 2:
                    driveCommand = (setpoint - measure) / OUTPUT_RAMPDOWN_DISTANCE * 0.8 + 0.2;
                    break;
            }
            
            return (Math.abs(setpoint - measure) > OUTPUT_THRESHOLD_DISTANCE);
        }

        protected void cleanup() {
            driveCommand = 0;
        }
    }
    
    private class Turn extends Command
    {
        private final double setpoint;
        
        public Turn(double setpoint)
        {
            if (tc == null)
                throw new IllegalStateException("Cannot regulate " + RobotDrive.this.name + " because it has no sensor attached");
            
            this.setpoint = setpoint;
        }

        protected boolean requirements() {
            return requires(RobotDrive.this);
        }
        
        protected void init() {
            driveCommand = 0;
            turnCommand = 0;
            tc.setSetpoint(setpoint);
            if (leftEncoder != null)
                leftEncoder.reset();
            if (rightEncoder != null)
                rightEncoder.reset();
            gyro.reset();
            tc.reset();
            tc.enable();
        }

        protected boolean run() {
            System.out.println("Gyro turn: " + gyro.pidGet() + "  target: " + setpoint);
            System.out.println("ontarget: " + tc.onTarget());
            return !tc.onTarget();
        }

        protected void cleanup() {
        }
    }
    
    private class SetArcadeDrive extends Command
    {
        private final double moveCommand, rotateCommand;
        
        public SetArcadeDrive(double move, double rotate)
        {
            this.moveCommand = move;
            this.rotateCommand = rotate;
        }

        protected boolean requirements() {
            return requires(RobotDrive.this);
        }
        
        protected void init() {
        }

        protected boolean run() {
            setArcadeDrive(moveCommand, rotateCommand);
            
            return false;
        }
        
        protected void cleanup() {
        }
    }

    private class SetTankDrive extends Command
    {
        private final double leftCommand, rightCommand;
        
        public SetTankDrive(double left, double right)
        {
            this.leftCommand = left;
            this.rightCommand = right;
        }

        protected boolean requirements() {
            return requires(RobotDrive.this);
        }
        
        protected void init() {
        }

        protected boolean run() {
            setTankDrive(leftCommand, rightCommand);
            
            return false;
        }
        
        protected void cleanup() {
        }
    }
    
    public Command RegulateDrive(double setpoint)
    {
        return new Drive(setpoint);
    }
    
    public Command RegulateTurn(double setpoint)
    {
        return new Turn(setpoint);
    }
    
    public Command SetTankDrive(double left, double right)
    {
        return new SetTankDrive(left, right);
    }
    
    public void setTankDrive(double left, double right)
    {
        if (tc != null)
        {
            tc.disable();
        }
        _setTankDrive(left, right);
    }
    
    private void _setTankDrive(double left, double right)
    {
        leftMotor.set(leftReversed ? -left : left);
        rightMotor.set(rightReversed ? -right : right);
        //TODO: update table
    }
    
    static double limit(double num) {
        if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
    }
    
    public Command SetArcadeDrive(double moveValue, double rotateValue)
    {
        return new SetArcadeDrive(moveValue, rotateValue);
    }
    
    public void setArcadeDrive(double moveValue, double rotateValue)
    {
        if (tc != null)
        {
            tc.disable();
        }
        _setArcadeDrive(moveValue, rotateValue);
    }
    
    private void _setArcadeDrive(double moveValue, double rotateValue)
    {
        double leftMotorSpeed;
        double rightMotorSpeed;
        
        moveValue = limit(moveValue);
        rotateValue = limit(-rotateValue);

        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        
        _setTankDrive(leftMotorSpeed, rightMotorSpeed);
    }
    
    protected void updateMotorCommand()
    {
        _setArcadeDrive(driveCommand, turnCommand);
    }
}
