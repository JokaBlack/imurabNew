package com.attractorschool.imurab.service.security.impl;

import com.attractorschool.imurab.repository.UserRepository;
import com.attractorschool.imurab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserPrincipal(repository.findByEmailAndDeletedIsFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s doesn't exist", email))));
    }
}
