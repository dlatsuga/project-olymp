package org.pantheon.toshiba;

import org.pantheon.Hand;

public class ToshibaHand implements Hand {
    @Override
    public void catchSomething() {
        System.out.println("Toshiba hand");
    }
}
