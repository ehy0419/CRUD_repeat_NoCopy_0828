package com.crud_repeat_nocopy_0828.schedule.repository;

import com.crud_repeat_nocopy_0828.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
