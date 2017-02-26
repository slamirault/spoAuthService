package authService.Utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Stevie on 2/26/2017.
 */
public class PasswordManager
{
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String encodePassword(String pwd)
    {
        return bCryptPasswordEncoder.encode(pwd);
    }

    public boolean passwordVerifier(String inputPwd, String dbPwd)
    {
        return bCryptPasswordEncoder.matches(inputPwd, dbPwd);
    }

    public static boolean validatePassword(String pwd)
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
}
