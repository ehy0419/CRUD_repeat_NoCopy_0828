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

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // version1
//    @PostMapping
//    public ResponseEntity<ScheduleSaveResponseDto> create(
//            @RequestBody ScheduleSaveRequestDto request
//    ) {
//        return ResponseEntity.ok(scheduleService.save(request));
//    }

    // version2
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody ScheduleSaveRequestDto request
    ) {
        ScheduleResponseDto saved = scheduleService.save(request);
        return ResponseEntity.created(URI.create("/schedules/" + saved.getId())).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findOne(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(scheduleService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequestDto request
    ) {
        return ResponseEntity.ok(scheduleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        scheduleService.deleteById(id, userId);
        return ResponseEntity.noContent().build();
    }
}
