package com.crud_repeat_nocopy_0828.comment.entity;

import com.crud_repeat_nocopy_0828.common.entity.BaseEntity;
// BaseEntity 공통 필드(common.entity)에 있는 createdAt, updatedAt을/를 상속하려는 엔티티
import com.crud_repeat_nocopy_0828.schedule.entity.Schedule;
import com.crud_repeat_nocopy_0828.user.entity.User;
// 댓글 엔티티가 연관관계에 있는 일정과 사용자 엔티티
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// 롬복이 모든 필드의 getter를 컴파일 시 자동 생성
@Entity
// 댓글 클래스를 JPA가 테이블과 매핑해서 관리하도록 표시해준다.

/**
 * 매핑(Mapping)이란?
 * 뜻: A를 B에 “대응시켜 연결”하는 과정/규칙.
 * 사용처
 * 1) JPA 엔티티 ↔ 테이블:
 *      Comment.user(객체 참조) ↔ comment.user_id(FK 컬럼)로 연결하는 것 → @ManyToOne + @JoinColumn이 바로 이것이 매핑 규칙
 * 2) DTO ↔ 엔티티:
 *      네트워크/뷰용 DTO 필드들을 엔티티 필드에 대응시켜 복사. (예: MapStruct, ModelMapper)
 * 3) 키-값 자료구조(Map):
 *      키를 값에 대응시키는 저장 방식도 “매핑”의 한 종류.
 * 4) URL ↔ 컨트롤러:
 *      GET /users를 UserController.list()에 연결하는 것도 라우팅 매핑.
 * */

/**
 * JPA가 테이블과 매핑해서 관리한다는 의미는?
 * @Entity, @ManyToOne, @JoinColumn 같은 매핑 정보(메타데이터) 를 읽어
 * 어떤 클래스가 어떤 테이블/컬럼과 연결되는지 연관관계(FK)와 로딩 전략(LAZY/EAGER)을 알고 JPA가 SQL을 자동 생성/실행합니다.
 * 또한 영속성 컨텍스트(1차 캐시) 로 엔티티를 관리하고 변경 감지(Dirty Checking) 로 UPDATE도 자동으로 만들어 줍니다.
 *
 * 변경 감지, 더티 체킹
 * 더티체킹이란 영속성 컨텍스트가 관리하는 엔티티의 변경 사항을 감지하고, 트랜잭션이 끝날 때 자동으로 DB에 반영하는 것이다.
 * */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 파라미터가 없는 기본 생성자를 자동 생성(JPA 리플렉션 용)

/**
 * @NoArgsConstructor , @RequiredArgsConstructor
 * 용어 파자
 * @No-Args-Constructor
 * No: 없다
 * Args: Arguments(매개변수)
 * Constructor: 생성자
 * → “매개변수가 없는 생성자 하나 만들어 줘!”
 *
 * @Required-Args-Constructor
 * Required: 필수의(반드시 필요한)
 * Args: (Lombok 기준) final 또는 @NonNull 로 표시된 필드들
 * Constructor: 생성자
 * → “필수(= final 또는 @NonNull) 필드만 받는 생성자 만들어 줘!”
 *
 * 중요: Required의 기준은 Lombok의 @NonNull/final 이고,
 *       @NotNull(Bean Validation)은 무관합니다.
 *
 * 용어 조합
 * @NoArgsConstructor       : 매개변수 없는 기본 생성자를 만든다.
 * @RequiredArgsConstructor : 필수 필드만 받는 생성자를 만든다.
 *
 * 공통점
 * 생성자를 자동 생성
 *
 * 차이점
 * @NoArgsConstructor
 * 매개변수 없는 기본 생성자 를 만들어준다
 * @RequiredArgsConstructor
 * final 필드 + @NonNull 필드만 받는 생성자 를 만들어준다.
 *
 * 언제 사용하나
 * @NoArgsConstructor
 * JPA 리플렉션에 사용, (access = PROTECTED) 또는 (access = AccessLevel.PROTECTED)
 * @RequiredArgsConstructor
 * 불변 / 필수값만 받는 편의 생성자 대용. 서비스 / 컴포넌트 의존성 주입에도 사용된다
 * */

/**
 * @NoArgsConstructor(access = PROTECTED)
 * @NoArgsConstructor(access = AccessLevel.PROTECTED) 의 차이는??
 *
 * 1) 뜻은 완전히 동일합니다.
 * 2) PROTECTED는 lombok.AccessLevel.PROTECTED의 상수값이고,
 *    보통은 AccessLevel.PROTECTED로 풀네임을 쓰는 게 일반적이에요.
 *
 * 3) 효과: 파라미터 없는 기본 생성자를 protected로 만들어 줌.
 * @NoArgsConstructor(access = AccessLevel.PROTECTED)
 * // 컴파일 후: protected Comment() { }
 * */

/**
 * “파라미터 없는 기본 생성자”란?
 * 정의: 매개변수가 하나도 없는 생성자.
 * 예: public Comment(){}
 * JPA 필수: 하이버네이트가 리플렉션/프록시로 엔티티를 만들려면 꼭 필요해요.
 * 접근 제어자는 protected 권장: 외부에서 의미 없이 빈 객체를 만드는 걸 막고, JPA는 내부적으로 접근 가능.
 * */

public class Comment extends BaseEntity {
    /**
     * 이 클래스는 '댓글' 이라는 도메인 객체를 RDB 테이블과 연결하는 JPA 엔티티 이다.
     * 댓글은 일정과 사용자 간의 연관관계를 가지고 있다.
     * 댓글과 일정은 M:1 의 관계를 가지고 있다. 여러 댓글들이 하나의 일정에 속합니다(의존합니다).
     * 댓글과 사용자는 M:1 의 관계를 가지고 있다. 여러 댓글들은 한 명의 사용자가 작성합니다.
     * */

    @Id
    // 기본키 (PK)를 제공
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // DB의 AUTO_INCREMENT를 써서 ID를 만듦
    // ID 생성은 IDENTITY/SEQUENCE/TABLE/AUTO 가 있다.

    /**
     * 전략별 ID 생성(장단점 압축)
     * 1) IDENTITY(지금 사용): DB가 즉석 번호 발급(AUTO_INCREMENT).
     *      ✔ 간단 / ❗ INSERT가 빨리 나가서 배치 최적화가 제한될 수 있음
     *
     * 2) SEQUENCE: 시퀀스 객체에서 번호를 미리 받아서 INSERT.
     *      ✔ 대량 저장/성능 유리(allocationSize 튜닝) / ❗ 시퀀스 없는 DB에선 불가
     *
     * 3) TABLE: 번호용 테이블 사용.
     *      ✔ 거의 모든 DB / ❗ 느림, 요즘 잘 안 씀
     *
     * 4) AUTO: JPA가 DB 벤더 보고 자동 선택.
     *      ✔ 초기 편의 / ❗ 예측 어려워 실무에선 보통 명시
     * */

    /**
     * “DB의 AUTO_INCREMENT로 ID를 만든다”의 SQL 작동 원리
     * 정의
     * DB가 INSERT할 때 자동으로 증가하는 정수 ID를 붙여 주는 기능. 보통 PK(기본키) 컬럼에 씁니다.
     * 작동 원리
     * 테이블마다 “다음 번호” 카운터를 DB가 관리합니다.
     * INSERT 시 id를 비워 두면 DB가 다음 값을 할당해 저장합니다.
     * 같은 값을 두 번 주지 않으니 유일성이 보장됩니다(보통 PK 제약과 함께 사용).
     * 실패/롤백이 있으면 번호가 건너뛰기도 합니다(연속적 보장은 아님).
     * JDBC에서는 getGeneratedKeys()로 방금 생성된 키를 돌려받습니다.
     * 요약: INSERT → DB가 새 번호 발급 → 하이버네이트가 그 번호를 되돌려 받아 엔티티에 넣음.
     * */

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // 댓글 N : 1 일정. 댓글 입장에서 “하나의 Schedule에 속한다”.
    // fetch = LAZY : 실제로 필요할 때만 DB에서 일정 조회(지연 로딩).
    // ManyToOne = EAGER라서 명시적으로 LAZY로 바꾼 것.

    /**
     * @ManyToOne(fetch = FetchType.LAZY)
     * 사용하는 이유
     * 댓글 리스트를 가져올 때, 매번 일정과 사용자까지 즉시 (EAGER) 끌고 오면 N + 1 문제와 성능이 저하되는 문제 발생.
     * 그래서 필요할 때만 불러오게 하는 LAZY가 기본이다.
     * 하지만 ManyToOne의 기본값은 EAGER 라서 명시적으로 바꾼 것.
     *
     * 작동 원리
     * 처음엔 프록시(가짜 객체) 를 넣어두고, 실제로 schedule.getTitle() 같은 필드/메서드 접근 시점에 그때 SELECT를 날려 채우는 방식.
     * */

    @JoinColumn(name = "schedule_id")
    // @JoinColumn(name="schedule_id") : 댓글 테이블에 FK 컬럼 이름을 schedule_id로 지정.
    //(없으면 보통 schedule_id로 유추되지만 명시가 안전/가독성↑)
    private Schedule schedule;
    // 객체 환경에서는 외래키 숫자 id를 들고 있지 않고, 연관된 객체 자체를 참조한다.
    // DB에서는 comment 테이블에 schedule_id 로 숫자 FK로 저장
    // 코드 내에서는 Comment -> Schedule 객체를 참조한다

    /**
     * @JoinColumn 과 @Column의 비교
     * @JoinColumn
     * 객체 참조(Schedule, User) <-> FK(Comment)를 연결하는 연관관계 매핑용
     * 매핑 대상 : 외래키 (FK) 컬럼
     * 사용처 : 연관관계 필드(엔티티 타입 필드)
     * 역할 : FK 컬럼명/널 허용 여부 등 지정
     * @Column
     * 값 타입(String, int) 컬럼 제약 설정용
     * 매핑 대상 : 일반 값 컬럼
     * 사용처 : 기본 타입 / 문자열 등
     * 역할 : 길이, nullable, unique 등의 제약 지정
     * */

    /**
     * @ManyToOne 필드에는 @JoinColumn을 씁니다. @Column은 쓰면 안 됩니다.
     * @Column은 값 타입(문자열/숫자 등) 전용이고, 연관관계 필드(엔티티 타입) 에서는 무시되거나 오류가 납니다.
     * 연관관계(FK) 컬럼의 이름 / 널 여부 / 유니크 등은 @JoinColumn에서 설정하세요.
     * */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // 댓글 N : 1 사용자와 매핑.
    // optional : 이 연관관계는 반드시 존재해야 한다. (NULL 금지)

    /**
     * JPA에서 프록시(Proxy)는 실제 엔티티 객체를 대신하여 데이터베이스 조회를 지연시키는 가짜 객체입니다.
     * 이는 연관된 엔티티에 접근할 때 실제 데이터를 필요한 시점까지 로딩을 미루는 지연 로딩(Lazy Loading)을 구현하는 기술로,
     * 불필요한 쿼리 실행을 방지하고 성능을 최적화하는 데 사용된다.
     *
     * 프록시의 특징
     * 1) 데이터베이스 조회 지연:
     * 프록시 객체는 실제 엔티티 데이터가 필요하기 전까지는 데이터베이스에서 데이터를 로드하지 않습니다.
     * 2) 실제 객체 상속:
     * 프록시 객체는 실제 엔티티 객체를 상속받아 만들어지며, 실제 엔티티의 기능과 동일하게 동작합니다.
     * 3) 실제 데이터 접근 시점:
     * 프록시 객체의 특정 메서드를 호출하거나 속성에 접근하는 등 실제 엔티티 데이터가 필요한 시점이 되면,
     * 프록시 객체는 그제서야 실제 데이터베이스 조회를 실행하고, 해당 데이터를 메모리에 로드한 후 실제 엔티티 객체로 변경되어 작업을 수행합니다.
     * 4) 성능 최적화:
     * 모든 연관된 엔티티를 한 번에 로딩하지 않으므로,
     * 메모리 사용량을 줄이고 불필요한 데이터베이스 쿼리 실행을 방지하여 애플리케이션의 성능을 향상시킵니다.
     * */

    /// <<< ---- 연관관계에서 주인인 댓글 ---- >>>
    @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
    // FK 컬럼명은 user_id.
    private User user;
    // 코드 내에서는 Comment -> User 객체를 참조한다.
    // DB에서는 comment 테이블에 user_id 로 숫자 FK로 저장

    /**
     * @ManyToOne + @JoinColumn의 역할
     * 객체 참조 → FK로 자동 변환해주는 것
     * 저장 시: schedule.getId() / user.getId()를 읽어 FK 컬럼에 넣음
     * 조회 시: FK로 연관 엔티티를 프록시/실체로 로딩
     * */

    /**
     * 댓글을 저장하고 조회하는 방법은???
     * 저장
     * 1. Schedule schedule와 User user(영속/프록시)를 이미 갖고 있음
     * 2. Comment comment = new Comment(schedule, user, "내용");
     * 3. em.persist(comment) - em : EntityManager,
     *      em.persist(comment) : “이 comment를 저장해(INSERT)”라고 자바에서 지시하면,
     *                              JPA(하이버네이트)가 알맞은 SQL을 만들어 DB에 보내 줍니다.
     * 4. IDENTITY라 PK 필요 → INSERT 실행
     * <SQL>
     *     insert into comment (schedule_id, user_id, content, created_at, ...)
     *     values (schedule.id, user.id, '내용', now(), ...)
     * </SQL>
     * 5. 트랜잭션 커밋
     *
     * <<<--- 동작 순서 코드 --->>>
     *
     *     @Service
     *     @RequiredArgsConstructor
     * public class CommentService {
     *
     *     private final EntityManager em; // 스프링이 주입
     *
     *     @Transactional
     *     public Long addComment(Long scheduleId, Long userId, String content) {
     *         // 1) 연관 엔티티 핸들 확보 (프록시여도 OK)
     *         Schedule s = em.getReference(Schedule.class, scheduleId); // 지연 참조 (SELECT 없이 FK만 씀)
     *         User u = em.getReference(User.class, userId);
     *
     *         // 2) 엔티티 생성 (필수 값들을 한 번에)
     *         Comment c = new Comment(s, u, content);
     *
     *         // 3) 저장 지시 (영속화)
     *         em.persist(c); // 여기가 "INSERT 준비/실행"의 트리거
     *
     *         // 4) 트랜잭션 커밋 시점(또는 IDENTITY면 더 빨리) 실제 INSERT + PK 회수
     *         return c.getId();
     *     }
     * }
     *
     *     </--->
     *
     * <<<--- JPA가 만드는 SQL --->>>
     *
     *     -- flush/commit 시점에 하이버네이트가 실행
     * insert into comment (schedule_id, user_id, content, created_at, updated_at)
     * values (?, ?, ?, ?, ?);
     *
     * -- MySQL은 AUTO_INCREMENT로 PK 생성.
     * -- 하이버네이트는 JDBC의 getGeneratedKeys() 등으로 생성된 id를 돌려받아 c.id에 세팅.
     *
     *     em.getReference()는 프록시를 돌려줘서 추가 SELECT 없이 FK만 써도 될 때 유리합니다.
     *     em.find()는 즉시 SELECT를 날려 실제 엔티티를 로딩합니다.
     *
     *     </--->></>
     *
     * 조회
     * 1. commentRepository.findById(1L) → 코멘트 엔티티 1차 캐시/DB에서 로딩
     * 2. comment.getSchedule() 호출 전까지는 프록시 상태
     * 3. comment.getSchedule().getTitle() 같은 실제 접근 순간에 SELECT schedule ... 실행
     * */

    @Column(nullable=false, length=500)     // 실무에서 제약을 명시
    private String content;
    // 일반 문자열 컬럼

    ///  생성자
    //이름이 클래스명 Comment 과 같고 리턴 타입이 없으면 생성자.
    //댓글을 “유효한 상태”로 만들기 위해 필요한 값들을 한 번에 받습니다.
    public Comment(Schedule schedule, User user, String content
    ) {
        this.schedule = schedule;
        this.user = user;
        this.content = content;
    }

    /**
     * “생성자에서 댓글을 유효한 상태로 만든다”는 의미
     * 유효한 상태 = 도메인 규칙을 만족하는 상태.
     * 예: schedule과 user는 반드시 존재해야 한다.
     *    content는 비어 있으면 안 된다, 길이 제한을 지킨다.
     * 그래서 생성자에서 필수 값을 한 번에 받으면,
     * “필수 값 없이 불완전한 Comment”가 못 만들어지도록 막는 효과가 있어요.
     * */

    /**
     * 검증 코드까지 완전체
     * public Comment(Schedule schedule, User user, String content) {
     *     this.schedule = Objects.requireNonNull(schedule, "일정은 필수입니다");
     *     this.user = Objects.requireNonNull(user, "사용자는 필수입니다");
     *     this.content = Objects.requireNonNull(content, "댓글 내용은 필수입니다").trim();
     *     if (this.content.isEmpty()) throw new IllegalArgumentException("댓글 내용이 비어있습니다");
     * }
     * */
}
