package org.pantheon;

import org.springframework.stereotype.Component;

@Component
public class MyLoggerOld {

    public void printValue(Object obj) {
        System.out.println(obj);
    }

    public void init() {
        System.out.println("init");
    }

    public void close() {
        System.out.println("close");
    }
}
