package com.attractorschool.imurab.service.token.impl;

import com.attractorschool.imurab.entity.PasswordToken;
import com.attractorschool.imurab.repository.PasswordTokenRepository;
import com.attractorschool.imurab.service.token.PasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordTokenServiceImpl implements PasswordTokenService {
    private final PasswordTokenRepository repository;

    @Override
    public PasswordToken create(PasswordToken passwordToken) {
        return saveOrUpdate(passwordToken);
    }

    @Override
    public PasswordToken saveOrUpdate(PasswordToken passwordToken) {
        return repository.save(passwordToken);
    }

    @Override
    public PasswordToken delete(PasswordToken passwordToken) {
        repository.delete(passwordToken);
        return passwordToken;
    }

    @Override
    public PasswordToken findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PasswordToken findByToken(String token) {
        return repository.findByToken(token).orElse(null);
    }

    @Override
    public PasswordToken findByUserEmail(String email) {
        return repository.findFirstByUserEmailOrderByIdDesc(email).orElse(null);
    }

    @Override
    public PasswordToken refreshToken(PasswordToken token) {
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiredAt(token.getCreatedAt().plusDays(1));
        token.setToken(UUID.randomUUID().toString());
        return saveOrUpdate(token);
    }
}
