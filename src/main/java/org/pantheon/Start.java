package org.pantheon;

import org.pantheon.robot.ModelT1000;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("all_context.xml");
        ModelT1000 t1000 = (ModelT1000) context.getBean("modelT1000");
        t1000.action();

        ModelT1000 t1 = (ModelT1000) context.getBean("model1");
        t1.action();
    }
}