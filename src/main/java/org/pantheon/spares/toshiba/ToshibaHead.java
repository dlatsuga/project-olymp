package org.pantheon.spares.toshiba;

import org.pantheon.spares.Head;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class ToshibaHead implements Head {
    @Override
    public void speak() {
        System.out.println("Toshiba head");
    }
}
