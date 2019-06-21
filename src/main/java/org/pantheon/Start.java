package org.pantheon;

import org.pantheon.jdbc.dao.PostgreSqlDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("all_context.xml");
        PostgreSqlDao postgreSqlDao = (PostgreSqlDao) context.getBean("postgresqldao");
        System.out.println(postgreSqlDao.getMp3ById(1));
    }
}