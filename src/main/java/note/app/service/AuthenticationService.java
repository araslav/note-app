package note.app.service;

import note.app.model.User;

public interface AuthenticationService {
    User register(User user);

    User login(User user);
}
