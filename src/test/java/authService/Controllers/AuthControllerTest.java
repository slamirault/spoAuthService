package authService.Controllers;

import authService.Utility.PasswordManager;
import domain.Interfaces.IUserRepository;
import domain.User;
import domain.UserRepository;
import org.junit.Test;
import static org.easymock.EasyMock.*;
import org.easymock.EasyMockRunner;
import org.easymock.TestSubject;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;


@RunWith(EasyMockRunner.class)
public class AuthControllerTest
{


    @Test
    public void test1() throws Exception
    {

    }

    @Test
    public void create() throws Exception
    {
        String email = "stevie@amirault.org";
        String pwd = "c@T45678";

        PasswordManager pm = new PasswordManager();

        IUserRepository userRepository = createNiceMock(IUserRepository.class);
        HttpServletResponse response = createNiceMock(HttpServletResponse.class);
        User user = createNiceMock(User.class);
        expect(userRepository.createUser(email, pm.encodePassword(pwd))).andReturn(true);
        replay();

        AuthController controller = new AuthController();
        controller.create(email,pwd,null,response);

        //assertEquals(201, response);
    }

    @Test
    public void auth() throws Exception
    {

    }

    @Test
    public void findUser() throws Exception
    {

    }

    @Test
    public void validateEmail() throws Exception
    {

    }

    @Test
    public void setNewUser() throws Exception
    {

    }

}