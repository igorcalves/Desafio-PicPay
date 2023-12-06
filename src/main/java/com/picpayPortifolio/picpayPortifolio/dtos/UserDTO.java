package com.picpayPortifolio.picpayPortifolio.dtos;

import com.picpayPortifolio.picpayPortifolio.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password,
                      UserType userType) {
}
