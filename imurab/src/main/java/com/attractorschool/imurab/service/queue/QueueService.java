package com.attractorschool.imurab.service.queue;

import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.entity.Queue;
import com.attractorschool.imurab.service.BaseService;
import com.attractorschool.imurab.util.enums.QStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueueService extends BaseService<Queue> {
    @Override
    Queue create(Queue queue);

    @Override
    Queue saveOrUpdate(Queue queue);

    @Override
    Queue delete(Queue queue);

    @Override
    Queue findById(Long id);

    List<Queue> findAll();

    Page<Queue> findAll(Pageable pageable);

    Page<Queue> findAllByStatus(QStatus qStatus, Pageable pageable);

    Page<Queue> findAllByDepartmentAvpAndStatus(AVP avp, QStatus qStatus, Pageable pageable);

    Page<Queue> findAllByDepartmentAndStatus(Department department, QStatus qStatus, Pageable pageable);
}
