package com.crud_repeat_nocopy_0828.schedule.entity;

import com.crud_repeat_nocopy_0828.common.entity.BaseEntity;
import com.crud_repeat_nocopy_0828.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedules")                      // User 엔티티에 @Table 을 붙였으니 컨벤션에 맞게.
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * @JoinColumn과 @JoinColumns의 차이는?
     * @JoinColumn
     * 한 개의 외래키 FK를 가져올 때 사용된다.
     * @JoinColumns
     * 여러 개의 외래키 FK를 함께 묶을 때 사용된다.
     * */

    // @OneToMany : @ManyToOne이 존재하기 전까지는 절대로 존재할 수 없는 친구 - 양방향
    // @ManyToOne

    public Schedule(User user,
                    String userName, String title, String content) {
        this.user = user;
        this.userName = userName;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}