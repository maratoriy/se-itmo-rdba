package se.itmo.moratorium.rdba.mapper;

import org.mapstruct.Mapper;
import se.itmo.moratorium.rdba.dto.UserLoginDto;
import se.itmo.moratorium.rdba.dto.UserRegistrationDto;
import se.itmo.moratorium.rdba.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserRegistrationDto registrationDto);

    UserLoginDto toDto(UserEntity userEntity);
}