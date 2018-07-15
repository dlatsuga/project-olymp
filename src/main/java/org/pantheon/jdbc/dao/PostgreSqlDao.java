package org.pantheon.jdbc.dao;

import org.pantheon.jdbc.Mp3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component("postgresqldao")
public class PostgreSqlDao implements Mp3Dao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insert(Mp3 mp3) {
        String sql = "Insert into spring_core_tmp.mp3 (author, song) Values (?, ?)";
        mp3.setId(jdbcTemplate.update(sql, mp3.getAuthor(), mp3.getSong()));
    }

    @Override
    public void insert(List<Mp3> mp3List) {
        for (Mp3 mp3 : mp3List) {
            insert(mp3);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "Delete from spring_core_tmp.mp3 Where id = ?";
        int result = jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(Mp3 mp3) {
        delete(mp3.getId());
    }

    @Override
    public Mp3 getMp3ById(int id) {
        return null;
    }

    @Override
    public List<Mp3> getMp3ListBySong(String song) {
        return null;
    }

    @Override
    public List<Mp3> getMp3ListByAuthor(String author) {
        return null;
    }
}
