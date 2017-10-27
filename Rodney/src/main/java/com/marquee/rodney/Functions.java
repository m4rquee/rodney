package com.marquee.rodney;

public class Functions {

    public static double huberLoss() {
    }

    public static double LFunction(double δ) {
        return Math.abs(δ) <= 1 ? Math.pow(δ, 2) / 2 : δ - 1 / 2;
    }
}
