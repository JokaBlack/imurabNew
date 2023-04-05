package com.attractorschool.imurab.dto.fieldCrops;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FieldCropsDto {
    private Long id;

    private String name;

    private BigDecimal coefficient;

    private String imgLink;
}
