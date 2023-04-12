package note.app.mapper.request;

import java.util.ArrayList;
import note.app.dto.request.NoteRequest;
import note.app.lib.Mapper;
import note.app.mapper.RequestDtoMapper;
import note.app.model.Note;
import note.app.model.User;
import note.app.service.UserService;

@Mapper
public class NoteRequestDtoMapper
        implements RequestDtoMapper<NoteRequest, Note> {
    private final UserService userService;

    public NoteRequestDtoMapper(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Note toModel(NoteRequest dto) {
        User user = userService.findByEmail(dto.getUserEmail()).orElse(null);

        return Note.builder()
                .text(dto.getText())
                .user(user)
                .like(new ArrayList<>())
                .build();
    }
}
