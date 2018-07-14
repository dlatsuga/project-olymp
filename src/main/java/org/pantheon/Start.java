package org.pantheon;

import org.pantheon.filemanager.FileManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("all_context.xml");
        FileManager fileManager = (FileManager) context.getBean("fileManager");
        fileManager.getExtensionCount("C:\\Users\\Dima\\Desktop\\polish");
    }
}