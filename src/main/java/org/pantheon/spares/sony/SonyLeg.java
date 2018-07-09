package org.pantheon.spares.sony;

import org.pantheon.spares.Leg;

public class SonyLeg implements Leg {
    @Override
    public void go() {
        System.out.println("Sony leg");
    }
}
