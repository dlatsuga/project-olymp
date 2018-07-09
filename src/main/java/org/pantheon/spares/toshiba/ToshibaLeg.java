package org.pantheon.spares.toshiba;

import org.pantheon.spares.Leg;

public class ToshibaLeg implements Leg {
    @Override
    public void go() {
        System.out.println("Toshiba leg");
    }
}
