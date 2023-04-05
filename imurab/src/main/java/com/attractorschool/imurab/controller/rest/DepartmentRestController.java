package com.attractorschool.imurab.controller.rest;

import com.attractorschool.imurab.dto.department.DepartmentDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.mapper.department.DepartmentMapper;
import com.attractorschool.imurab.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentRestController {
    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DepartmentDto>> getDepartment(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok().body(departmentService.findAll(PageRequest.of(page, 10)).map(departmentMapper::convertToDepartmentDto));
    }

    @GetMapping("/avp/{id}")
    public ResponseEntity<List<DepartmentDto>> getDepartmentAvp(@PathVariable(value = "id") AVP avp) {
        return ResponseEntity.ok().body(departmentMapper.convertToDepartmentDto(departmentService.findAllByAvp(avp)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DepartmentDto> deleteDepartment(@PathVariable("id") Department department){
        return ResponseEntity.ok().body(departmentMapper.convertToDepartmentDto(departmentService.delete(department)));
    }
}
