package authService;

import java.util.List;
import java.lang.Object;


import org.apache.commons.validator.routines.EmailValidator;
import domain.User;
import domain.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Just a test method to test the server
     * @return
     */
    @RequestMapping("/test")
    public Action test()
    {
        return new Action(true, "Hello, world!");
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public Action create(@RequestParam String email, @RequestParam String password,
                         HttpServletRequest request, HttpServletResponse response)
    {
        if (!validateEmail(email))
        {
            /*
               if either the email address entered or password don't meet
               the standards given of them, don't even call the DB
             */
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new Action(false,"Invalid email");
        }
        else {
            if (!validatePassword(password))
            {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new Action(false,"Invalid password");
            }
        }

        boolean userCreated = setNewUser(email,password);

        if (!userCreated)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new Action(false,"User was not created");
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return new Action(true,"Created");
        }
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public Action auth(@RequestParam String email, @RequestParam String password,
                           HttpServletRequest request, HttpServletResponse response)
    {
        boolean foundUser = findUser(email, password);

        if (!foundUser)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new Action(foundUser,"Authentication failed; Invalid email or password");
        }

        return new Action(foundUser,"Authentication success!");
    }


    public boolean findUser(String email, String password)
    {
        String sql = "SELECT email, pass FROM user_data WHERE email = ?";

        List<User> users = jdbcTemplate.query(sql, new Object[] { email }, new UserRowMapper());


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
        .{8,128} at least 8 characters
         */
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return pwd.matches(pattern);
    }

    public boolean passwordVerifier(String inputPwd, String dbPwd)
    {
        return bCryptPasswordEncoder.matches(inputPwd, dbPwd);
    }



    public boolean setNewUser(String email, String pwd)
    {
        String sql = "INSERT INTO user_data (id, email,pass) VALUES (DEFAULT,?,?);";

        String encodedPwd = encodePassword(pwd);
        int rowsUpdated = jdbcTemplate.update(sql,new Object[]{email,encodedPwd});
        return rowsUpdated == 1;
    }


    public String encodePassword(String pwd)
    {
        return bCryptPasswordEncoder.encode(pwd);
    }
}