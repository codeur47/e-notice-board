package com.yorosoft.enoticeboard.mapper;

import com.yorosoft.enoticeboard.dto.BoardDTO;
import com.yorosoft.enoticeboard.model.Board;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardDTO boardToDto(Board board);
    Board dtoToBoard(BoardDTO boardDTO);
}
