package com.crud_repeat_nocopy_0828.user.controller;

import com.crud_repeat_nocopy_0828.user.dto.request.UserSaveRequestDto;
import com.crud_repeat_nocopy_0828.user.dto.request.UserUpdateRequestDto;
import com.crud_repeat_nocopy_0828.user.dto.response.UserResponseDto;
import com.crud_repeat_nocopy_0828.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 1. @Controller vs @RestController
 * @Controller와 @RestController 둘 다 스프링에서 Controller를 지정해주기 위한 어노테이션으로 사용된다.
 * 둘의 주요한 차이점은 ResponseBody가 생성되는 방식인데 하나씩 알아가보자.
 *
 * 2. @Controller
 * @Controller 어노테이션은 Spring MVC의 전통적인 컨트롤러 어노테이션이다.
 * 기본적으로는 다음과 같은 시퀀스로 View를 반환한다.
 * */

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSaveRequestDto dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findOne(id));
    }

    @PutMapping("/users/me")
    public ResponseEntity<UserResponseDto> update(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody UserUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(userService.update(userId, dto));
    }

    @DeleteMapping("/users/me")
    public void delete(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("LOGIN_USER");
        userService.deleteById(userId);
        session.invalidate();
    }
}
