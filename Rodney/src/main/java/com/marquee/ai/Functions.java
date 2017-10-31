package com.marquee.ai;

import java.util.*;
import java.util.stream.*;

public class Functions {
    
    public static double huberLoss(ArrayList<Transition> batch) {
        return batch.stream()
                .mapToDouble((Transition t) -> LFunction(t.getReward()))
                .sum() / batch.size();
    }
    
    public static double LFunction(double δ) {
        return Math.abs(δ) <= 1 ? Math.pow(δ, 2) / 2 : δ - 1.0 / 2;
    }
    
    public static double[] actionsPR(ArrayList<Double> qValues, double τ) {
        double sum = qValues.stream()
                .mapToDouble(q -> Math.exp(q / τ))
                .sum();
        
        return IntStream
                .iterate(0, i -> ++i)
                .limit(qValues.size())
                .mapToDouble(i -> Math.exp(qValues.get(i) / τ) / sum)
                .toArray();
    }
}
