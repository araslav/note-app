package note.app.controller;

import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import note.app.dto.request.UserRegisterDto;
import note.app.dto.response.NoteResponse;
import note.app.dto.response.UserRegisterResponseDto;
import note.app.mapper.ResponseDtoMapper;
import note.app.model.Note;
import note.app.model.User;
import note.app.service.NoteService;
import note.app.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final NoteService noteService;
    private final UserService userService;
    private final ResponseDtoMapper<User, UserRegisterResponseDto> responseDtoMapper;
    private final ResponseDtoMapper<Note, NoteResponse> noteResponseDtoMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(NoteService noteService,
                          UserService userService, ResponseDtoMapper<User,
                          UserRegisterResponseDto> responseDtoMapper,
                          ResponseDtoMapper<Note, NoteResponse> noteResponseDtoMapper,
                          PasswordEncoder passwordEncoder) {
        this.noteService = noteService;
        this.userService = userService;
        this.responseDtoMapper = responseDtoMapper;
        this.noteResponseDtoMapper = noteResponseDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getData(Model model, Authentication auth) {
        if (Optional.ofNullable(auth).isEmpty()) {
            return "emptyUserPage";
        }
        Optional<User> optionalUser = userService.findByEmail(auth.getName());
        if (optionalUser.isEmpty()) {
            return "errorPage";
        }

        model.addAttribute("user", responseDtoMapper.toDto(optionalUser.get()));
        model.addAttribute("noteList", noteService.findByUser(optionalUser.get())
                .stream()
                .map(noteResponseDtoMapper::toDto)
                .collect(Collectors.toList()));
        return "userPage";
    }

    @PostMapping("/edite")
    public String editeUser(@Valid UserRegisterDto dto, Authentication auth) {
        User user = userService.findByEmail(auth.getName()).get();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }
}
