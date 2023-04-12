package note.app.security;

import note.app.exeption.UserException;
import note.app.model.User;
import note.app.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = userService.findByEmail(userName)
                .orElseThrow(() -> new UserException("Didn't find in DB User with email " + userName));
        UserBuilder builder = withUsername(userName);
        builder.password(user.getPassword());
        return builder.build();
    }
}
