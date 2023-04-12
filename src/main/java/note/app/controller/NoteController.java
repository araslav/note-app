package note.app.controller;

import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import note.app.dto.request.NoteRequest;
import note.app.dto.response.NoteResponse;
import note.app.mapper.RequestDtoMapper;
import note.app.mapper.ResponseDtoMapper;
import note.app.model.Like;
import note.app.model.Note;
import note.app.model.User;
import note.app.service.LikeService;
import note.app.service.NoteService;
import note.app.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    private final LikeService likeService;
    private final RequestDtoMapper<NoteRequest, Note> noteRequestDtoMapper;
    private final ResponseDtoMapper<Note, NoteResponse> noteResponseDtoMapper;

    public NoteController(NoteService noteService, UserService userService, LikeService likeService, RequestDtoMapper<NoteRequest, Note> noteRequestDtoMapper, ResponseDtoMapper<Note, NoteResponse> noteResponseDtoMapper) {
        this.noteService = noteService;
        this.userService = userService;
        this.likeService = likeService;
        this.noteRequestDtoMapper = noteRequestDtoMapper;
        this.noteResponseDtoMapper = noteResponseDtoMapper;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("noteRequest", new NoteRequest());
        return "note";
    }

    @PostMapping("/add")
    public String save(@Valid NoteRequest noteRequest, Authentication auth) {
        if (Optional.ofNullable(auth).isPresent()) {
            noteRequest.setUserEmail(auth.getName());
        }
        noteService.save(noteRequestDtoMapper.toModel(noteRequest));
        return "redirect:/notes";
    }

    @PostMapping("/edite/{id}")
    public String update(@Valid NoteRequest noteRequest,
                         @PathVariable("id") String noteId, Authentication auth) {
        if (Optional.ofNullable(auth).isEmpty()) {
            return "redirect:/login";
        }

        Note note = noteService.findById(noteId);
        note.setText(noteRequest.getText());

        noteService.save(note);
        return "redirect:/user";
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable("id") String noteId, Authentication auth) {
        if (Optional.ofNullable(auth).isEmpty()) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(auth.getName()).get();
        noteService.deleteNote(noteId, user.getId());
        return "redirect:/user";
    }

    @PostMapping("/addLike/{id}")
    public String addLike(@PathVariable("id") String noteId, Authentication auth) {
        if (Optional.ofNullable(auth).isEmpty()) {
            return "redirect:/errorLike";
        }
        noteService.addLike(noteId, auth);
        return "redirect:/notes";
    }

    @PostMapping("/deleteLike/{id}")
    public String deleteLike(@PathVariable("id") String noteId, Authentication auth) {
        if (Optional.ofNullable(auth).isEmpty()) {
            return "redirect:/errorLike";
        }
        noteService.deleteLike(noteId, auth);
        return "redirect:/notes";
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "desc") String sortOrder,
                         Model model, Authentication auth) {
        Sort.Order order = new Sort.Order(Sort.Direction.fromString(sortOrder), "createdAt");
        model.addAttribute("noteList", noteService.getAll(Sort.by(order))
                .stream()
                .map(noteResponseDtoMapper::toDto)
                .peek(nr -> nr.setLiked(checkIsLike(auth, nr.getId())))
                .collect(Collectors.toList()));
        model.addAttribute("likeModel", new Like());
        return "notes";
    }

    private boolean checkIsLike(Authentication auth, String noteId) {
        if (Optional.ofNullable(auth).isEmpty()) {
            return false;
        }

        User user = userService.findByEmail(auth.getName()).orElse(null);

        if (Optional.ofNullable(user).isEmpty()) {
            return false;
        }

        return likeService
                .findByNoteIdAndUserId(noteId, user.getId()).isPresent();
    }
}
