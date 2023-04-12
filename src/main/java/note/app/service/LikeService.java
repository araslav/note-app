package note.app.service;

import java.util.Optional;
import note.app.model.Like;

public interface LikeService {
    Like save(Like like);

    Like getById(String id);

    Optional<Like> findByNoteIdAndUserId(String noteId, String userId);

    void deleteByNoteIdAndUserId(String noteId, String id);
}
