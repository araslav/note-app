package note.app.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import note.app.model.Like;
import note.app.model.User;

@Data
@Builder
public class NoteResponse {
    private String id;
    private String text;
    private User user;
    private List<Like> like;
    private boolean isLiked;
    private LocalDateTime createdAt;
}
