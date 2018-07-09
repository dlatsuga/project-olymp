package org.pantheon.spares.sony;

import org.pantheon.spares.Hand;

public class SonyHand implements Hand {
    @Override
    public void catchSomething() {
        System.out.println("Sony hand");
    }
}
