package com.attractorschool.imurab.dto.user;

import com.attractorschool.imurab.util.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class OperatorDto {
    private Long id;
    private String firstName;

    private String lastName;

    private String patronymic;

    private String email;

    private Role role;
}
