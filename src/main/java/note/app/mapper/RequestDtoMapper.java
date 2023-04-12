package note.app.mapper;

public interface RequestDtoMapper<R, M> {
    M toModel(R dto);
}
