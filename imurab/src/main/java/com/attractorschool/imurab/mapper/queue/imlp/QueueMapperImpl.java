package com.attractorschool.imurab.mapper.queue.imlp;

import com.attractorschool.imurab.dto.queue.*;
import com.attractorschool.imurab.entity.Queue;
import com.attractorschool.imurab.mapper.queue.QueueMapper;
import com.attractorschool.imurab.mapper.field.FieldMapper;
import com.attractorschool.imurab.mapper.user.OperatorMapper;
import com.attractorschool.imurab.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QueueMapperImpl implements QueueMapper {
    private final UserMapper userMapper;
    private final FieldMapper fieldMapper;
    private final OperatorMapper operatorMapper;
    @Override
    public QueueDto convertToQueueDto(Queue queue) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return QueueDto.builder()
                .id(queue.getId())
                .field(fieldMapper.convertToFieldDto(queue.getField()))
                .createdTime(queue.getCreatedTime())
                .status(queue.getStatus())
                .operator(operatorMapper.convertToOperatorDto(queue.getOperator()))
                .endWatering(queue.getFinishTime())
                .startWatering(queue.getStartTime())
                .build();
    }
    @Override
    public List<DayDto> convertToListDto(List<Day> days){
        List<DayDto> dayDto = new ArrayList<>();
        for (Day day : days) {
            dayDto.add(convertToDayDto(day));
        }
        return dayDto;
    }
    private DayDto convertToDayDto(Day day){
        return DayDto.builder()
                .date(day.getDate())
                .hours(convertToHoursDto(day.getHours()))
                .build();
    }

    private HourDto[] convertToHoursDto(Hour[] hours) {
        HourDto[] hourDtos = new HourDto[24];
        for (int i = 0; i < 24; i++) {
            hourDtos[i] = new HourDto(hours[i].getHour(), hours[i].isFree());
        }
        return hourDtos;
    }
}
