package org.pantheon.spares.toshiba;

import org.pantheon.spares.Hand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ToshibaHand implements Hand {
    @Override
    public void catchSomething() {
        System.out.println("Toshiba hand");
    }
}
