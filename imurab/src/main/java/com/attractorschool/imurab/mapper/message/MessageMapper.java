package com.attractorschool.imurab.mapper.message;

import com.attractorschool.imurab.dto.message.CreateMessageDto;
import com.attractorschool.imurab.dto.message.EditMessageDto;
import com.attractorschool.imurab.dto.message.MessageDto;
import com.attractorschool.imurab.entity.Message;
import com.attractorschool.imurab.entity.User;

public interface MessageMapper {
    MessageDto convertEntityToDto(Message message);

    Message convertDtoToEntity(CreateMessageDto messageDto, User user);
}
