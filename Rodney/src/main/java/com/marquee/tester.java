package com.marquee;

import com.marquee.ai.*;
import java.util.*;

public class tester {

    public static void main(String[] args) {
        Random rnd = new Random(System.currentTimeMillis());

        ArrayList<Double> a = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            a.add(rnd.nextDouble());

        System.out.println(Arrays.toString(Functions.actionsPR(a, 0.5)));
    }
}
