package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findDepartmentsByAvp(AVP avp);

    @Query("select d.maxParallelIrrigation from Department d where d.id = :id")
    int getMaxParallelIrrigationByDepartmentId(Long id);

    List<Department> findAllByAvp(AVP avp);
}
