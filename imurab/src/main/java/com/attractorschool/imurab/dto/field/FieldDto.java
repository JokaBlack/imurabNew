package com.attractorschool.imurab.dto.field;

import com.attractorschool.imurab.dto.department.DepartmentDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsDto;
import com.attractorschool.imurab.dto.user.FarmerDto;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FieldDto {
    private Long id;

    private String name;

    private BigDecimal size;

    private FieldCropsDto fieldCropsDto;

    private FarmerDto farmer;

    private DepartmentDto department;

    private String status;

    private int extraTime;
}
