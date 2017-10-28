package com.marquee.rodney;

import com.marquee.rodney.ai.Functions;
import com.marquee.rodney.ai.ReplayMemory;
import com.marquee.rodney.ai.Transition;

public class tester {

    public static void main(String[] args) {
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

        ReplayMemory a = new ReplayMemory(125, (int) System.currentTimeMillis());
        for (int i = 0; i < 125; i++)
            a.push(new Transition(i, i, i, i * 0.01));

        System.out.println(Functions.huberLoss(a.sample(3)));
    }
}
