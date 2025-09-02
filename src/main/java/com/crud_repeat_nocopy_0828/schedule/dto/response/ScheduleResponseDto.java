package com.crud_repeat_nocopy_0828.schedule.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public ScheduleResponseDto(Long id, Long userId, String title, String content, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
