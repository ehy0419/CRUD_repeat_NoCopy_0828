package com.crud_repeat_nocopy_0828.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleSaveRequestDto {

    ///  보안/설계 측면에서 요청 DTO에 userId를 받지 말고 세션을 신뢰하도록 맞추면 깔끔!
    /// userId는 삭제 해야지만 주석처리
//    @NotNull(message = "작성자 ID는 필수입니다.")
//    private Long userId;

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 50, message = "제목은 50글자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;
}