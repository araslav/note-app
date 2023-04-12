package note.app.service;

import java.util.List;
import note.app.model.Note;
import note.app.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;

public interface NoteService {
    Note save(Note note);

    Note findById(String id);

    void addLike(String noteId, Authentication auth);

    List<Note> getAll(Sort sort);

    void deleteLike(String noteId, Authentication auth);

    List<Note> findByUser(User user);

    void deleteNote(String noteId, String userId);
}
