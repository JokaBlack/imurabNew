package com.attractorschool.imurab.entity;

import com.attractorschool.imurab.dto.queue.Vacant;
import com.attractorschool.imurab.util.enums.QStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue")
@NoArgsConstructor
@Data
public class Queue extends BaseIdEntity{
    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private User farmer;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "field_id", referencedColumnName = "id")
    private Field field;

    @Column(name = "created_time")
    private LocalDateTime createdTime;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "finish_time")
    private LocalDateTime finishTime;

    @Column(name = "status", length = 25)
    @Enumerated(EnumType.STRING)
    private QStatus status;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private User operator;

    public Queue(Field field, Vacant vacant) {
        this.farmer = field.getUser();
        this.department = field.getDepartment();
        this.field = field;
        this.createdTime = LocalDateTime.now();
        this.status = QStatus.CREATED;
        this.startTime = vacant.getStartDate().atTime(vacant.getStartTime(), 0);
        this.finishTime = this.startTime.plusHours(vacant.getHour());
    }

}