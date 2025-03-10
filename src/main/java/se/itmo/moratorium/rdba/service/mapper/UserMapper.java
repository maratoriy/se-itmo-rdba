package se.itmo.moratorium.rdba.service.mapper;

import org.mapstruct.Mapper;
import se.itmo.moratorium.rdba.controller.dto.UserLoginDto;
import se.itmo.moratorium.rdba.controller.dto.UserRegistrationDto;
import se.itmo.moratorium.rdba.model.users.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserRegistrationDto registrationDto);

    UserLoginDto toDto(UserEntity userEntity);
}