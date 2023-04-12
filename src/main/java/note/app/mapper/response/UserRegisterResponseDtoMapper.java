package note.app.mapper.response;

import note.app.dto.response.UserRegisterResponseDto;
import note.app.lib.Mapper;
import note.app.mapper.ResponseDtoMapper;
import note.app.model.User;

@Mapper
public class UserRegisterResponseDtoMapper implements ResponseDtoMapper<User, UserRegisterResponseDto> {
    @Override
    public UserRegisterResponseDto toDto(User user) {
        UserRegisterResponseDto userResponse = new UserRegisterResponseDto();
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}
