package note.app.service.impl;

import java.util.Optional;
import note.app.exeption.AuthenticationException;
import note.app.model.User;
import note.app.repository.UserRepository;
import note.app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder,
                                     UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AuthenticationException(user.getEmail() + " email already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User login(User user) {
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isEmpty()) {
            throw new AuthenticationException("Bad credentials!");
        }
        User userFromDb = userByEmail.get();
        if (!passwordEncoder.matches(user.getPassword(), userFromDb.getPassword())) {
            throw new AuthenticationException("Bad credentials!");
        }
        return userFromDb;
    }
}
