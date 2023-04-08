package note.proxyseller.controller;

import java.util.List;
import note.proxyseller.model.Note;
import note.proxyseller.servive.NoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAll() {
        return noteService.getAll();
    }
}
