package com.yorosoft.enoticeboard.mapper;

import com.yorosoft.enoticeboard.dto.AuthorDTO;
import com.yorosoft.enoticeboard.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDTO authorToDto(Author author);
    Author dtoToAuthor(AuthorDTO authorDTO);
    List<Author> dtoSToAuthors(List<AuthorDTO> authorDTOS);
    List<AuthorDTO> authorsToDtoS(List<Author> authorDTOS);
}
