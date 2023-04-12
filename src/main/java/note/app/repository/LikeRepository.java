package note.app.repository;

import java.util.Optional;
import note.app.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepository extends MongoRepository<Like, String> {
    Optional<Like> findOneByNoteIdAndUserId(String noteId, String userId);

    void deleteByNoteIdAndUserId(String noteId, String userId);
}
