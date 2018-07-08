package org.pantheon.sony;

import org.pantheon.Hand;

public class SonyHand implements Hand {
    @Override
    public void catchSomething() {
        System.out.println("Sony hand");
    }
}
