package com.crud_repeat_nocopy_0828.schedule.service;

import com.crud_repeat_nocopy_0828.schedule.dto.request.ScheduleCreateRequestDto;
import com.crud_repeat_nocopy_0828.schedule.dto.request.ScheduleUpdateRequestDto;
import com.crud_repeat_nocopy_0828.schedule.dto.response.ScheduleCreateResponseDto;
import com.crud_repeat_nocopy_0828.schedule.dto.response.ScheduleResponseDto;
import com.crud_repeat_nocopy_0828.schedule.dto.response.ScheduleUpdateResponseDto;
import com.crud_repeat_nocopy_0828.schedule.entity.Schedule;
import com.crud_repeat_nocopy_0828.schedule.repository.ScheduleRepository;
import com.crud_repeat_nocopy_0828.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor        // 의존성 주입
// 생성자 주입
// 필드 주입
// 세터 주입
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional // 생성이기 때문에
    // 쿼리가 몇 번 나가느냐
    public ScheduleCreateResponseDto save(ScheduleCreateRequestDto request) {
        // 일정리퀘스트를 저장하는 것이 아니라 일정을 저장해야한다
        User user = request.fromUserId(userId);
        Schedule schedule = new Schedule(user, request.getTitle(),request.getContent());
        Schedule savedSchedule = scheduleRepository.save(Schedule);
        return new ScheduleCreateResponseDto(
                savedSchedule.getId(),
                user.getId(),
                savedSchedule.getUserName(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getId(),
                        schedule.getUser().getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getCreatedAt(),
                        schedule.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

    @Transactional
    public ScheduleUpdateResponseDto update(Long scheduleId, Long userId, ScheduleUpdateRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
        if (!userId.equals(schedule.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 스케줄만 수정할 수 있습니다.");
        }
        schedule.update(dto.getTitle(), dto.getContent());
        return new ScheduleUpdateResponseDto(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

    @Transactional
    public void deleteById(Long scheduleId, Long userId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
        if (!userId.equals(schedule.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 스케줄만 삭제할 수 있습니다.");
        }
        scheduleRepository.delete(schedule);
    }
}