package com.yorosoft.enoticeboard.service;

import com.yorosoft.enoticeboard.dto.AuthorDTO;
import com.yorosoft.enoticeboard.mapper.AuthorMapper;
import com.yorosoft.enoticeboard.model.Author;
import com.yorosoft.enoticeboard.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService implements CrudService<AuthorDTO> {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDTO> findAll() {
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        authorRepository.findAll().forEach(author -> authorDTOList.add(authorMapper.authorToDto(author)));
        return authorDTOList;
    }

    @Override
    public Optional<AuthorDTO> findById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        return authorOptional.map(authorMapper::authorToDto);
    }

    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = authorMapper.dtoToAuthor(authorDTO);
        return authorMapper.authorToDto(authorRepository.save(author));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDTO update(Long id, AuthorDTO authorDTO) {
        Author savedAuthor = authorRepository.findById(id).orElseThrow();
        Author authorToUpdate = authorMapper.dtoToAuthor(authorDTO);

        savedAuthor.setFirstName(authorToUpdate.getFirstName());
        savedAuthor.setLastName(authorToUpdate.getLastName());

        return authorMapper.authorToDto(authorRepository.save(savedAuthor));
    }
}
