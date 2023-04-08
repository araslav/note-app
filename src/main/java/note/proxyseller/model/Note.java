package note.proxyseller.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Document("notes")
public class Note {
    @Id
    private String id;
    private String text;
    @DocumentReference
    private User user;
    @DBRef(lazy = true)
    private List<Like> like;
}
