package com.crud_repeat_nocopy_0828.user.repository;

import com.crud_repeat_nocopy_0828.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    // 필요한 이유는??
    /**
     * 권장안(A): 서비스는 DTO 1개만 받기 (세션은 컨트롤러에서만 처리)
     * 1) Controller (이름/상태코드/상수도 함께 정리)
     * @RestController
     * @RequiredArgsConstructor
     * public class LoginController { // LonginController → LoginController
     *
     *     private final UserService userService;
     *
     *     @PostMapping("/login")
     *     public ResponseEntity<String> login(
     *             @Valid @RequestBody LoginRequestDto dto, HttpServletRequest request) {
     *
     *         Long userId = userService.login(dto); // ← 서비스 시그니처를 1개로 맞춤
     *
     *         HttpSession session = request.getSession(true); // 새 세션 생성
     *         session.setAttribute(Const.LOGIN_USER, userId); // 문자열 리터럴 대신 상수 사용
     *
     *         return ResponseEntity.ok("로그인 성공"); // 본문 있을 때 200 OK
     *     }
     *
     *     @PostMapping("/logout")
     *     public ResponseEntity<Void> logout(HttpServletRequest request) {
     *         HttpSession session = request.getSession(false);
     *         if (session != null) {
     *             session.invalidate();
     *         }
     *         return ResponseEntity.noContent().build(); // 본문 없이 204 NO CONTENT 권장
     *     }
     * }
     *
     * 2) Service (DTO 1개 받도록 통일)
     * @Service
     * @RequiredArgsConstructor
     * public class UserService {
     *
     *     private final UserRepository userRepository;
     *     private final PasswordEncoder passwordEncoder;
     *
     *     @Transactional(readOnly = true)
     *     public Long login(LoginRequestDto dto) {
     *         User user = userRepository.findByEmail(dto.getEmail())
     *                 .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));
     *         if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
     *             throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
     *         }
     *         return user.getId();
     *     }
     * }
     *
     * */

    boolean existsByEmail(String email);
}
