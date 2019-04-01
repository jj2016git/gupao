package com.example.templatemethod;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    private MyJdbcTemplate myJdbcTemplate;

    public UserDao(MyJdbcTemplate myJdbcTemplate) {
        this.myJdbcTemplate = myJdbcTemplate;
    }

    public List<User> queryUsers() {
        return myJdbcTemplate.query("select * from t_user", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                return user;
            }
        });
    }
}
