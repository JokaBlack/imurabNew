package com.attractorschool.imurab.mapper.fieldHistory;

import com.attractorschool.imurab.dto.fieldHistory.CreateFieldHistoryDto;
import com.attractorschool.imurab.dto.fieldHistory.EditFieldHistoryDto;
import com.attractorschool.imurab.dto.fieldHistory.FieldHistoryDto;
import com.attractorschool.imurab.entity.FieldHistory;

public interface FieldHistoryMapper {
    FieldHistory convertDtoToEntity(CreateFieldHistoryDto fieldHistoryDto);

    FieldHistory convertDtoToEntity(EditFieldHistoryDto fieldHistoryDto);

    FieldHistoryDto convertEntityToDto(FieldHistory fieldHistory);
}
