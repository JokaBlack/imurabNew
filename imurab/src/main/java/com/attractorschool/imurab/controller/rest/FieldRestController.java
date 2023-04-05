package com.attractorschool.imurab.controller.rest;

import com.attractorschool.imurab.dto.field.FieldDto;
import com.attractorschool.imurab.dto.fieldHistory.CreateFieldHistoryDto;
import com.attractorschool.imurab.dto.fieldHistory.EditFieldHistoryDto;
import com.attractorschool.imurab.dto.fieldHistory.FieldHistoryDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.entity.FieldHistory;
import com.attractorschool.imurab.mapper.field.FieldMapper;
import com.attractorschool.imurab.mapper.fieldHistory.FieldHistoryMapper;
import com.attractorschool.imurab.security.UserPrincipal;
import com.attractorschool.imurab.service.field.FieldService;
import com.attractorschool.imurab.service.fieldHistory.FieldHistoryService;
import com.attractorschool.imurab.util.enums.FieldStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldRestController {
    private final FieldService fieldService;
    private final FieldMapper fieldMapper;
    private final FieldHistoryService historyService;
    private final FieldHistoryMapper historyMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FieldDto>> getFields(@AuthenticationPrincipal UserPrincipal userPrincipal, Pageable pageable) {
        return ResponseEntity.ok().body(fieldService.findAllByUser(pageable, userPrincipal.getUser()).map(fieldMapper::convertToFieldDto));
    }

    @GetMapping(value = "/avp/{id}/review", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FieldDto>> getFieldsAvp(@PathVariable(value = "id") AVP avp, Pageable pageable) {
        return ResponseEntity.ok().body(fieldService.findAllByAvp(pageable, avp, FieldStatus.CREATED).map(fieldMapper::convertToFieldDto));
    }

    @GetMapping(value = "/departments/{id}/review", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FieldDto>> getFieldDepartment(@PathVariable(value = "id") Department department, Pageable pageable) {
        return ResponseEntity.ok().body(fieldService.findAllByDepartment(pageable, department, FieldStatus.CREATED).map(fieldMapper::convertToFieldDto));
    }

    @DeleteMapping(value = "/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldDto> delete(@PathVariable(value = "id") Field field) {
        return ResponseEntity.ok().body(fieldMapper.convertToFieldDto(fieldService.delete(field)));
    }

    @DeleteMapping(value = "/{id}/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldDto> reject(@PathVariable(value = "id") Field field) {
        return ResponseEntity.ok().body(fieldMapper.convertToFieldDto(fieldService.reject(field)));
    }

    @PutMapping(value = "/{id}/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldDto> confirm(@PathVariable("id") Field field) {
        return ResponseEntity.ok().body(fieldMapper.convertToFieldDto(fieldService.confirm(field)));
    }

    @PostMapping(value = "/histories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldHistoryDto> create(@RequestBody @Valid CreateFieldHistoryDto fieldHistoryDto) {
        FieldHistory history = historyMapper.convertDtoToEntity(fieldHistoryDto);
        return ResponseEntity.ok().body(historyMapper.convertEntityToDto(historyService.create(history)));
    }

    @GetMapping(value = "/{fieldId}/histories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FieldHistoryDto>> getAll(@PathVariable(value = "fieldId") Field field, Pageable pageable) {
        return ResponseEntity.ok().body(historyService.findAllByField(pageable, field).map(historyMapper::convertEntityToDto));
    }

    @GetMapping(value = "/histories/{id}")
    public ResponseEntity<FieldHistoryDto> get(@PathVariable(value = "id") FieldHistory fieldHistory) {
        return ResponseEntity.ok().body(historyMapper.convertEntityToDto(fieldHistory));
    }

    @PutMapping(value = "/histories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldHistoryDto> update(@RequestBody @Valid EditFieldHistoryDto fieldHistoryDto) {
        FieldHistory history = historyMapper.convertDtoToEntity(fieldHistoryDto);
        return ResponseEntity.ok().body(historyMapper.convertEntityToDto(historyService.saveOrUpdate(history)));
    }

    @DeleteMapping(value = "/histories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldHistoryDto> delete(@PathVariable(value = "id") FieldHistory history) {
        return ResponseEntity.ok().body(historyMapper.convertEntityToDto(historyService.delete(history)));
    }

    @GetMapping(value = "/review")
    public ResponseEntity<Page<FieldDto>> getFieldsCreated(Pageable pageable) {
        return ResponseEntity.ok().body(fieldService.findAllByStatus(pageable, FieldStatus.CREATED).map(fieldMapper::convertToFieldDto));
    }
}
