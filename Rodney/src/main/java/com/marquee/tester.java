package com.marquee;

import com.marquee.ai.nn.*;
import jama.*;

public class tester {

    public static void main(String[] args) {
        NNetwork test = new NNetwork(4, 4, 3, 2);

        test.foward(Matrix.random(1, 4)).print(1, 4);
    }
}
