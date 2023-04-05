package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.util.enums.FieldStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    Page<Field> findAllByUserAndArchivedIsFalse(Pageable pageable, User user);

    Page<Field> findAllByStatus(Pageable pageable, FieldStatus status);

    Optional<Field> findByIdAndArchivedIsFalse(Long id);

    Page<Field> findAllByDepartmentAvpAndStatus(Pageable pageable, AVP avp, FieldStatus status);

    Page<Field> findAllByDepartmentAndStatus(Pageable pageable, Department department, FieldStatus status);
}
