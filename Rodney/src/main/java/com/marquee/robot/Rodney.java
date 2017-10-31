package com.marquee.robot;

import robocode.*;

public class Rodney extends AdvancedRobot {

    @Override
    public void run() {
        while (true)
            out.println(getEnergy());
    }
}
