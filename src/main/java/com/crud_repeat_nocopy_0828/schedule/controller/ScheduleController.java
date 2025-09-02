package com.crud_repeat_nocopy_0828.schedule.controller;

import com.crud_repeat_nocopy_0828.schedule.dto.request.ScheduleCreateRequestDto;
import com.crud_repeat_nocopy_0828.schedule.dto.request.ScheduleUpdateRequestDto;
import com.crud_repeat_nocopy_0828.schedule.dto.response.ScheduleResponseDto;
import com.crud_repeat_nocopy_0828.schedule.dto.response.ScheduleCreateResponseDto;
import com.crud_repeat_nocopy_0828.schedule.dto.response.ScheduleUpdateResponseDto;
import com.crud_repeat_nocopy_0828.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleCreateResponseDto> create(
            @RequestBody ScheduleCreateRequestDto request
    ) {
        return ResponseEntity.ok(scheduleService.save(request));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }



    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> findOne(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(scheduleService.findOne(id));
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<ScheduleUpdateResponseDto> update(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto request
    ) {
        return ResponseEntity.ok(scheduleService.update(id, request));
    }

    @DeleteMapping("schedules/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        scheduleService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
