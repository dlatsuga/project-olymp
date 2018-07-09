package org.pantheon.spares.toshiba;

import org.pantheon.spares.Hand;

public class ToshibaHand implements Hand {
    @Override
    public void catchSomething() {
        System.out.println("Toshiba hand");
    }
}
