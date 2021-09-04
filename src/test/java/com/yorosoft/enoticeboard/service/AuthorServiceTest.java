package com.yorosoft.enoticeboard.service;

import com.yorosoft.enoticeboard.dto.AuthorDTO;
import com.yorosoft.enoticeboard.model.Author;
import com.yorosoft.enoticeboard.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.yorosoft.enoticeboard.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests of AuthorService class")
class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    @DisplayName("Get an empty list of Author")
    void givenNoAuthors_whenFindAll_thenGetEmptyList() {
        //given
        when(authorRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<AuthorDTO> authorList = authorService.findAll();

        //then
        Assertions.assertEquals(0, authorList.size());
    }

    @Test
    @DisplayName("Get a list with single Author")
    void givenSingleAuthors_whenFindAll_thenSingleAuthorList() {
        //given
        when(authorRepository.findAll())
                .thenReturn(getAuthorList(1L));

        //when
        List<AuthorDTO> authorList = authorService.findAll();

        //then
        Assertions.assertEquals(1, authorList.size());
        Assertions.assertEquals("First Name 1", authorList.get(0).getFirstName());
        Assertions.assertEquals("Last Name 1", authorList.get(0).getLastName());
    }

    @Test
    @DisplayName("Get a list of 500 Authors")
    void given500Authors_whenFindAll_then500AuthorList() {
        //given
        when(authorRepository.findAll())
                .thenReturn(getAuthorList(500L));

        //when
        List<AuthorDTO> authorList = authorService.findAll();

        //then
        Assertions.assertEquals(500, authorList.size());
    }

    @Test
    @DisplayName("Get an Author by Id")
    void givenSingleAuthor_whenFindById_thenGetSingleAuthor(){
        //given
        when(authorRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleAuthor(1L)));

        //when
        Optional<AuthorDTO> authorOpt = authorService.findById(1L);

        //then
        Assertions.assertTrue(authorOpt.isPresent());
        Assertions.assertNotNull(authorOpt.get().getId());
        Assertions.assertEquals("First Name 1", authorOpt.get().getFirstName());
        Assertions.assertEquals("Last Name 1", authorOpt.get().getLastName());
    }

    @Test
    @DisplayName("Get an Author by Id and return empty result")
    void givenNoAuthor_whenFindById_thenGetEmptyOptional(){
        //given
        when(authorRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<AuthorDTO> authorOpt = authorService.findById(1L);

        //then
        Assertions.assertFalse(authorOpt.isPresent());
    }

    @Test
    @DisplayName("Save an Author")
    void givenAuthor_whenSave_thenGetSavedAuthor() {
        //given
        when(authorRepository.save(any(Author.class)))
                .thenReturn(getSingleAuthor(1L));

        AuthorDTO authorDTO = getSingleAuthorDTO(1L);

        //when
        AuthorDTO savedAuthor = authorService.save(authorDTO);

        //then
        Assertions.assertNotNull(savedAuthor.getId());
    }

    @Test
    @DisplayName("Update an Author")
    void givenSavedAuthor_whenUpdate_thenAuthorIsUpdated() {
        //given
        when(authorRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleAuthor(1L)));

        when(authorRepository.save(any(Author.class)))
                .thenReturn(getSingleAuthor(2L));

        AuthorDTO toBeUpdatedAuthorDTO = getSingleAuthorDTO(2L);

        //when
        AuthorDTO updatedAuthorDTO = authorService.update(1L, toBeUpdatedAuthorDTO);

        //then
        Assertions.assertEquals(toBeUpdatedAuthorDTO.getFirstName(), updatedAuthorDTO.getFirstName());
        Assertions.assertEquals(toBeUpdatedAuthorDTO.getLastName(), updatedAuthorDTO.getLastName());
    }

}
