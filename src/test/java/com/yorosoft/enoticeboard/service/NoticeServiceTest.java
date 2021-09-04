package com.yorosoft.enoticeboard.service;


import com.yorosoft.enoticeboard.dto.NoticeDTO;
import com.yorosoft.enoticeboard.model.Notice;
import com.yorosoft.enoticeboard.repository.NoticeRepository;
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

import static com.yorosoft.enoticeboard.util.TestDataFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests of NoticeService class")
class NoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeService noticeService;

    @Test
    @DisplayName("Get an empty list of Notices")
    void givenNoNotices_whenFindAllNotices_thenGetEmptyList() {
        //given
        when(noticeRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<NoticeDTO> noticeList = noticeService.findAll();

        //then
        Assertions.assertEquals(0, noticeList.size());
    }

    @Test
    @DisplayName("Get a list with single Notice")
    void givenSingleNotices_whenFindAllNotices_thenSingleNoticeList() {
        //given
        when(noticeRepository.findAll())
                .thenReturn(getNoticeList(1L));

        //when
        List<NoticeDTO> noticeList = noticeService.findAll();

        //then
        Assertions.assertEquals(1, noticeList.size());
        Assertions.assertEquals("Notice 1", noticeList.get(0).getTitle());
        Assertions.assertEquals("Notice description 1", noticeList.get(0).getDescription());
    }

    @Test
    @DisplayName("Get a list of 500 Notices")
    void given500Notices_whenFindAllNotices_then500NoticeList() {
        //given
        when(noticeRepository.findAll())
                .thenReturn(getNoticeList(500L));

        //when
        List<NoticeDTO> noticeList = noticeService.findAll();

        //then
        Assertions.assertEquals(500, noticeList.size());
    }

    @Test
    @DisplayName("Get a Notice by Id")
    void givenSingleNotice_whenFindById_thenGetSingleNotice(){
        //given
        when(noticeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleNotice(1L)));

        //when
        Optional<NoticeDTO> noticeDTOOpt = noticeService.findById(1L);

        //then
        Assertions.assertTrue(noticeDTOOpt.isPresent());
        Assertions.assertEquals("Notice 1", noticeDTOOpt.get().getTitle());
        Assertions.assertEquals("Notice description 1", noticeDTOOpt.get().getDescription());
    }

    @Test
    @DisplayName("Get a Notice by Id and return empty result")
    void givenNoNotice_whenFindById_thenGetEmptyOptional(){
        //given
        when(noticeRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<NoticeDTO> noticeDTOOpt = noticeService.findById(1L);

        //then
        Assertions.assertFalse(noticeDTOOpt.isPresent());
    }

    @Test
    @DisplayName("Save a Notice")
    void givenNotice_whenSave_thenGetSavedNotice() {
        //given
        when(noticeRepository.save(any(Notice.class)))
                .thenReturn(getSingleNotice(1L));

        NoticeDTO noticeDTO = getSingleNoticeDTO(1L);

        //when
        NoticeDTO savedNotice = noticeService.save(noticeDTO);

        //then
        Assertions.assertNotNull(savedNotice.getId());
    }

    @Test
    @DisplayName("Update a Notice")
    void givenSavedNotice_whenUpdate_thenNoticeIsUpdated() {
        //given
        when(noticeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleNotice(1L)));

        when(noticeRepository.save(any(Notice.class)))
                .thenReturn(getSingleNotice(2L));

        NoticeDTO toBeUpdatedNoticeDTO = getSingleNoticeDTO(2L);

        //when
        NoticeDTO updatedNoticeDTO = noticeService.update(1L, toBeUpdatedNoticeDTO);

        //then
        Assertions.assertEquals(toBeUpdatedNoticeDTO.getTitle(), updatedNoticeDTO.getTitle());
        Assertions.assertEquals(toBeUpdatedNoticeDTO.getDescription(), updatedNoticeDTO.getDescription());
    }
}
