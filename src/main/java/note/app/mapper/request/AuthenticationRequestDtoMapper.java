package note.app.mapper.request;

import note.app.dto.request.AuthenticationRequestDto;
import note.app.lib.Mapper;
import note.app.mapper.RequestDtoMapper;
import note.app.model.User;

@Mapper
public class AuthenticationRequestDtoMapper
        implements RequestDtoMapper<AuthenticationRequestDto, User> {
    @Override
    public User toModel(AuthenticationRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}
