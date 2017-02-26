package authService;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class AuthControllerTest
{
    @Test
    public void validateEmail() throws Exception
    {

    }

    @Test
    public void validatePassword() throws Exception
    {

    }

    @Test
    public void passwordVerifier() throws Exception
    {
        String encodedPass = "$2a$10$opwzWCG0E34qc16Ea0Y99uxU8EnR0GXvbRtF02.ZaC3Wchv8d946q";
        String decodedPass = "cat";

        AuthController authController = new AuthController();

        assertTrue(authController.passwordVerifier(decodedPass,encodedPass));
    }

    @Test
    public void setNewUser() throws Exception
    {

    }

    @Test
    public void encodePassword() throws Exception
    {


    }

}