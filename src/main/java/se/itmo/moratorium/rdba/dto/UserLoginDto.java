package se.itmo.moratorium.rdba.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class UserLoginDto {
    private final String login;
    private final String password;
}