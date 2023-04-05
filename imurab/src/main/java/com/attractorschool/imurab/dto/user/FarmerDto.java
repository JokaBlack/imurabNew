package com.attractorschool.imurab.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FarmerDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String email;
}
