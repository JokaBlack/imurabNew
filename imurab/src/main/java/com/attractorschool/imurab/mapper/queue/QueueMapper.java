package com.attractorschool.imurab.mapper.queue;

import com.attractorschool.imurab.dto.queue.Day;
import com.attractorschool.imurab.dto.queue.DayDto;
import com.attractorschool.imurab.dto.queue.QueueDto;
import com.attractorschool.imurab.entity.Queue;

import java.util.List;

public interface QueueMapper {

    QueueDto convertToQueueDto(Queue queue);

    List<DayDto> convertToListDto(List<Day> days);
}
