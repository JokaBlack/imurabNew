package com.attractorschool.imurab.service.fieldCrops.impl;

import com.attractorschool.imurab.dto.fieldCrops.FieldCropsCreateDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsUpdateDto;
import com.attractorschool.imurab.entity.FieldCrops;
import com.attractorschool.imurab.mapper.fieldCrops.FieldCropsMapper;
import com.attractorschool.imurab.repository.FieldCropsRepository;
import com.attractorschool.imurab.service.fieldCrops.FieldCropsService;
import com.attractorschool.imurab.service.file.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@RequiredArgsConstructor
public class FieldCropsServiceImpl implements FieldCropsService {
    private final FieldCropsRepository fieldCropsRepository;

    private final FieldCropsMapper fieldCropsMapper;
    private final FileUploadService fileUploadService;
    private final FileUploadService uploadService;


    @Override
    public FieldCrops create(FieldCrops fieldCrops) {
        return saveOrUpdate(fieldCrops);
    }

    @Override
    public FieldCrops saveOrUpdate(FieldCrops fieldCrops) {
        return fieldCropsRepository.save(fieldCrops);
    }

    @Override
    public FieldCrops delete(FieldCrops fieldCrops) {
        fieldCrops.setDeleted(true);
        return saveOrUpdate(fieldCrops);
    }

    @Override
    public FieldCrops findById(Long id) {
        return fieldCropsRepository.findById(id).orElse(null);
    }

    public FieldCrops addFieldCrops(FieldCropsCreateDto fieldCropsCreateDto) {
        String directory = "fieldCrops";
        fileUploadService.uploadFile(fieldCropsCreateDto.getMultipartFile(), directory);
        return saveOrUpdate(fieldCropsMapper.convertFieldCropsCreateDtoToEntity(fieldCropsCreateDto));
    }

    @Override
    public Boolean existByName(String fieldCropsName) {
        List<FieldCrops> fieldCropsList = findAll();
        return fieldCropsList.stream().anyMatch(e -> e.getName().equalsIgnoreCase(fieldCropsName));
    }

    @Override
    public FieldCrops updateFieldCrops(FieldCrops fieldCrops, FieldCropsUpdateDto fieldCropsUpdateDto) {
        fieldCrops.setName(fieldCropsUpdateDto.getName());
        fieldCrops.setCoefficient(fieldCropsUpdateDto.getCoefficient());
        if (nonNull(fieldCropsUpdateDto.getMultipartFile()) && isNotEmpty(fieldCropsUpdateDto.getMultipartFile().getOriginalFilename())) {
            fieldCrops.setImgLink(fieldCropsUpdateDto.getMultipartFile().getOriginalFilename());
            uploadService.uploadFile(fieldCropsUpdateDto.getMultipartFile(), "fieldCrops");
        }
        return saveOrUpdate(fieldCrops);
    }

    @Override
    public Boolean existByNameOtherFieldCrops(FieldCropsUpdateDto fieldCropsUpdateDto) {
        List<FieldCrops> fieldCropsList = findAll();
        return fieldCropsList.stream()
                .filter(e -> e.getId() != fieldCropsUpdateDto.getId())
                .anyMatch(e-> e.getName().equalsIgnoreCase(fieldCropsUpdateDto.getName()));
    }

    @Override
    public List<FieldCrops> findAll() {
        return fieldCropsRepository.findAll();
    }

    public Page<FieldCrops> findFieldCropsPage(Pageable pageable){
        return fieldCropsRepository.findAll(pageable);
    }
}
