package com.crud_repeat_nocopy_0828.schedule.dto.response;

import com.crud_repeat_nocopy_0828.user.entity.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleCreateResponseDto {

    // final 을 붙이는 이유
    // Response에서는 한번 정해지면 바꿀 이유가 없다.
    private final Long id;
    private final User user;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public ScheduleCreateResponseDto(Long id, User user, String userName, String title, String content, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.user = user;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
