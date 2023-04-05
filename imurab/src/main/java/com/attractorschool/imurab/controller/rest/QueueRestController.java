package com.attractorschool.imurab.controller.rest;

import com.attractorschool.imurab.dto.queue.DayDto;
import com.attractorschool.imurab.dto.queue.QueueDto;
import com.attractorschool.imurab.dto.queue.Vacant;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.entity.Queue;
import com.attractorschool.imurab.mapper.queue.QueueMapper;
import com.attractorschool.imurab.security.UserPrincipal;
import com.attractorschool.imurab.service.queue.impl.QueueServiceImpl;
import com.attractorschool.imurab.util.enums.QStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/queue")
public class QueueRestController {
    private final QueueServiceImpl queueService;
    private final QueueMapper queueMapper;


    @GetMapping("/{departmentId}")
    public ResponseEntity<List<QueueDto>> queue(@PathVariable Long departmentId) {
        return new ResponseEntity<>(queueService.findAllByDepartmentId(departmentId), HttpStatus.OK);
    }

    @GetMapping("/vacant/{departmentId}")
    public ResponseEntity<List<DayDto>> availableTime(@PathVariable Long departmentId) {
        return new ResponseEntity<>(queueService.getFreeCurrentVacancies(departmentId), HttpStatus.OK);
    }


    @GetMapping("/vacantForField/{fieldId}")
    public ResponseEntity<?> createVacant(@PathVariable Long fieldId) {
        return queueService.createVacant(fieldId);
    }

    @PostMapping(value = "/create/{fieldId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewQueue(@PathVariable Long fieldId, @RequestBody @Valid Vacant vacant) {
        return queueService.createQueue(fieldId, vacant);
    }

    @PutMapping(value = "/confirm/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QueueDto> confirmQueue(@PathVariable("id") Queue queue, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(
                queueMapper.convertToQueueDto(
                        queueService.confirmUserQueue(queue, userPrincipal.getUser())
                ),
                HttpStatus.OK);
    }

    @PutMapping(value = "/reject/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QueueDto> rejectQueue(@PathVariable("id") Queue queue,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(
                queueMapper.convertToQueueDto(
                        queueService.rejectUserQueue(queue, userPrincipal.getUser())
                ),
                HttpStatus.OK);
    }

    @PutMapping(value = "/skip/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QueueDto> skipQueue(@PathVariable("id") Queue queue,
                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(
                queueMapper.convertToQueueDto(
                        queueService.skipUserQueue(queue, userPrincipal.getUser())
                ),
                HttpStatus.OK);
    }

    @PutMapping(value = "/finish/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QueueDto> finishQueue(@PathVariable("id") Queue queue) {
        return new ResponseEntity<>(
                queueMapper.convertToQueueDto(
                        queueService.finishUserQueue(queue)
                ),
                HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<QueueDto>> getQueue(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok().body(queueService.findActual(PageRequest.of(page, 10, Sort.by("createdTime").ascending())).map(queueMapper::convertToQueueDto));
    }

    @GetMapping(value = "/myActualQueue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QueueDto>> getMyQueue(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        return ResponseEntity.ok().body(queueService.findMyActualQueue(userPrincipal.getUser().getId()).stream()
                .map(queueMapper::convertToQueueDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<QueueDto>> getCreatedQueue(Pageable pageable) {
        return ResponseEntity.ok().body(queueService.findAllByStatus(QStatus.CREATED, pageable).map(queueMapper::convertToQueueDto));
    }

    @GetMapping(value = "/avp/{id}/review", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<QueueDto>> getCreatedQueueAvp(@PathVariable(value = "id") AVP avp, Pageable pageable) {
        return ResponseEntity.ok().body(queueService.findAllByDepartmentAvpAndStatus(avp, QStatus.CREATED, pageable).map(queueMapper::convertToQueueDto));
    }

    @GetMapping(value = "/departments/{id}/review", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<QueueDto>> getCreatedQueueDepartment(@PathVariable(value = "id") Department department, Pageable pageable) {
        return ResponseEntity.ok().body(queueService.findAllByDepartmentAndStatus(department, QStatus.CREATED, pageable).map(queueMapper::convertToQueueDto));
    }
}