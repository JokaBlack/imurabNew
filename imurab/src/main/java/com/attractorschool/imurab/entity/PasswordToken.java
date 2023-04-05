package com.attractorschool.imurab.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PasswordToken extends BaseIdEntity {
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "token")
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
