package com.crud_repeat_nocopy_0828.user.controller;

import com.crud_repeat_nocopy_0828.common.consts.Const;
import com.crud_repeat_nocopy_0828.user.dto.request.LoginRequestDto;
import com.crud_repeat_nocopy_0828.user.entity.User;
import com.crud_repeat_nocopy_0828.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDto dto, HttpServletRequest request) {

        User user = userService.login(dto);   // User로 받기
        Long userId = user.getId();           // 여기서 ID만 추출

        /**
         * 에러 원인: userService.login(dto) 호출과 UserService.login(...) 메서드 시그니처가 불일치해서 생깁니다.
         * 현재 UserService 쪽에 **파라미터 2개짜리 login(...)**만 정의돼 있어서,
         * 1개만 넘기니 컴파일러가 “2개 필요, 1개 받음”이라며 오류를 냅니다.
         * */

        /**
         * 에러문 : error: incompatible types: User cannot be converted to Long
         *         > Long userId = userService.login(dto); <
         * */

        HttpSession session = request.getSession();           // 신규 세션 생성, JSESSIONID 쿠키 발급
        session.setAttribute(Const.LOGIN_USER, userId);       // 서버 메모리에 세션 저장, 문자열 리터럴 대신 상수 사용
        // 으로 수정. 수정 전 : session.setAttribute("LOGIN_USER", userId);
        return ResponseEntity.ok("로그인 성공");          // 본문 있을 때 200 OK
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");        // 본문 없이 204 NO CONTENT 권장

        /// 멱등성의 원리???
        /// 이미 로그아웃 이어도 204 응답
        /// 로그인 200 OK, 로그아웃 본문 없을 때 204 NO CONTENT
    }

    /// 본문 없이 멱등하게 가려면??    -- 204 No Content
//    public ResponseEntity<Void> logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) session.invalidate();
//        return ResponseEntity.noContent().build(); // 204 No Content
//    }
}
