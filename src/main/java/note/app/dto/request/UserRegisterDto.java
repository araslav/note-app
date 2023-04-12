package note.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import note.app.validator.Email;
import note.app.validator.FieldsValueMatch;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords don't match!")
@Data
public class UserRegisterDto {
    @Email
    private String email;
    private String password;
    private String repeatPassword;
    @NotNull
    private String name;
}
