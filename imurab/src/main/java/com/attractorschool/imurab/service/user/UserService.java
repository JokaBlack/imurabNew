package com.attractorschool.imurab.service.user;

import com.attractorschool.imurab.dto.user.UserDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends BaseService<User> {
    Page<User> findAll(Pageable pageable);

    Page<User> findAllBySearch(String search, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    User findByEmail(String email);

    User updatePassword(User user, String password);

    User updateUser(User user, UserDto userDto);

    User activation(User user);

    User deactivation(User user);

    Page<User> findAllByAvp(Pageable pageable, AVP avp);

    Page<User> findAllByAvpAndSearch(Pageable pageable, AVP avp, String search);
}
