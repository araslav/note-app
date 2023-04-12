package note.app.mapper.response;

import note.app.dto.response.NoteResponse;
import note.app.lib.Mapper;
import note.app.mapper.ResponseDtoMapper;
import note.app.model.Note;

@Mapper
public class NoteResponseDtoMapper implements ResponseDtoMapper<Note, NoteResponse> {
    @Override
    public NoteResponse toDto(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .text(note.getText())
                .user(note.getUser())
                .like(note.getLike())
                .isLiked(false)
                .build();
    }
}
