package com.crud_repeat_nocopy_0828.user.entity;

import com.crud_repeat_nocopy_0828.common.entity.BaseEntity;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
// 롬복(Lombok) 애너테이션
// getter와 기본 생성자를 자동 생성해 줍니다.
// 컴파일 시점(Annotation Processing 단계)에 getId(), getUserName() 같은 메서드와 기본 생성자를 자동 생성합니다.

@Entity
// 이 클래스를 JPA 엔티티로 등록. 엔티티 매니저가 영속성 컨텍스트에서 관리할 수 있게 됩니다.
// @Table을 생략하면 기본 테이블명은 클래스명(User)을 스네이크케이스("user")로 유추(벤더/네이밍전략에 따라)합니다.
// 참고: 데이터베이스에 따라 "user"는 예약어일 수 있어 @Table(name="users")로 명시하는 습관이 좋습니다.

/**
 * @Entity 가 붙으면 내부적으로 있는 일은?
 * 부트 기동 시 엔티티 메타모델이 만들어지고, 엔티티매니저팩토리가 해당 클래스를 테이블과 매핑해서 관리합니다.
 * */

@Getter
// 클래스 모든 필드에 대해 getXxx() 메서드(getter 메서드)를 롬복이 생성해준다.
// JPA는 프록시/지연로딩 등에서 게터 접근이 쓰입니다.
// public LocalDateTime getCreatedAt() {
//     return createdAt;
// }

@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 파라미터 없는 기본 생성자 생성. JPA는 리플렉션을 위해 기본 생성자가 필요합니다.
// 관례적으로 public 보다는 protected로 두는 걸 권장: @NoArgsConstructor(access = AccessLevel.PROTECTED)

/// 기본 생성자가 필요한 이유는?
// JPA가 리플렉션으로 객체를 생성/초기화하므로 파라미터 없는 생성자가 필수입니다. 접근 제어자는 protected 권장(무분별한 직접 생성 방지).

/// @Setter가 없는 이유는?
// 엔티티의 무분별한 상태 변경을 막기 위해 보통 @Setter를 최소화하고, 생성자/도메인 메서드로 의도를 가진 변경만 허용합니다.
// 불변/업무 규칙 보존을 위해서고, 변경은 의도 있는 메서드/생성자/서비스에서만 하도록 유도합니다.

/**
 * 파라미터 없는 기본 생성자 생성은 Lombok의 @NoArgsConstructor가 그 생성자를 자동으로 만들어 준다는 뜻
 * 매개변수가 하나도 없는 생성자(예: public User(){} )를 말합니다.
 *
 * */

/**
 * @NoArgsConstructor(access = AccessLevel.PROTECTED) 에서 PROTECTED가 필요한 이유는?
 * JPA 리플렉션 때문이고, 외부에서 의미 없는 빈 객체 생성을 막으려면 protected가 좋아요.
 * */

/**
 * @NoArgsConstructor(access = PROTECTED)
 * 1) 왜 쓰나?
 * JPA 요구사항: 프록시/리플렉션으로 엔티티를 만들 수 있도록 파라미터 없는 기본 생성자가 필요합니다.
 * 캡슐화: public이면 아무 데서나 빈 객체가 만들어져 불완전 상태가 될 위험이 있어요. protected로 외부 오용을 줄입니다.
 *
 * 2) 원리/논리
 * 하이버네이트는 리플렉션으로 객체를 생성해 필드를 채웁니다. 이때 접근 가능한 무인자 생성자가 필요합니다.
 * Lombok이 컴파일 시점에 실제 protected User(){}를 생성합니다(바이트코드에 반영).
 *
 * 3) 실무 포인트
 * 팩토리/정적 생성자, 빌더 등을 통해 의미 있는 생성 경로만 열어 두고, 무인자 생성자는 JPA를 위한 기술적 구멍만 남기는 전략이 깔끔합니다.
 * */

@Table(name = "users")
/**
 * @Table(name="users")
 * 1) 왜 쓰나?
 * 예약어/충돌 회피: DB마다 user 가 예약어인 경우가 있어요. 안전하게 users 같은 명시적 이름을 쓰면 이식성과 안정성이 높아집니다.
 * 네이밍 전략 고정: 하이버네이트의 네이밍 전략이 바뀌거나 팀 컨벤션이 달라도, 테이블 이름이 흔들리지 않도록 명시합니다.
 *
 * 2) 원리/논리
 * 애플리케이션 기동 시 JPA가 엔티티 메타데이터를 만들고, @Table에 지정된 이름을 테이블 식별자로 사용합니다.
 * 스키마 자동생성(spring.jpa.hibernate.ddl-auto=create|update)을 쓰면, 이 이름으로 DDL을 생성합니다.
 *
 * 3) 팁
 * 팀 컨벤션(복수형 테이블명, 스네이크 케이스 등)을 애너테이션으로 고정해 두면, 장기 유지보수에서 헷갈림이 줄어요.
 * 멀티 모듈/멀티 서비스에서 스키마 충돌 방지에 유리합니다.
 * */

public class User extends BaseEntity {
    // 엔티티 클래스 선언!
    // BaseEntity를 "상속(extends)"해서 공통 필드를 재사용합니다.
    // 생성/수정 시각처럼 공통 속성/로직을 재사용하고
    // 감사(Auditing)를 표준화하려고 합니다.
    // 참고로 implements는 '인터페이스 구현'일 때 사용합니다.

    /**
     * extends 와 implements 의 차이는??
     * extends = 클래스 상속(코드 재사용 + 다형성, 단일 상속),
     * implements = 인터페이스 구현(규약 이행, 다중 구현 가능).
     **/

    /**
     * extends (상속)
     * 대상 : 클래스(추상 규현)
     * 개수 : 단일 상속만 가능
     * 목적 : 구현 재사용 + 다형성 확장
     * 강제 메서드 : 부모가 추상 메서드면 구현 강제
     * 상태(필드) : 필드 / 구현을 물려받음
     * 다이아몬드 문제 : 복잡. 자바는 클래스 다중속성 x
     * */

    /**
     * implements (인터페이스 구현)
     * 대상 : 인터페이스
     * 개수 : 여러 개 인터페이스 동시 구현 가능
     * 목적 : 행동 규약을 약속하고 DI/폴리모피즘에 유리
     * 강제 메서드 : 인터페이스의 추상 메서드 구현 필수 (default 제외)
     * 상태(필드) : 기본적으로 상태는 없음(상수 제외), 구현은 default/static만
     * 다이아몬드 문제 : 인터페이스 다중 구현 OK (충돌 시 명시적 선택)
     * */

    //// 상속: 공통 필드/로직을 재사용
    //public abstract class BaseEntity { /* createdAt, updatedAt ... */ }
    //public class User extends BaseEntity { /* 공통 필드 상속 */ }
    //
    //// 인터페이스 구현: 규약 제공
    //public interface Encryptable { String encrypt(String raw); }
    //public class BcryptEncryptor implements Encryptable {
    //    public String encrypt(String raw) { /* ... */ }
    //}

    /**
     * 상속 다이아몬드 문제(Diamond Problem)는 객체 지향 프로그래밍에서 다중 상속을 사용할 때,
     * 동일한 이름의 메서드를 여러 상위 클래스에서 상속받아 발생할 수 있는 호출 모호성 문제를 의미합니다.
     * 클래스 D가 클래스 B와 C를 상속받고, 이 B와 C가 모두 클래스 A를 상속받아,
     * A와 같은 이름의 메서드를 호출할 때 어떤 메서드가 실행될지 결정할 수 없는 문제가 바로 다이아몬드 문제이며,
     * 이를 해결하기 위해 자바는 클래스의 다중 상속을 제한하고,
     * 인터페이스의 다중 상속은 허용하지만 default 메서드 충돌 시 해결책을 명시하도록 합니다.
     *
     * 문제 발생 원인
     * 1) 클래스 구조:
     * A라는 조부모 클래스가 있고, B와 C라는 두 자식 클래스가 각각 A를 상속받습니다.
     * 2) 메서드 중복:
     * D라는 클래스가 B와 C를 동시에 상속받는데, A에 정의된 메서드와 동일한 이름의 메서드를 B와 C가 각각 오버라이딩(또는 상속받아) 가지고 있는 경우입니다.
     * 3) 호출의 모호성:
     * D 클래스가 해당 메서드를 호출하면, B를 통해 호출되는 것과 C를 통해 호출되는 것 중 어느 메서드가 실행되어야 하는지 컴파일러가 알 수 없어 오류가 발생합니다.
     *
     * 문제 해결 방안
     * 자바의 인터페이스 다중 상속: 자바는 클래스의 다중 상속은 허용하지 않지만, 인터페이스의 다중 상속은 허용합니다.
     * default 메서드 해결: 인터페이스에 default 메서드가 있는 경우, 다이아몬드 문제가 발생할 수 있는데, 이 경우 클래스에서 어떤 default 메서드를 사용할지 명확하게 재정의하여 충돌을 해결해야 합니다.
     * */

    // <<<----- 필드 ------>>>
    @Id
    // 엔티티 식별자
    // 이 필드를 기본키(PK)로 지정한다.
    // @Id 가 필드에 있으므로 필드 접근(Field Access) 전략을 사용한다.
    // 리플렉션으로 필드를 직접 읽고 쓴다

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK 생성 전략. 데이터베이스의 AUTO_INCREMENT(IDENTITY)를 사용.
    // 특징: ID를 받기 위해 INSERT가 즉시 나가거나(벤더/버전에 따라) flush 타이밍이 앞당겨질 수 있어 배치/성능 고려가 필요.

    /// @GeneratedValue의 전략은?
    //IDENTITY: DB AUTO_INCREMENT. 간편하지만 INSERT 타이밍이 앞당겨짐.
    // 장점: 설정 간단, DB가 키를 보장.
    // 단점: PK 확보 위해 INSERT가 빨리 나가서 쓰기 지연/배치 최적화가 제한될 수 있어요.
    //SEQUENCE: DB 시퀀스 사용. 배치/최적화에 유리.
    //TABLE: 별도 키 테이블. 이식성↑, 성능↓.
    //AUTO: 벤더별 기본 전략 자동 선택.
    private Long id;

    @Column(name = "user_name", nullable=false, length=50, unique=true)
    private String userName;
    // @Column을 생략하면 기본 컬럼 매핑. 보통 VARCHAR(255), nullable=true로 생성(디폴트).
    // 비즈니스 제약(UNIQUE/NOT NULL/길이)은 @Column(nullable=false, length=...), @UniqueConstraint 등으로 명시 추천.

    @Column(nullable=false, length=60, unique=true)
    // length 크기설정
    // size dto에서 설정.
    private String email;


    private String password;

    /**
     * @Column 제약들 (nullable, length, unique 등)
     * 1) 왜 쓰나?
     * 데이터 무결성: “이 컬럼은 비워지면 안 된다”, “이 정도 길이를 넘겨선 안 된다” 같은 업무 규칙을 DB 레벨로 강제합니다.
     * 성능 / 저장 효율: 길이 / 인덱스 선택은 저장 공간과 검색 성능에 직접 영향을 줍니다.
     *
     * 2) 원리/논리
     * @Column(nullable=false) → NOT NULL 제약을 DDL에 반영(스키마 생성 사용 시).
     * @Column(length=60) → VARCHAR(60) 같은 타입 길이를 DDL에 반영.
     * @Column(unique=true) → UNIQUE 인덱스/제약을 생성(벤더마다 구현은 다소 차이).
     *
     * 3) 팁/주의
     * @Column(unique=true)는 단일 컬럼 유니크일 땐 간편하지만, 복합 유니크(예: orgId + email)는 아래의 @Table(uniqueConstraints=...)를 쓰세요.
     * Bean Validation(@NotBlank, @Email, @Size)은 애플리케이션 레벨, @Column은 DB 레벨. 둘 다 쓰면 “빠른 피드백 + 최종 안전망”이 됩니다.
     * columnDefinition은 DB 의존적이라 이식성이 떨어집니다. 꼭 필요할 때만 사용.
     * */

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
