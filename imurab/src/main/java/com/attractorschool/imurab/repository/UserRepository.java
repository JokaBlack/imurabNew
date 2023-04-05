package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndDeletedIsFalse(String email);

    Boolean existsByEmail(String email);

    @Query("select u from User u where " +
            "lower(u.firstName) like lower(concat('%', ?1, '%')) or " +
            "lower(u.lastName) like lower(concat('%', ?1, '%'))  or " +
            "lower(u.patronymic) like lower(concat('%', ?1, '%')) or " +
            "lower(u.email) like lower(concat('%', ?1, '%')) or " +
            "lower(u.role) like lower(concat('%', ?1, '%'))")
    Page<User> findAllBySearch(String search, Pageable pageable);

    Boolean existsByPhone(String phone);

    Page<User> findAllByDeletedIsFalse(Pageable pageable);

    Page<User> findAllByAvp(Pageable pageable, AVP avp);

    @Query("select u from User u where " +
            "u.avp = ?1 and " +
            "(lower(u.firstName) like lower(concat('%', ?2, '%')) or " +
            "lower(u.lastName) like lower(concat('%', ?2, '%'))  or " +
            "lower(u.patronymic) like lower(concat('%', ?2, '%')) or " +
            "lower(u.email) like lower(concat('%', ?2, '%')) or " +
            "lower(u.role) like lower(concat('%', ?2, '%')))")
    Page<User> findAllByAvpAndSearch(AVP avp, String search, Pageable pageable);
}
