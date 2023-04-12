package note.app.repository;

import java.util.List;
import java.util.Optional;
import note.app.model.Note;
import note.app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    Optional<Note> findById(String id);

    List<Note> findByUser(User user);
}
