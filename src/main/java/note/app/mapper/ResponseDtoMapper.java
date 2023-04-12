package note.app.mapper;

public interface ResponseDtoMapper<M, R> {
    R toDto(M model);
}
