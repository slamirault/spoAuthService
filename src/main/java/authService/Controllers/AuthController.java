package authService.Controllers;

import authService.Models.Action;
import authService.Utility.Messages;
import authService.Utility.PasswordManager;
import domain.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
     * Just a get method to poke the server
     */
    @RequestMapping("/health")
    public Action health()
    {
        return new Action(true, Messages.HEALTH.getName());
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public Action create(@RequestParam String email, @RequestParam String password,
                         HttpServletRequest request, HttpServletResponse response)
    {
        /*
           if either the email address entered or password don't meet
           the standards given of them, don't even call the DB
         */
        if (!validateEmail(email))
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new Action(false,Messages.CREATE_FAIL_EML.getName());
        }

        if (!PasswordManager.validatePassword(password))
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new Action(false,Messages.CREATE_FAIL_PWD.getName());
        }

        boolean userCreated = setNewUser(email,password);

        if (!userCreated)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new Action(false,Messages.CREATE_FAIL.getName());
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_CREATED);
            return new Action(true,Messages.CREATE_SUCCESS.getName());
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
            return new Action(foundUser,Messages.AUTH_FAIL.getName());
        }

        return new Action(foundUser,Messages.AUTH_SUCCESS.getName());
    }


    public boolean findUser(String email, String password)
    {
        UserRepository userRepo = new UserRepository(jdbcTemplate);
        PasswordManager pm = new PasswordManager();

        User user = userRepo.findUserByEmail(email);

        if (user != null)
        {
            return pm.passwordVerifier(password,user.getPassword());
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

    public boolean setNewUser(String email, String pwd)
    {
        UserRepository userRepo = new UserRepository(jdbcTemplate);
        PasswordManager pm = new PasswordManager();

        String encodedPwd = pm.encodePassword(pwd);
        return userRepo.createUser(email,encodedPwd);
    }

}