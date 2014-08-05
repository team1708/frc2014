/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team1708.frc2014;

import org.team1708.frc2014.framework.Command;
import org.team1708.frc2014.framework.Sequence;
import org.team1708.frc2014.subsystems.Drivetrain;

/**
 *
 * @author Team1708
 */
public class Test {
    public static Command testTurn()
    {
        Sequence turn = new Sequence();
        turn.add(Drivetrain.Gyro(90));
        return turn;
    }
}
