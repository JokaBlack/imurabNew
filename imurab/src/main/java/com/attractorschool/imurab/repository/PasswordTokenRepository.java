package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    Optional<PasswordToken> findByToken(String token);

    Optional<PasswordToken> findFirstByUserEmailOrderByIdDesc(String email);
}
