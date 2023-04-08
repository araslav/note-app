package note.proxyseller.servive.impl;

import java.util.List;
import note.proxyseller.model.Note;
import note.proxyseller.repository.NoteRepository;
import note.proxyseller.servive.NoteService;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAll() {
        return noteRepository.findAll();
    }
}
