package note.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteRequest {
    @NotNull
    private String text;
    private String userEmail;
}
