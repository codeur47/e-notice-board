package com.yorosoft.enoticeboard.service;


import com.yorosoft.enoticeboard.dto.NoticeDTO;
import com.yorosoft.enoticeboard.model.Notice;
import com.yorosoft.enoticeboard.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.yorosoft.enoticeboard.mapper.NoticeMapper.INSTANCE;


@Service
@RequiredArgsConstructor
public class NoticeService implements CrudService<NoticeDTO> {

    private final NoticeRepository noticeRepository;

    @Override
    public List<NoticeDTO> findAll() {
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        noticeRepository.findAll().forEach(notice -> noticeDTOList.add(INSTANCE.noticeToDto(notice)));
        return noticeDTOList;
    }

    @Override
    public Optional<NoticeDTO> findById(Long id) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);
        return noticeOptional.map(INSTANCE::noticeToDto);
    }

    @Override
    @Transactional
    public NoticeDTO save(NoticeDTO noticeDTO) {
        Notice notice = INSTANCE.dtoToNotice(noticeDTO);
        return INSTANCE.noticeToDto(noticeRepository.save(notice));
    }

    @Override
    @Transactional
    public void delete(Long id){
        noticeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public NoticeDTO update(Long id, NoticeDTO noticeDTO){
        Notice savedNotice = noticeRepository.findById(id).orElseThrow();
        Notice noticeToUpdate = INSTANCE.dtoToNotice(noticeDTO);

        savedNotice.setTitle(noticeToUpdate.getTitle());
        savedNotice.setDescription(noticeToUpdate.getDescription());

        return INSTANCE.noticeToDto(noticeRepository.save(savedNotice));
    }
}
