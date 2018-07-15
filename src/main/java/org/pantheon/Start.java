package org.pantheon;

import org.pantheon.jdbc.Mp3;
import org.pantheon.jdbc.dao.PostgreSqlDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
    public static void main(String[] args) {
        Mp3 mp3 = new Mp3();
        mp3.setAuthor("MKTO2");
        mp3.setSong("Monaco2");

        ApplicationContext context = new ClassPathXmlApplicationContext("all_context.xml");
        PostgreSqlDao postgreSqlDao = (PostgreSqlDao) context.getBean("postgresqldao");
        postgreSqlDao.insert(mp3);
    }
}