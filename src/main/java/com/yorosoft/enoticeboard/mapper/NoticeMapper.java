package com.yorosoft.enoticeboard.mapper;

import com.yorosoft.enoticeboard.dto.NoticeDTO;
import com.yorosoft.enoticeboard.model.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

    NoticeDTO noticeToDto(Notice notice);
    Notice dtoToNotice(NoticeDTO noticeDTO);
}
