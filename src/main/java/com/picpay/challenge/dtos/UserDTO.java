package com.picpay.challenge.dtos;

import com.picpay.challenge.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(
        String firstName,
        String lastName,
        String document,
        BigDecimal balance,
        String email,
        String password,
        UserType userType
) {
}
