package note.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
