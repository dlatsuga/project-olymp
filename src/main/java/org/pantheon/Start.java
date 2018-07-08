package org.pantheon;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ModelT1000 t1000 = (ModelT1000) context.getBean("t1000");
        t1000.action();
        ModelT1000 t1000Empty = (ModelT1000) context.getBean("t1000Empty");
        t1000Empty.action();
    }
}
