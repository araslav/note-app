package note.app.controller;

import jakarta.validation.Valid;
import note.app.dto.request.UserRegisterDto;
import note.app.mapper.RequestDtoMapper;
import note.app.model.User;
import note.app.service.AuthenticationService;
import note.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RequestDtoMapper<UserRegisterDto, User> userRegisterMapper;

    @Autowired
    public AuthenticationController(
            UserService userService, AuthenticationService authenticationService,
            RequestDtoMapper<UserRegisterDto, User> userRegisterMapper) {
        this.authenticationService = authenticationService;
        this.userRegisterMapper = userRegisterMapper;
    }

    @GetMapping("/registration")
    public String registerForm(Model model) {
        model.addAttribute("userregisterdto", new UserRegisterDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@Valid UserRegisterDto dto) {
        User user = userRegisterMapper.toModel(dto);
        authenticationService.register(user);
        return "redirect:/login";
    }
}
