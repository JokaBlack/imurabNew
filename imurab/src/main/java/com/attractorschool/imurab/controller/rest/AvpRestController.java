package com.attractorschool.imurab.controller.rest;

import com.attractorschool.imurab.dto.avp.AvpDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.service.avp.AvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avp")
@RequiredArgsConstructor
public class AvpRestController {
    private final AvpService avpService;
    private final AvpMapper avpMapper;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AvpDto> deleteAvp(@PathVariable("id")AVP avp){
        return ResponseEntity.ok().body(avpMapper.convertEntityToFrontDto(avpService.delete(avp)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AvpDto>> getAvpPage(@RequestParam(name = "page") int page) {
        return ResponseEntity.ok().body(avpService.findAvpPage(PageRequest.of(page, 10)).map(avpMapper::convertEntityToFrontDto));
    }
}
