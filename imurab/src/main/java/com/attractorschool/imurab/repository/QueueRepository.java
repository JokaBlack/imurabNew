package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.entity.Queue;
import com.attractorschool.imurab.util.enums.QStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface QueueRepository extends JpaRepository<Queue, Long> {
        List<Queue> findQueuesByDepartment_Id(Long departmentId);

        @Query("select q from Queue q" +
                " where Date( q.createdTime) >= :startDate" +
                " and q.status in ('CREATED', 'CONFIRMED')")
        List<Queue> getConfirmedAndCreatedQueue(Date startDate);

        @Query("select q from Queue q where q.status in ('CREATED', 'CONFIRMED')")
        Page<Queue> findAllActual (Pageable pageable);

        Page<Queue> findAllByStatus(QStatus status, Pageable pageable);

        Page<Queue> findAllByDepartmentAvpAndStatus(AVP avp, QStatus status, Pageable pageable);


        Page<Queue> findAllByDepartmentAndStatus(Department department, QStatus status, Pageable pageable);
}