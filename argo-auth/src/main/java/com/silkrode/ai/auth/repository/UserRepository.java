package com.silkrode.ai.auth.repository;

import com.silkrode.ai.auth.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Define user repositories which implement by using Mysql.
 */
@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDto findByUsername(String username) {
        String sql = "select * from user where username = ?";
        return jdbcTemplate.queryForObject(sql, new UserDto(), new Object[]{username});
    }
}
