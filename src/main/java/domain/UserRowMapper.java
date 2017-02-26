package domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper
{
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        User user = new User();
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("pass"));
        return user;
    }

}