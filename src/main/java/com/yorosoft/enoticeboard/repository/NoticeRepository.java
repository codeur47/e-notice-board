package com.yorosoft.enoticeboard.repository;


import com.yorosoft.enoticeboard.model.Notice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {
}
