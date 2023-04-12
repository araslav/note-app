package note.app.model;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Builder
@Document("Notes")
public class Note {
    @Id
    private String id;
    private String text;
    @DocumentReference
    private User user;
    @DBRef
    private List<Like> like;
    @CreatedDate
    private Date createdAt;
}
