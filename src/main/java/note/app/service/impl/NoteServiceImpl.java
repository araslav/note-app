package note.app.service.impl;

import java.util.List;
import java.util.Optional;
import note.app.exeption.NoteException;
import note.app.exeption.UserException;
import note.app.model.Like;
import note.app.model.Note;
import note.app.model.User;
import note.app.repository.NoteRepository;
import note.app.service.LikeService;
import note.app.service.NoteService;
import note.app.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;
    private final LikeService likeService;

    public NoteServiceImpl(NoteRepository noteRepository, UserService userService, LikeService likeService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
        this.likeService = likeService;
    }

    @Override
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note findById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteException("Didn't find note in DB by id " + id));
    }

    @Override
    public void addLike(String noteId, Authentication auth) {
        Note note = findById(noteId);
        Like like = new Like();
        User user = userService.findByEmail(auth.getName())
                .orElseThrow(() -> new UserException("Didn't find in DB User with email " + auth.getName()));
        like.setUserId(user.getId());
        like.setNoteId(note.getId());
        likeService.save(like);
        note.getLike().add(like);
        noteRepository.save(note);
    }

    @Override
    public List<Note> getAll(Sort sort) {
        return noteRepository.findAll(sort);
    }

    @Override
    public void deleteLike(String noteId, Authentication auth) {
        Note note = findById(noteId);
        User user = userService.findByEmail(auth.getName())
                .orElseThrow(() -> new UserException("Didn't find in DB User with email " + auth.getName()));
        Optional<Like> byNoteIdAndUserId = likeService.findByNoteIdAndUserId(noteId, user.getId());

        if (byNoteIdAndUserId.isPresent()) {
            note.getLike().remove(byNoteIdAndUserId.get());
            noteRepository.save(note);
            likeService.deleteByNoteIdAndUserId(noteId, user.getId());
        }
    }

    @Override
    public List<Note> findByUser(User user) {
        return noteRepository.findByUser(user);
    }

    @Override
    public void deleteNote(String noteId, String userId) {
        noteRepository.deleteById(noteId);
        likeService.deleteByNoteIdAndUserId(noteId, userId);
    }
}
