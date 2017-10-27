package com.marquee.rodney.ai;

import java.util.*;

public class Functions {

    public static double huberLoss(ArrayList<Transition> batch) {
        return batch.stream()
                .mapToDouble((Transition t) -> LFunction(t.getReward()))
                .sum() / batch.size();
    }

    public static double LFunction(double δ) {
        return Math.abs(δ) <= 1 ? Math.pow(δ, 2) / 2 : δ - 1 / 2;
    }
}
