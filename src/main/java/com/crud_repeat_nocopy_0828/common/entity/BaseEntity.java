package com.crud_repeat_nocopy_0828.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@MappedSuperclass
// 하위 엔티티에 컬럼을 내려보냄 (상위 = BaseEntity, 하위 = 상속하는 유저, 일정, 댓글)
@EntityListeners(AuditingEntityListener.class)
// 스프링 데이터 JPA Auditing으로 자동 시간 기록
public abstract class BaseEntity {
    //public abstract class로 사용하는 경우는??

    @CreatedDate                                    // INSERT 시 1회 자동 세팅
    @Column(name = "created_at", updatable = false)
    // 공통 필드 (1)
    private LocalDate createdAt;                    // LocalDate라 시·분·초가 없어서 같은 날 여러 번 수정해도 값이 같을 수 있습니다.

    @LastModifiedDate                               // UPDATE 시 자동 갱신됩니다.
    @Column(name = "updated_at")
    // 공통 필드 (2)
    private LocalDate updatedAt;
}

// 스파르타 강의 : 챕터 2-8  : 객체지향 PART 2 - 상속
// https://teamsparta.notion.site/2-8-PART-2-1902dc3ef514819ca31bdd2ae7ee4e77
/**
 * 추상 클래스
 * 공통 기능을 제공하면서 하위 클래스에 특정 메서드 구현을 강제하기 위해 사용됩니다.
 * - 객체를 생성할 목적이 아니라 “설계도” 역할을 할때 적합합니다.
 * - `abstract` 키워드로 클래스를 선언하면 `추상클래스`입니다.
 * - `abstract` 키워드로 메서드를 선언하면 자식클래스에서 강제로 구현해야합니다.
 * - **추상클래스로 객체를 생성할 수 없습니다.**
 * - 일반 클래스처럼 변수와 메서드를 가질 수 있습니다.
 */

/**
 * 예시
 * abstract class Animal {
 *     private String name; // ✅ 변수선언가능
 *
 *     abstract void eat(); // ⚠️ 추상메서드: 상속 받은 자식은 강제 구현해야합니다.
 *
 *     public void sleep() { // ✅ 자식클래스에서 재사용가능합니다.
 *         System.out.println("쿨쿨");
 *     }
 * }
 *
 * public class Cat extends Animal {
 *
 *     @Override
 *     void eat() {
 *         System.out.println("냠냠"); //  ⚠️ 자식클래스에서 강제 구현해야합니다.
 *     }
 * }
 *
 * public class Main {
 *
 *     public static void main(String[] args) {
 *         Animal animal = new Animal(); // ❌ 추상클래스는 구현할 수 없습니다.
 *
 *         Cat cat = new Cat();
 *         cat.eat(); // ⚠️ 강제 구현한 메서드 사용
 *         cat.sleep(); // ✅ 부모클래스의 매서드 사용
 *     }
 * }
 */

/**
 * **추상클래스와 인터페이스 차이점**
 * - 상속이 계층적 구조를 선언하기 적합합니다.
 * - 인터페이스는 표준을 제공하는 데 적합합니다.
 * - 인터페이스는 인스턴스 변수를 선언할 수 없습니다.
 * - 계층적 구조를 표현하면서 공통 속성과 기능을 재사용할 때 추상클래스를 사용하는것이 적합합니다.
 */


//public abstract class BaseEntity
//인스턴스화 불가하다 즉, new BaseEntity()로 객체화 인스턴스화가 안된다.
//추상 메서드 정의 가능
//“상속 전용” 의도를 컴파일
//
//public class BaseEntity
//인스턴스화 가능
//추상 메서드 불가
//실수로 직접 생성/사용될 위험
