package com.crud_repeat_nocopy_0828.schedule.service;

import com.crud_repeat_nocopy_0828.schedule.dto.request.ScheduleSaveRequestDto;
import com.crud_repeat_nocopy_0828.schedule.dto.request.ScheduleUpdateRequestDto;
import com.crud_repeat_nocopy_0828.schedule.dto.response.ScheduleResponseDto;
import com.crud_repeat_nocopy_0828.schedule.entity.Schedule;
import com.crud_repeat_nocopy_0828.schedule.repository.ScheduleRepository;
import com.crud_repeat_nocopy_0828.user.entity.User;
import com.crud_repeat_nocopy_0828.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;     // List만 사용하는 버전에서 명시적으로 사용

@Service
@RequiredArgsConstructor        // 의존성 주입
// 생성자 주입
// 필드 주입
// 세터 주입

/**
 * @Autowired 와 관련하여 의존성 주입
 * 주입별 차이는 주입되는 위치에 따라서 다르게 사용될 수 있습니다. 스프링에서는 다음과 같은 주입 방식을 지원한다:
 * 1. 생성자 주입(Constructor Injection)
 * 2. 세터 주입(Setter Injection)
 * 3. 필드 주입(Field Injection)
 *
 * 1. 생성자 주입
 * 특징
 * 클래스를 인스턴스화 할 때 모든 필수적인 의존성을 주입받아야 한다.
 * 생성자 호출 시점에 딱 1번만 호출된다는 것이 보장된다.
 * 생성자가 딱 1개만 있으면 @Autowired 생략가능하다. (스프링 빈에만 적용)
 * 불변성(Immutability)을 강조하여 객체를 불변 상태로 유지할 수 있다.
 * 생성자 호출 시점에 딱 1번만 호출한다.
 * 코드의 가독성이 높아진다.
 * 의존성 주입을 생성자를 통해 명시적으로 표현하기에 의존성이 명확하게 드러난다.
 * 테스트 용이성이 높아진다.
 * Mock 객체 등을 주입하여 테스트 하기 쉽다.
 *
 * 장점:
 *
 * 필수적인 의존성을 강제로 주입받기에 객체의 일관성을 보장 가능하다.
 * 의존성 변경에 유연하게 대처할 수 있다.
 *
 * 단점:
 * 의존성이 많은 경우 생성자의 파라미터 개수가 늘어날 수 있다.
 * 객체 생성 시 매번 모든 의존성을 주입해야하기에 코드의 중복이 발생할 수 있다.
 *
 * 2. 세터 주입
 * 특징:
 * 스프링 컨테이너가 세터 메서드를 호출하여 의존성을 주입한다.
 * 생성자 주입과 같이 있으면 생성자 주입 다음으로 이루어진다.
 * 선택적인 의존성이 있는 경우에 유용하다.
 *
 * 장점:
 * 선택적인 의존성에 유연하게 대처할 수 있다.
 *
 * 단점:
 * 외부에서 의존성을 변경하기 어렵다.
 * 세터 메서드가 노출되어 객체의 일관성을 해치는 가능성이 있다.
 * 코드의 가독성이 생성자 주입에 비해 낮아질 수 있다.
 *
 * 3. 필드 주입
 * 특징:
 * 스프링 컨테이너가 필드에 직접 접근하여 주입한다.
 * 주입받을 필드에 대한 접근 제어자를 private로 지정할 수 있으므로, 필드의 캡슐화를 유지할 수 있다.
 * 코드의 간결성을 유지할 수 있다.
 *
 * 장점:
 * 코드가 간결하고 읽기 쉽다.
 * 필드의 접근 제어자를 private로 설정하여 캡슐화를 유지한다.
 *
 * 단점:
 * 필드에 직접 접근하므로 외부에서 의존성을 변경하기 어렵다.
 * 주입할 의존성이 선택적이 아닌 경우에도 null이 될 수 있다.
 * 의존성을 명시적으로 표현하지 않기 때문에 코드의 의존성 관계 파악이 어려울 수 있다.
 *
 * 결론 :  * 생성자 주입을 선택하라!!!!
 *
 * 이유:
 * 1) 불변
 * 대부분의 의존관계는 종료시점까지 변경할 일이 없다. 오히려 대부분은 변하면 안된다.(불변해야한다)
 * 수정자 주입을 사용하면 set 메서드를 public으로 열어둬야하는데 이로 인하여 실수로 변경될 수 있고 좋은 설계방법이 아니다.
 * 생성자 주입은 객체 생성 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다. 따라서 불변하게 설계 가능하다.
 *
 * 2) 누락:
 * 프레임워크 없이 순수한 자바 코드를 단위 테스트 하는 경우 생성자 주입을 사용하면 주입 데이터를 누락 했을 때 컴파일 오류가 발생한다.
 * 그리고 IDE에서 바로 어떤 값을 필수로 주입 하는지 알 수 있다.
 *
 * 3) fianl 키워드:
 * 생성자 주입을 사용하면 필드에 final 키워드를 사용할 수 있다. 이로인해 생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에 막아준다.
 * 객체 안의 변수라면 생성자, static 블럭을 통한 초기화까지는 허용
 * 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 호출 이후에 호출되므로, 필드에 fianl 키워드를 사용할 수 없다.
 * 오직 생성자 주입 방식만 final 키워드를 사용할 수 있다.
 *
 * 컴파일 오류가 세상에서 가장 빠르고 좋은 오류다!!
 * 생성자 주입 방식을 선택하는 이유는 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살리는 방법이다.
 * 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다. 생성자 주입과 수정자 주입을 동시에 사용할 수 있다.
 * 항상 생성자 주입을 선택하라! 가끔 옵션이 필요하면 수정자 주입을 선택하라.
 * */

public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 요약 비교
     * 버전1(추천): toDto를 만들어 항상 그걸 사용
     * 장점: 중복 제거, 유지보수 쉬움, 응답 일관성.
     * 실무/테스트 코드에서 표준적인 방식.
     *
     * 버전2(학습/임시): 변환을 그 자리에서 직접 작성
     * 장점: 즉시 눈에 보임.
     * 단점: 중복, 실수/누락 위험, 응답 변경 시 고칠 곳 다수.
     *
     * */

    /** 일정 저장 */
    /// version1 return toDto(saved); 사용할 때
//    @Transactional      // 생성이기 때문에, 쿼리가 몇 번 나가느냐
//    public ScheduleResponseDto save(ScheduleSaveRequestDto request) {
//        // 일정리퀘스트를 저장하는 것이 아니라 일정을 저장해야한다
//        User user = userRepository.findById(request.getUserId())
//                .orElseThrow(
//                        () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
//                );
//
//        Schedule schedule = new Schedule(
//                user,
//                request.getTitle(),
//                request.getContent()
//        );
//
//        Schedule saved = scheduleRepository.save(schedule); // DB에 INSERT되고 PK/시간컬럼 등이 채워짐
//
//        return toDto(saved); // 저장된 엔티티를 응답 DTO로 변환해 반환
//    }

    ///  version 2 return toDto(saved); 사용하지 않을 때 (직접 풀어쓰기)
    @Transactional
    public ScheduleResponseDto save(Long loginUserId, ScheduleSaveRequestDto request) {
        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
                // 리팩터링 방향(세션 신뢰)
                //요청 DTO에서 userId를 제거하고, Service 시그니처를 (loginUserId, …) 형태로 통일.
        );

        Schedule schedule = new Schedule(
                user,
                request.getTitle(),
                request.getContent()
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);

        // 아래 변환 로직을 매번 손으로 작성해야 해서 중복/실수 위험 증가
        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getUser().getId(),
                savedSchedule.getUser().getUsername(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt()
        );
    }

    /** 전체 일정 조회 */
    ///  version 1 (★요구사항) Stream 없이 List만 사용해서 반환
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();                // DB에서 전체 스케줄 로드
        List<ScheduleResponseDto> result = new ArrayList<>(schedules.size());   // 미리 용량 지정(미세한 이점)

        for (Schedule schedule : schedules) {                                   // 향상된 for문으로 순회
            result.add(toDto(schedule));                                        // /// version1 사용할 때: 공통 변환기 사용
            // result.add(new ScheduleResponseDto(                              // /// version2 사용하지 않을 때: 직접 생성 (참고용)
            //         schedule.getId(),
            //         schedule.getUser().getId(),
            //         schedule.getUser().getUsername(),
            //         schedule.getTitle(),
            //         schedule.getContent(),
            //         schedule.getCreatedAt(),
            //         schedule.getUpdatedAt()
            // ));
        }
        return result;                                                          // List로 반환
    }

    /**
     * findAll()은 List<Schedule>를 줍니다.
     * stream().map(...)은 각 요소를 다른 타입으로 바꾸는 표준 패턴.
     * this::toDto는 메서드 참조(레퍼런스) 문법(= s -> toDto(s)와 동일).
     * JDK 11 이하라면 .toList() 대신 collect(Collectors.toList()) 사용.
     *
     * */

    ///  version 1 (이전 스트림 버전) return scheduleRepository.findAll().stream().map(this::toDto).toList(); 사용할 때
//    @Transactional(readOnly = true)
//    public List<ScheduleResponseDto> findAll() {
//        return scheduleRepository.findAll().stream() // List<Schedule> → Stream<Schedule>
//                .map(this::toDto)                    // 각 Schedule을 ScheduleResponseDto로 변환
//                .toList();                           // Stream → 불변에 가까운 List
//    }

    ///  version 2 (스트림 + 직접 풀어쓰기) 사용하지 않을 때
//    @Transactional(readOnly = true)
//    public List<ScheduleResponseDto> findAll() {
//        return scheduleRepository.findAll().stream()
//                .map(s -> new ScheduleResponseDto(
//                        s.getId(),
//                        s.getUser().getId(),
//                        s.getUser().getUsername(),
//                        s.getTitle(),
//                        s.getContent(),
//                        s.getCreatedAt(),
//                        s.getUpdatedAt()
//                ))
//                .toList();
//    }

    ///  version 3 초기 (의미·타입 오류 예시를 보존)
//    @Transactional(readOnly = true)
//    public List<ScheduleResponseDto> findAll(ScheduleSaveRequestDto request) {
//        User user = userRepository.existsById(request.getUserId()).orElseThrow(
//                () -> new IllegalArgumentException("일정이 없습니다.")
//        );
//
//        List<Schedule> schedules = scheduleRepository.findAll();
//        List<ScheduleResponseDto> scheduleResponseDtos = new ArrayList<>();
//
//        for (Schedule schedule : schedules) {
//            scheduleResponseDtos.add(new ScheduleResponseDto(
//                    schedule.getId(),
//                    user.getId(),
//                    user.getUsername(),
//                    schedule.getTitle(),
//                    schedule.getContent(),
//                    schedule.getCreatedAt(),
//                    schedule.getUpdatedAt()
//            ));
//        }
//        return scheduleResponseDtos;
//    }

    /** 단건 조회 */
    /// version 1 toDto(schedule); 사용할 때
    /// 단건도 결국 “엔티티 → DTO” 변환이 필요하므로 같은 규칙을 재사용.
    @Transactional(readOnly = true)
    public ScheduleResponseDto findOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
        return toDto(schedule);
    }

    /// version 2 toDto(schedule); 사용하지 않을 때
//    @Transactional(readOnly = true)
//    public ScheduleResponseDto findOne(Long id) {
//        Schedule schedule = scheduleRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
//        return new ScheduleResponseDto(
//                schedule.getId(),
//                schedule.getUser().getId(),
//                schedule.getTitle(),
//                schedule.getContent(),
//                schedule.getCreatedAt(),
//                schedule.getUpdatedAt());
//    }

    ///  version 1 return toDto(schedule); 사용할 때
    @Transactional
    public ScheduleResponseDto update(Long loginUserId, Long scheduleId, ScheduleUpdateRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));

        if (!schedule.getUser().getId().equals(loginUserId)) {
            // 인증은 됐지만 권한이 없는 경우 → 403으로 매핑하는 게 적절(아래 예외 핸들러 참고)
            throw new SecurityException("본인이 작성한 스케줄만 수정할 수 있습니다.");
        }

        schedule.update(
                dto.getTitle(),
                dto.getContent()
        );
        return toDto(schedule);
    }

    ///  version 2 return toDto(schedule); 사용하지 않을 때
//    @Transactional
//    public ScheduleUpdateResponseDto update(Long scheduleId, Long userId, ScheduleUpdateRequestDto dto) {
//        Schedule schedule = scheduleRepository.findById(scheduleId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
//        if (!userId.equals(schedule.getUser().getId())) {
//            throw new IllegalArgumentException("본인이 작성한 스케줄만 수정할 수 있습니다.");
//        }
//        schedule.update(dto.getTitle(), dto.getContent());
//        return new ScheduleUpdateResponseDto(
//                schedule.getId(),
//                schedule.getUser().getId(),
//                schedule.getTitle(),
//                schedule.getContent(),
//                schedule.getCreatedAt(),
//                schedule.getUpdatedAt());
//    }

    ///  version 1
    @Transactional
    public void delete(Long loginUserId, Long scheduleId) {  // deleteById(Long userId, Long scheduleId) ->
        ///  파라미터 순서 지키자.
        // Service는 (scheduleId, userId)인데 -> (Long userId, Long scheduleId) 수정
        // Controller는 (userId, id)로 호출 → 컴파일은 되지만 권한 체크가 틀어지는 잠재 버그.
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
        if (!loginUserId.equals(schedule.getUser().getId())) {       // 사용자 id와 일정 작성자 id 비교
            throw new IllegalArgumentException("본인이 작성한 스케줄만 삭제할 수 있습니다.");
        }
        scheduleRepository.delete(schedule);
    }

    ///  version 2
//    @Transactional
//    public void deleteById(Long scheduleId, Long userId) {
//        Schedule schedule = scheduleRepository.findById(scheduleId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
//        if (!schedule.getUser().getId().equals(userId)) {     // 일정 작성자id와 사용자 id 비교
//            throw new IllegalArgumentException("본인이 작성한 스케줄만 삭제할 수 있습니다.");
//        }
//        scheduleRepository.delete(schedule);
//    }

    /**
     * toDto가 하는 일 (요약)
     * 엔티티(Schedule) → **응답 DTO(ScheduleResponseDto)**로 항상 같은 규칙으로 변환.
     * 같은 변환 로직을 여러 군데에서 복붙하지 않도록 한 곳에 모아(DRY) 둡니다.
     * 필드가 바뀌어도 한 군데만 고치면 전체 API 응답이 일관되게 바뀝니다.
     * */
    private ScheduleResponseDto toDto(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),               // 1) 스케줄 PK: 리소스의 고유 식별자(URI 구성/클라이언트 저장용)
                schedule.getUser().getId(),     // 2) 작성자 PK: 권한 체크/프론트에서 본인글 표시 등에 필요
                schedule.getUser().getUsername(),   // 3) 작성자 이름: 리스트/상세에서 바로 표시하려고 함께 내려줌
                schedule.getTitle(),            // 4) 제목
                schedule.getContent(),          // 5) 내용
                schedule.getCreatedAt(),        // 6) 생성 시각: 정렬/표시/감사로그 등에 유용
                schedule.getUpdatedAt()         // 7) 수정 시각: 변경 여부/최종 갱신 시점 표시
        );

        /**
         * 왜 이렇게 쓰나요? (이유/의도)
         * 단일 책임: “변환 규칙”을 한 메서드에 모아 서비스 로직과 분리 → 가독성↑.
         * DRY: 여러 서비스 메서드(저장/조회/수정/삭제 결과)에서 중복 코드를 제거.
         * 일관성: 응답 스키마가 바뀌어도 toDto만 수정하면 전체 API가 일관되게 변경.
         * 안전성: 엔티티를 직접 노출하면 JPA 내부 구조(지연로딩 프록시, 양방향 연관관계)가 외부로 새어 나가거나, 추가 컬럼 노출 위험이 있어요. DTO는 내보낼 것만 선택적으로 담습니다.
         * 성능/지연로딩 주의: schedule.getUser()는 LAZY면 실제 접근 시 쿼리가 나갈 수 있어요.
         * 현재처럼 트랜잭션 안에서 쓰면 OK.
         * 대량 조회에서 N+1 쿼리가 생기면 fetch join/전용 조회 쿼리/프로젝션/Batch Size 등을 고려하세요.
         * */
    }
}