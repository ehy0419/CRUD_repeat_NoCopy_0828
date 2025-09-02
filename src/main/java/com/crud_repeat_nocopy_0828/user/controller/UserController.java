package com.crud_repeat_nocopy_0828.user.controller;

import com.crud_repeat_nocopy_0828.user.dto.request.UserSaveRequestDto;
import com.crud_repeat_nocopy_0828.user.dto.request.UserUpdateRequestDto;
import com.crud_repeat_nocopy_0828.user.dto.response.UserResponseDto;
import com.crud_repeat_nocopy_0828.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.crud_repeat_nocopy_0828.common.consts.Const;
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
    public ResponseEntity<UserResponseDto> signup(
            @RequestBody UserSaveRequestDto dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> findOne(
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.findOne(id));
    }

    @PutMapping("/users/me")
    public ResponseEntity<UserResponseDto> update(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            // 사용하고자 하는 것은 프로젝트 내부의 상수 클래스
            // 여기서 사용 중인 import는
            // import org.apache.tomcat.util.bcel.Const;
            // 일정 컨트롤러에서는
            // import com.crud_repeat_nocopy_0828.common.consts.Const; 을 사용 중.
            @RequestBody UserUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(userService.update(userId, dto));
    }

    /**
     * 하드코딩(hard-coding)
     * 하드코딩은 값(문자열, 숫자 등)을 직접 코드에 박아넣는 것을 말해요.
     * 예) "LOGIN_USER"를 여기저기 문자열로 직접 쓰는 것.
     * 문제점
     * 오타 위험: "LOGINUSER", "login_user"처럼 살짝만 달라도 버그.
     * 중복 수정: 키를 바꾸고 싶을 때 모든 파일을 찾아 바꿔야 함.
     * 일관성/가독성↓: 어디가 정답인지 헷갈림.
     * 그래서 이런 값은 상수로 한 곳에 모아두고 Const.LOGIN_USER처럼 가져다 쓰는 게 좋아요.
     * */

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> delete(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(401).build();               // 인증 없음
        }
        Long userId = (Long) session.getAttribute(Const.LOGIN_USER); // 상수 사용
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        userService.deleteById(userId);
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
}
