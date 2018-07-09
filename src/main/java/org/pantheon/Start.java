package org.pantheon;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("all_context.xml");
        T1000Pool pool = (T1000Pool) context.getBean("t1000Pool");
        pool.action();
    }
}
