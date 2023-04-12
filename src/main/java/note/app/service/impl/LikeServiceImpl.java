package note.app.service.impl;

import java.util.Optional;
import note.app.exeption.LikeException;
import note.app.model.Like;
import note.app.repository.LikeRepository;
import note.app.service.LikeService;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public Like getById(String id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new LikeException("Didn't find in DB Like with id " + id));
    }

    @Override
    public Optional<Like> findByNoteIdAndUserId(String noteId, String userId) {
        return likeRepository.findOneByNoteIdAndUserId(noteId, userId);
    }

    @Override
    public void deleteByNoteIdAndUserId(String noteId, String userId) {
        likeRepository.deleteByNoteIdAndUserId(noteId, userId);
    }
}
