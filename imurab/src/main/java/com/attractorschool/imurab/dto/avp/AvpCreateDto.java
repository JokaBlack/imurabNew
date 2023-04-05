package com.attractorschool.imurab.dto.avp;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AvpCreateDto {
    @Size(min = 2, max = 50,message = "{avp.create.error}")
    private String name;
}
