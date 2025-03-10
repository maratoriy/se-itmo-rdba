package se.itmo.moratorium.rdba.controller.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class UserRegistrationDto {
    private final String login;
    private final String email;
    private final String password;
}
