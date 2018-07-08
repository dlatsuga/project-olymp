package org.pantheon;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("all_context.xml");
        ModelT1000 t1000_1 = (ModelT1000) context.getBean("t1000");
        t1000_1.action();
        System.out.println("********");

        ModelT1000 t1000Empty_1 = (ModelT1000) context.getBean("t1000Empty");
        ModelT1000 t1000Empty_2 = (ModelT1000) context.getBean("t1000Empty");
        t1000Empty_1.action();

        System.out.println("********* " + (t1000Empty_1 == t1000Empty_2));
        System.out.println(t1000Empty_1);
        System.out.println(t1000Empty_2);
    }
}
