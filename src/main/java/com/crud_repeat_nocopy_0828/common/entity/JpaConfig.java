package com.crud_repeat_nocopy_0828.common.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
// BaseEntity를 사용하기 위해서 @MappedSuperclass(BaseEntity) + @EnableJpaAuditing(main) 사용
public class JpaConfig {

    @Bean
    public DateTimeProvider auditingDateProvider() {
        // 감사 필드(LocalDate)용 — 한국 시간대의 '오늘' 날짜를 사용
        return () -> Optional.of(LocalDate.now(ZoneId.of("Asia/Seoul")));
    }
}
