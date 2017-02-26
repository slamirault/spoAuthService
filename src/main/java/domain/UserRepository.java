package domain;

import domain.Interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository
{
    JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findUserByEmail(String email)
    {
        String sql = "SELECT email, pass FROM user_data WHERE email = ?";
        List<User> users = new ArrayList<User>();

        try
        {
            users = jdbcTemplate.query(sql, new Object[] { email }, new UserRowMapper());
        }
        catch (Exception e)
        {
            //TODO: Add logging
        }

        if (users != null && users.size() != 0)
        {
            return users.get(0);
        }

        return null;
    }

    @Override
    public boolean createUser(String email, String encodedPwd)
    {
        String sql = "INSERT INTO user_data (id, email,pass) VALUES (DEFAULT,?,?);";
        int rowsUpdated = 0;

        try
        {
            rowsUpdated = jdbcTemplate.update(sql,new Object[]{email,encodedPwd});
        }
        catch (Exception e)
        {
            //TODO: Add logging
        }

        return rowsUpdated == 1;
    }
}
