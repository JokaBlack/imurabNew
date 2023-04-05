package com.attractorschool.imurab.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEmailDto {
    @NotEmpty(message = "{user.email.error.notEmpty}")
    @Email(message = "{user.email.error.email}")
    private String email;
}
