package com.crud_repeat_nocopy_0828.schedule.dto.request;

import com.crud_repeat_nocopy_0828.user.entity.User;
import lombok.Getter;

@Getter
public class ScheduleCreateRequestDto {

    private User user;
    private String userName;
    private String title;
    private String content;
}
