package authService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.lang.Object;


import org.apache.commons.validator.routines.EmailValidator;
import domain.User;
import domain.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
public class AuthController
{
    @Autowired
    JdbcTemplate jdbcTemplate;


    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private static final String template = "Hello, %s!";
    private static final String postTemplate = "Authentication is %s";

    @RequestMapping("/auth")
    public Auth auth(@RequestParam(value = "name", defaultValue = "World") String name)
    {
        return new Auth(true, String.format(template, name));
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public Auth create(@RequestParam String email, @RequestParam String password)
    {
        setNewUser(email,password);

        return new Auth(true,"Created");
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public Auth authPost(@RequestParam String email, @RequestParam String password)
    {
        boolean foundUser = findUser(email, password);
        return new Auth(foundUser,"Authenticating...");
    }


    public boolean findUser(String email, String password)
    {

        String sql = "SELECT * FROM user_data WHERE email = '" + email + "'";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());


        if (users != null && users.size() != 0)
        {
            User user = users.get(0);
            return passwordVerifier(password,user.getPassword());

        }
        else
        {
            return false;
        }
    }

    public static boolean validateEmail(String email)
    {
        return EmailValidator.getInstance().isValid(email);
    }

    public boolean validatePassword(String pwd)
    {
        /*
        (?=.*[0-9]) a digit must occur at least once
        (?=.*[a-z]) a lower case letter must occur at least once
        (?=.*[A-Z]) an upper case letter must occur at least once
        (?=.*[@#$%^&+=]) a special character must occur at least once
        (?=\\S+$) no whitespace allowed in the entire string
        .{8,128} at least 8 characters but no more than 128
         */
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,128}";
        return pwd.matches(pattern);
    }

    public boolean passwordVerifier(String inputPwd, String dbPwd)
    {
        return bCryptPasswordEncoder.matches(inputPwd, dbPwd);
    }



    public void setNewUser(String email, String pwd)
    {
        String sql = "INSERT INTO user_data VALUES (null,'%s','%s');";
        jdbcTemplate.update(String.format(sql,email,encodePassword(pwd)));
    }


    public String encodePassword(String pwd)
    {
        return bCryptPasswordEncoder.encode(pwd);
    }
}