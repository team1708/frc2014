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
public class Compressor extends edu.wpi.first.wpilibj.Compressor {
    public Compressor(int pressureSwitchChannel, int compressorRelayChannel) {
        super(pressureSwitchChannel, compressorRelayChannel);
        
        super.start();
    }
}
