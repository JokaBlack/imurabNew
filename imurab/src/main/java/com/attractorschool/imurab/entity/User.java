package com.attractorschool.imurab.entity;

import com.attractorschool.imurab.util.enums.Role;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "deleted = false")
public class User extends BaseIdEntity {
    @Column(name = "first_name", length = 25)
    private String firstName;

    @Column(name = "last_name", length = 25)
    private String lastName;

    @Column(name = "patronymic", length = 25)
    private String patronymic;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "avp_id")
    private AVP avp;

    @Column(name = "role", length = 25)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "locked")
    private boolean locked;

    @Column(name = "enabled")
    @Builder.Default
    private boolean enabled = true;

    @Column(name = "deleted")
    @Builder.Default
    private boolean deleted = false;

    @OneToMany(mappedBy = "user")
    private List<PasswordToken> passwordTokens;

    @OneToMany(mappedBy = "user")
    private List<DiscussionTopic> topics;

    @Column(name = "image")
    private String image;
}
