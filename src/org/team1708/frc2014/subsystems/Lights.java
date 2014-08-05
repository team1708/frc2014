/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPIDevice;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Utility;
import org.team1708.frc2014.framework.Polling;

/**
 *
 * @author rcahoon
 */
public class Lights extends Polling {
    private static Lights instance;
    private final SPILights device;

    private static final int NUM_LEDS = 77;
    
    SPILights.RGB[] leds = new SPILights.RGB[NUM_LEDS];
    
    public static void init() {
        instance = new Lights();
    }
    
    public Lights() {
        System.out.println("Lights init");
        
        device = new SPILights();
        
        for(int i=0; i < leds.length; ++i)
        {
            leds[i] = new SPILights.RGB();
        }
    }
    
    private double lastTime;
    //int position = 0;
    
    protected void update() {
        
        if (Timer.getFPGATimestamp() < lastTime + 0.05)
            return;
        
        lastTime = Timer.getFPGATimestamp();
        
        /*++position;
        position %= 3;
        int p = position;
        
        for(int led_number = 0; led_number < NUM_LEDS; led_number++)
        {
            leds[led_number].R = (p == 0) ? 255 : 0; ++p; p %= 3;
            leds[led_number].G = (p == 0) ? 255 : 0; ++p; p %= 3;
            leds[led_number].B = (p == 0) ? 255 : 0; ++p; p %= 3;
            ++p; p %= 3;
        }*/
        
        double position = (Timer.getFPGATimestamp() / 3) % NUM_LEDS;
        
        if (DriverStation.getInstance().isDisabled())
        {
            for(int led_number = 0; led_number < NUM_LEDS; led_number++)
            {
                if ((led_number < NUM_LEDS / 2) ^ (Timer.getFPGATimestamp() % 1 < 0.5))
                {
                    leds[led_number].R = 128;
                    leds[led_number].G = 128;
                    leds[led_number].B = 0;
                }
                else
                {
                    leds[led_number].R = 0;
                    leds[led_number].G = 0;
                    leds[led_number].B = 0;
                }
            }
        }
        else
        {
            for(int led_number = 0; led_number < NUM_LEDS; led_number++)
            {
                int intensity = (int)Math.max(255 / (1 + Math.abs(position - led_number)), 255 / (1 + Math.abs((position - led_number + NUM_LEDS) % NUM_LEDS)));

                if (!DriverStation.getInstance().isOperatorControl())
                {
                    leds[led_number].R = 0;
                    leds[led_number].G = intensity;
                    leds[led_number].B = 0;
                }
                else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.kRed)
                {
                    leds[led_number].R = intensity / 2;
                    leds[led_number].G = 0;
                    leds[led_number].B = 0;
                }
                else
                {
                    leds[led_number].R = 0;
                    leds[led_number].G = 0;
                    leds[led_number].B = intensity / 2;
                }
            }
        }
        
        device.show(leds);
    }
}

class SPILights
{
    private final SPIDevice spi;
    private static final int CLOCK_SPEED = 75000;
    
    public static class RGB
    {
        public int R;
        public int G;
        public int B;
    }
    
    public SPILights()
    {
        spi = new SPIDevice(new DigitalOutput(7), new DigitalOutput(8), null, new DigitalOutput(9));
        spi.setBitOrder(SPIDevice.BIT_ORDER_MSB_FIRST);
        spi.setClockRate(CLOCK_SPEED);
        
        System.out.println("Max SPI clock " + spi.MAX_CLOCK_FREQUENCY);
        
        nextTime = Utility.getFPGATime();
    }
    
    private long nextTime;

    public void show(RGB[] colors) {
        while (Utility.getFPGATime() < nextTime)
        {}
        
        long[] data = new long[colors.length];
        int[] numbits = new int[colors.length];
        for(int i=0; i < colors.length; ++i)
        {
            data[i] = (colors[i].R << 16) | (colors[i].G << 8) | colors[i].B;
            numbits[i] = 24;
        }
        
        spi.transfer(data, numbits);
        
        nextTime = Utility.getFPGATime() + 1000 + 24 * colors.length * 1000000L / CLOCK_SPEED;
    }
    
    public void free(){
        spi.free();
    }
    
}
