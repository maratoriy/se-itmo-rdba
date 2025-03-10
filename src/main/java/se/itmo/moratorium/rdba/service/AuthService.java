package se.itmo.moratorium.rdba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.itmo.moratorium.rdba.dto.UserRegistrationDto;
import se.itmo.moratorium.rdba.model.UserEntity;
import se.itmo.moratorium.rdba.repository.UserRepository;
import se.itmo.moratorium.rdba.mapper.UserMapper;
import se.itmo.moratorium.rdba.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    public String register(UserRegistrationDto registrationDto) {
        UserEntity user = userMapper.toEntity(registrationDto);
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        UserEntity savedUser = userRepository.save(user);
        return jwtUtil.generateToken(savedUser.getUsername());
    }

    public String authenticate(String login, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        UserDetails userDetails = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}