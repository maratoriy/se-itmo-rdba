package se.itmo.moratorium.rdba.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.itmo.moratorium.rdba.dto.UserResponseDto;
import se.itmo.moratorium.rdba.mapper.UserMapper;
import se.itmo.moratorium.rdba.model.UserEntity;
import se.itmo.moratorium.rdba.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
