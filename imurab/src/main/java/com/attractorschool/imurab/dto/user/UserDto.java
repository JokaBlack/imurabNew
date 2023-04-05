package com.attractorschool.imurab.dto.user;

import com.attractorschool.imurab.util.enums.Role;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Long id;

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

    private Role role;

    private boolean locked;

    private String imageName;

    private MultipartFile image;
}


