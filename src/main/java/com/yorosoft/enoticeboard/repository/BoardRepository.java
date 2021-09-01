package com.yorosoft.enoticeboard.repository;

import com.yorosoft.enoticeboard.model.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
}
