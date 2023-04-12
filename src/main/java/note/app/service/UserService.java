package note.app.service;

import java.util.Optional;
import note.app.model.User;

public interface UserService {
    User save(User user);
    User findById(String id);
    Optional<User> findByEmail(String email);
}
