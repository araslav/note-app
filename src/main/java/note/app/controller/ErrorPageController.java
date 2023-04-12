package note.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {
    @GetMapping("/errorLike")
    public String errorLikePage() {
        return "errorLike";
    }
}
