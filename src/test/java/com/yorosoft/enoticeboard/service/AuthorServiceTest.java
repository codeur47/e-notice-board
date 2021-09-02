package com.yorosoft.enoticeboard.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yorosoft.enoticeboard.dto.AuthorDTO;
import com.yorosoft.enoticeboard.mapper.AuthorMapper;
import com.yorosoft.enoticeboard.model.Author;
import com.yorosoft.enoticeboard.repository.AuthorRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthorService.class})
@ExtendWith(SpringExtension.class)
public class AuthorServiceTest {
    @MockBean
    private AuthorMapper authorMapper;

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    public void testFindAll() {
        Iterable<Author> iterable = (Iterable<Author>) mock(Iterable.class);
        doNothing().when(iterable).forEach((java.util.function.Consumer<? super Author>) any());
        when(this.authorRepository.findAll()).thenReturn(iterable);
        assertTrue(this.authorService.findAll().isEmpty());
        verify(this.authorRepository).findAll();
        verify(iterable).forEach((java.util.function.Consumer<? super Author>) any());
    }

    @Test
    public void testFindById() {
        Author author = new Author();
        author.setLastName("Doe");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        author.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        author.setId(123L);
        author.setFirstName("Jane");
        Optional<Author> ofResult = Optional.<Author>of(author);
        when(this.authorRepository.findById((Long) any())).thenReturn(ofResult);
        when(this.authorMapper.authorToDto((Author) any())).thenReturn(new AuthorDTO("Jane", "Doe"));
        assertTrue(this.authorService.findById(123L).isPresent());
        verify(this.authorRepository).findById((Long) any());
        verify(this.authorMapper).authorToDto((Author) any());
    }

    @Test
    public void testSave() {
        Author author = new Author();
        author.setLastName("Doe");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        author.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        author.setId(123L);
        author.setFirstName("Jane");
        when(this.authorRepository.save((Author) any())).thenReturn(author);

        Author author1 = new Author();
        author1.setLastName("Doe");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        author1.setCreationDate(Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant()));
        author1.setId(123L);
        author1.setFirstName("Jane");
        AuthorDTO authorDTO = new AuthorDTO("Jane", "Doe");

        when(this.authorMapper.authorToDto((Author) any())).thenReturn(authorDTO);
        when(this.authorMapper.dtoToAuthor((AuthorDTO) any())).thenReturn(author1);
        assertSame(authorDTO, this.authorService.save(new AuthorDTO("Jane", "Doe")));
        verify(this.authorRepository).save((Author) any());
        verify(this.authorMapper).authorToDto((Author) any());
        verify(this.authorMapper).dtoToAuthor((AuthorDTO) any());
    }

    @Test
    public void testDelete() {
        doNothing().when(this.authorRepository).deleteById((Long) any());
        this.authorService.delete(123L);
        verify(this.authorRepository).deleteById((Long) any());
    }

    @Test
    public void testUpdate() {
        Author author = new Author();
        author.setLastName("Doe");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        author.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        author.setId(123L);
        author.setFirstName("Jane");
        Optional<Author> ofResult = Optional.<Author>of(author);

        Author author1 = new Author();
        author1.setLastName("Doe");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        author1.setCreationDate(Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant()));
        author1.setId(123L);
        author1.setFirstName("Jane");
        when(this.authorRepository.save((Author) any())).thenReturn(author1);
        when(this.authorRepository.findById((Long) any())).thenReturn(ofResult);

        Author author2 = new Author();
        author2.setLastName("Doe");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        author2.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.systemDefault()).toInstant()));
        author2.setId(123L);
        author2.setFirstName("Jane");
        AuthorDTO authorDTO = new AuthorDTO("Jane", "Doe");

        when(this.authorMapper.authorToDto((Author) any())).thenReturn(authorDTO);
        when(this.authorMapper.dtoToAuthor((AuthorDTO) any())).thenReturn(author2);
        assertSame(authorDTO, this.authorService.update(123L, new AuthorDTO("Jane", "Doe")));
        verify(this.authorRepository).findById((Long) any());
        verify(this.authorRepository).save((Author) any());
        verify(this.authorMapper).authorToDto((Author) any());
        verify(this.authorMapper).dtoToAuthor((AuthorDTO) any());
    }
}

