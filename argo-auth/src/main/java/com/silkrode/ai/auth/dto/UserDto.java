package com.silkrode.ai.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Define user data format used to authenticate.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements RowMapper<UserDto> {

    Integer id;

    String username;

    String password;

    String authorities;

    @Override
    public UserDto mapRow(ResultSet resultSet, int i) throws SQLException {
        UserDto userDto = new UserDto();
        userDto.setId(resultSet.getInt("id"));
        userDto.setUsername(resultSet.getString("username"));
        userDto.setPassword(resultSet.getString("password"));
        userDto.setAuthorities(resultSet.getString("authorities"));
        return userDto;
    }
}
