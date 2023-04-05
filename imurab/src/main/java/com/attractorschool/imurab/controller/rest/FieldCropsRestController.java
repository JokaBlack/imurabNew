package com.attractorschool.imurab.controller.rest;

import com.attractorschool.imurab.dto.fieldCrops.FieldCropsDto;
import com.attractorschool.imurab.entity.FieldCrops;
import com.attractorschool.imurab.mapper.fieldCrops.FieldCropsMapper;
import com.attractorschool.imurab.service.fieldCrops.impl.FieldCropsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/field-crops")
@RequiredArgsConstructor
public class FieldCropsRestController {
    private final FieldCropsServiceImpl fieldCropsService;
    private final FieldCropsMapper fieldCropsMapper;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FieldCropsDto> deleteDepartment(@PathVariable("id") FieldCrops fieldCrops){
        return ResponseEntity.ok().body(fieldCropsMapper.convertEntityDto(fieldCropsService.delete(fieldCrops)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FieldCropsDto>> getFieldCropsPage(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok().body(fieldCropsService.findFieldCropsPage(PageRequest.of(page, 10)).map(fieldCropsMapper::convertEntityDto));
    }
}
