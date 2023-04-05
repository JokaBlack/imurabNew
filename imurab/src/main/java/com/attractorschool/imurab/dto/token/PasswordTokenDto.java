package com.attractorschool.imurab.dto.token;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PasswordTokenDto {
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    private LocalDateTime confirmedAt;

    private String token;
}
