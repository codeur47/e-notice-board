package com.yorosoft.enoticeboard.repository;

import com.yorosoft.enoticeboard.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
