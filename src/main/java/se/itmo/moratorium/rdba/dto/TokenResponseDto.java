package se.itmo.moratorium.rdba.dto;

import lombok.Getter;
import lombok.Value;

@Value
public class TokenResponseDto {
    private final String token;
}
