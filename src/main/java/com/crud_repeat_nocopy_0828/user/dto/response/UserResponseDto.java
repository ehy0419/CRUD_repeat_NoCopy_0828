package com.crud_repeat_nocopy_0828.user.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public UserResponseDto(Long id,
                           String name, String email,
                           LocalDate createdAt, LocalDate updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
