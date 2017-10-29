package com.marquee.rodney;

import com.marquee.rodney.ai.*;
import java.util.*;

public class tester {

    public static void main(String[] args) {
        Random rnd = new Random(System.currentTimeMillis());

//        TriangleArea triangleArea = new TriangleArea(System.currentTimeMillis(), 0.1, 0, 1000, 1000, 2, 10, 3, 1, 1);
//
//        for (int j = 0; j < 10; j++) {
//            System.out.println("------------------------------------------------");
//            System.out.println("New Data----------------------------------------");
//            System.out.println("------------------------------------------------");
//            
//            triangleArea.generateRandomData();
//
//            for (int i = 0; i < 10; i++) {
//                triangleArea.train();
//            }
//        }
        ArrayList<Double> a = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            a.add(rnd.nextDouble());

        System.out.println(Arrays.toString(Functions.actionsPR(a, 0.5)));
    }
}
