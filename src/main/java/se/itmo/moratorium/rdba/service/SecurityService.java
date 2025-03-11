package se.itmo.moratorium.rdba.service;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.itmo.moratorium.rdba.model.UserEntity;
import se.itmo.moratorium.rdba.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;

    @PermitAll
    public UserEntity getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDetails.getUsername()));
    }
}
