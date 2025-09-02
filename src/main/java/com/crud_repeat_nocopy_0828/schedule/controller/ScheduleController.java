package com.crud_repeat_nocopy_0828.schedule.controller;

import com.crud_repeat_nocopy_0828.common.consts.Const;
import com.crud_repeat_nocopy_0828.schedule.dto.request.ScheduleSaveRequestDto;
import com.crud_repeat_nocopy_0828.schedule.dto.request.ScheduleUpdateRequestDto;
import com.crud_repeat_nocopy_0828.schedule.dto.response.ScheduleResponseDto;
import com.crud_repeat_nocopy_0828.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody ScheduleSaveRequestDto dto
    ) {
        ScheduleResponseDto body = scheduleService.save(userId, dto);
        // 201 Created + Location 헤더
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()           // /schedules
                .path("/{id}")                  // /{id}
                .buildAndExpand(body.getId())
                .toUri();

        return ResponseEntity.created(location).body(body);
        // 생성 시 ResponseEntity.created(…) 로 201 + Location 반환.
    }

    /// return ResponseEntity.ok(scheduleService.save(userId, dto)); 오류
    // userId 에서 1개의 인수가 필요하지만 2이(가) 발견되었습니다
    // 컴파일 에러의 “인수 개수 불일치”는 Controller가 서비스 메서드에 넘기는 파라미터와 Service 시그니처가 달라서 생기는 문제
    // 현재 Controller는 세션에서 userId를 꺼내 서비스에 전달하고, DTO에도 userId가 들어있습니다.
    // 그런데 Service 메서드 시그니처가 서로 다른 버전으로 섞여 있어요.
    // save(...)는 Service 쪽이 DTO 1개만 받도록 돼 있음 → Controller가 (userId, dto) 두 개를 넘겨서 에러.

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findOne(
            @PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequestDto request
    ) {
        // 동기 업데이트 → 200 OK(바디 포함) 권장
        return ResponseEntity.ok(scheduleService.update(userId, id, request));
    }

    /// return ResponseEntity.ok(scheduleService.update(userId, id, request)); 오류
    // id 에서 2개의 인수가 필요하지만 3이(가) 발견되었습니다
    // 컴파일 에러의 “인수 개수 불일치”는 Controller가 서비스 메서드에 넘기는 파라미터와 Service 시그니처가 달라서 생기는 문제
    // 현재 Controller는 세션에서 userId를 꺼내 서비스에 전달하고, DTO에도 userId가 들어있습니다.
    // 그런데 Service 메서드 시그니처가 서로 다른 버전으로 섞여 있어요.
    // update(...)도 Controller는 (userId, id, dto) 3개를 넘기지만, Service는 2개만 받도록 정의돼 있어 에러.

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long id
    ) {
        // 서비스 시그니처와 순서를 맞춰서 (loginUserId, scheduleId)
        scheduleService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}
