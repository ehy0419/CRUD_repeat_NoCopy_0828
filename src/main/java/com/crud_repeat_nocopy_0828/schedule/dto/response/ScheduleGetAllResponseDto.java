package com.crud_repeat_nocopy_0828.schedule.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleGetAllResponseDto {

    private final Long id;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public ScheduleGetAllResponseDto(Long id,
                                     String userName, String title, String content,
                                     LocalDate createdAt, LocalDate updatedAt
    ) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
