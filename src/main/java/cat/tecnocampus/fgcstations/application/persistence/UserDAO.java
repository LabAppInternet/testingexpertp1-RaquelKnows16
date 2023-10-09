package cat.tecnocampus.fgcstations.application.persistence;

import cat.tecnocampus.fgcstations.domain.User;
import java.util.List;

public interface UserDAO {
    User findByUsername(String username);

    List<User> getUsers();

    boolean existsUser(String username);
}
