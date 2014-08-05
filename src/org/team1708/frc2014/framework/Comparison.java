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
public abstract class Comparison {
    public static final Comparison LESS = new Comparison() {
        public boolean compare(double a, double b) {
            return a < b;
        }
        public boolean compare(int a, int b) {
            return a < b;
        }
    };
    public static final Comparison LESS_EQ = new Comparison() {
        public boolean compare(double a, double b) {
            return a <= b;
        }
        public boolean compare(int a, int b) {
            return a <= b;
        }
    };
    public static final Comparison EQUAL = new Comparison() {
        public boolean compare(double a, double b) {
            return a == b;
        }
        public boolean compare(int a, int b) {
            return a == b;
        }
    };
    public static final Comparison NOT_EQUAL = new Comparison() {
        public boolean compare(double a, double b) {
            return a != b;
        }
        public boolean compare(int a, int b) {
            return a != b;
        }
    };
    public static final Comparison GREATER = new Comparison() {
        public boolean compare(double a, double b) {
            return a > b;
        }
        public boolean compare(int a, int b) {
            return a > b;
        }
    };
    public static final Comparison GREATER_EQ = new Comparison() {
        public boolean compare(double a, double b) {
            return a >= b;
        }
        public boolean compare(int a, int b) {
            return a >= b;
        }
    };
    
    public abstract boolean compare(double a, double b);
    public abstract boolean compare(int a, int b);
}
