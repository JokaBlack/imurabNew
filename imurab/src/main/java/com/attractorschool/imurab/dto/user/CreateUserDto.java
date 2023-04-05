package com.attractorschool.imurab.dto.user;

import com.attractorschool.imurab.util.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUserDto {
    @Size(min = 2, max = 25, message = "{user.firstName.error.size}")
    private String firstName;

    @Size(min = 2, max = 25, message = "{user.lastName.error.size}")
    private String lastName;

    private String patronymic;

    @NotEmpty(message = "{user.email.error.notEmpty}")
    @Email(message = "{user.email.error.email}")
    private String email;

    @Pattern(regexp = "^(\\+996)\\s?\\d{3}\\s?\\d{3}\\s?\\d{3}$", message = "{user.phone.error.pattern}")
    private String phone;

    @NotNull(message = "{user.avp.error.notNull}")
    private Long avpId;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "{user.password.error.pattern}")
    private String password;

    private String confirmPassword;

    private Role role;

    private MultipartFile image;
}
