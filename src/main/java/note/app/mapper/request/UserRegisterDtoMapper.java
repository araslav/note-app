package note.app.mapper.request;

import note.app.dto.request.UserRegisterDto;
import note.app.lib.Mapper;
import note.app.mapper.RequestDtoMapper;
import note.app.model.User;

@Mapper
public class UserRegisterDtoMapper implements RequestDtoMapper<UserRegisterDto, User> {
    @Override
    public User toModel(UserRegisterDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        return user;
    }
}
