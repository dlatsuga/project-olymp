package org.pantheon;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("all_context.xml");
        RobotConveyor conveyor = (RobotConveyor) context.getBean("t1000Conveyor");
        Robot terminator1 = conveyor.createRobot();
        Robot terminator2 = conveyor.createRobot();
        Robot terminator3 = conveyor.createRobot();

        System.out.println("terminator1 : " + terminator1);
        System.out.println("terminator2 : " + terminator2);
        System.out.println("terminator3 : " + terminator3);

        System.out.println("****************************");

        terminator1.action();
        terminator2.action();
        terminator3.action();
    }
}
