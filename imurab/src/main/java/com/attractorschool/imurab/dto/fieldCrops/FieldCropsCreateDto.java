package com.attractorschool.imurab.dto.fieldCrops;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FieldCropsCreateDto {

    @Size(min = 2, max = 25, message = "{fieldCrops.name.error}")
    private String name;

    @NotNull(message = "{fieldCrops.coefficient.error}")
    @Positive(message = "{fieldCrops.coefficient.error}")
    private BigDecimal coefficient;

    @NotNull(message = "{fieldCrops.multipartFile.error}")
    private MultipartFile multipartFile;
}
