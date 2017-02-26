package domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

public interface UserRepository extends Repository<User, Long> {

    Page<User> findAll(Pageable pageable);

    User findUser(String name, String country);

}