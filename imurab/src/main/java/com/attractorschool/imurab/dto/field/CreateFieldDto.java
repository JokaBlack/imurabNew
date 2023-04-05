package com.attractorschool.imurab.dto.field;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateFieldDto {
    @Size(min = 2, max = 100, message = "{field.name.error.size}")
    private String name;

    @NotNull(message = "{field.size.error.notNull}")
    @DecimalMin(value = "0.1", message = "{field.size.error.min}")
    private BigDecimal size;

    @NotNull(message = "{field.culture.error.notNull}")
    private Long fieldCropsId;

    @NotNull(message = "{field.department.error.notNull}")
    private Long departmentId;
}
