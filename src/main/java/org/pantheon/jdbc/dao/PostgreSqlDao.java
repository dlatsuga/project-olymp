package org.pantheon.jdbc.dao;

import org.pantheon.jdbc.Mp3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

@Component("postgresqldao")
public class PostgreSqlDao implements Mp3Dao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
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
        String sql = "Select * from spring_core_tmp.mp3 Where id = ?";
        return jdbcTemplate.queryForObject(sql, new Mp3RowMapper(), id);
    }

    @Override
    public List<Mp3> getMp3ListBySong(String song) {
        String sql = "Select * from spring_core_tmp.mp3 tt Where tt.song like ?";
        return jdbcTemplate.query(sql, new Mp3RowMapper(), format("%%%s%%", song));
    }

    @Override
    public List<Mp3> getMp3ListByAuthor(String author) {
        String sql = "Select * from spring_core_tmp.mp3 tt Where tt.author like ?";
        return jdbcTemplate.query(sql, new Mp3RowMapper(), format("%%%s%%", author));
    }

    @Override
    public int getMp3Count() {
        String sql = "Select count(*) from spring_core_tmp.mp3";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private static final class Mp3RowMapper implements RowMapper<Mp3> {

        @Override
        public Mp3 mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Mp3 mp3 = new Mp3();
            mp3.setId(resultSet.getInt("id"));
            mp3.setAuthor(resultSet.getString("author"));
            mp3.setSong(resultSet.getString("song"));
            return mp3;
        }
    }
}