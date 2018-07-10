package org.pantheon.spares.toshiba;

import org.pantheon.spares.Leg;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ToshibaLeg implements Leg {
    @Override
    public void go() {
        System.out.println("Toshiba leg");
    }
}
