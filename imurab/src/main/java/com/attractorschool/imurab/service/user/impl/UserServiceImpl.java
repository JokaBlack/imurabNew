package com.attractorschool.imurab.service.user.impl;

import com.attractorschool.imurab.dto.user.UserDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.repository.UserRepository;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AvpService avpService;
    private final PasswordEncoder encoder;

    @Override
    public User create(User user) {
        return saveOrUpdate(user);
    }

    @Override
    public User saveOrUpdate(User user) {
        return repository.save(user);
    }

    @Override
    public User delete(User user) {
        user.setDeleted(true);
        return saveOrUpdate(user);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAllByDeletedIsFalse(pageable);
    }

    @Override
    public Page<User> findAllBySearch(String search, Pageable pageable) {
        return repository.findAllBySearch(search, pageable);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmailAndDeletedIsFalse(email).orElse(null);
    }

    @Override
    public User updatePassword(User user, String password) {
        user.setPassword(encoder.encode(password));
        return saveOrUpdate(user);
    }

    @Override
    public User updateUser(User user, UserDto userDto) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPatronymic(userDto.getPatronymic());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setAvp(avpService.findById(userDto.getAvpId()));
        user.setRole(userDto.getRole());
        user.setImage((nonNull(userDto.getImage()) && isNotEmpty(userDto.getImage().getOriginalFilename()) ?
                userDto.getImage().getOriginalFilename() : user.getImage()));
        return saveOrUpdate(user);
    }

    @Override
    public User activation(User user) {
        user.setLocked(false);
        return saveOrUpdate(user);
    }

    @Override
    public User deactivation(User user) {
        user.setLocked(true);
        return saveOrUpdate(user);
    }

    @Override
    public Page<User> findAllByAvp(Pageable pageable, AVP avp) {
        return repository.findAllByAvp(pageable, avp);
    }

    @Override
    public Page<User> findAllByAvpAndSearch(Pageable pageable, AVP avp, String search) {
        return repository.findAllByAvpAndSearch(avp, search, pageable);
    }
}
