package com.crud_repeat_nocopy_0828.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSaveRequestDto {

    @NotBlank(message = "유저명은 필수 입력값입니다.")
    @Size(max = 4, message = "유저명은 4글자 이내여야 합니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "비밀번호는 대소문자, 숫자, 특수문자를 포함해 8자 이상이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
