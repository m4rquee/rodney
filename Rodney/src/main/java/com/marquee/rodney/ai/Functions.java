package com.marquee.rodney.ai;

import java.util.*;

public class Functions {

    public static double huberLoss(ArrayList<Transition> batch) {
        double[] s = batch.stream()
                .mapToDouble((Transition t) -> LFunction(t.getReward())).toArray();
        double sum = 1;

        return sum / batch.size();
    }

    public static double LFunction(double δ) {
        return Math.abs(δ) <= 1 ? Math.pow(δ, 2) / 2 : δ - 1.0 / 2;
    }
}
