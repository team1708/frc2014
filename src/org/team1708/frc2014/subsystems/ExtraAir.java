package org.team1708.frc2014.subsystems;
import org.team1708.frc2014.framework.Compressor;
public class ExtraAir{

    private static Compressor compressor = new Compressor (6,1);
    
    public static void init() {    
    compressor.start();
    }  
    
}

