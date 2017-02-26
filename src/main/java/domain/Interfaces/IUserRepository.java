package domain.Interfaces;

import domain.User;
import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

public interface IUserRepository extends Repository<User, Long> {

    User findUserByEmail(String email);

    boolean createUser(String email, String encodedPwd);

}