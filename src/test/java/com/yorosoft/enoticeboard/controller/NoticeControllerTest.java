package com.yorosoft.enoticeboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yorosoft.enoticeboard.dto.NoticeDTO;
import com.yorosoft.enoticeboard.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.yorosoft.enoticeboard.util.TestDataFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoticeController.class)
@DisplayName("Unit tests of NoticeController")
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoticeService noticeService;

    @Test
    @DisplayName("GET a list with single Notice")
    void givenSingleNotice_whenGETNotices_thenGetSingleNoticeList() throws Exception {
        //given
        when(noticeService.findAll())
                .thenReturn(getNoticeListDTO(1L));

        mockMvc.perform(
                get("/notices/")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].title").value("Notice 1"))
                .andExpect(jsonPath("[0].description").value("Notice description 1"));
    }

    @Test
    @DisplayName("GET a Notice by Id")
    void givenNoticeId_whenGETNoticesById_thenGetSingleNotice() throws Exception {
        //given
        when(noticeService.findById(1L))
                .thenReturn(Optional.of(getSingleNoticeDTO(1L)));

        //when & then
        mockMvc.perform(
                get("/notices/1")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Notice 1"))
                .andExpect(jsonPath("$.description").value("Notice description 1"));
    }

    @Test
    @DisplayName("GET a Notice by Id and return 404 Not Found")
    void givenIncorrectNoticeId_whenGETNoticesById_thenGetNotFoundNotice() throws Exception {
        //given
        when(noticeService.findById(1L))
                .thenReturn(Optional.empty());

        //when & then
        mockMvc.perform(
                get("/notices/1")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST a Notice to create it")
    void givenNotice_whenPOSTSave_thenGetSavedNotice() throws Exception {
        //given
        NoticeDTO noticeDTO = getSingleNoticeDTO(1L);
        noticeDTO.setId(null);

        //when
        mockMvc.perform(
                post("/notices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(noticeDTO))
                .characterEncoding("utf-8")

        )

                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("DELETE a Notice by Id")
    void givenNoticeId_whenDELETENotice_thenNoticeIsDeleted() throws Exception {
        //given
        long noticeId = 1L;
        when(noticeService.findById(1L))
                .thenReturn(Optional.of(getSingleNoticeDTO(1L)));

        //when
        mockMvc.perform(
                delete("/notices/" + noticeId)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE a Notice by Id and return 404 HTTP Not Found")
    void givenNoticeId_whenDELETENotice_thenNoticeNotFound() throws Exception {
        //given
        long noticeId = 1L;
        when(noticeService.findById(1L))
                .thenReturn(Optional.empty());

        //when
        mockMvc.perform(
                delete("/notices/" + noticeId)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT a Notice by Id to update it")
    void givenIdAndUpdatedNotice_whenPUTUpdate_thenNoticeIsUpdated() throws Exception {
        //given
        long noticeId = 1L;
        NoticeDTO noticeDTO = getSingleNoticeDTO(1L);

        when(noticeService.findById(1L))
                .thenReturn(Optional.of(noticeDTO));

        NoticeDTO updatedNotice = noticeDTO;
        updatedNotice.setTitle("New Title");
        updatedNotice.setDescription("New Description");

        //when
        mockMvc.perform(
                put("/notices/" + noticeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(noticeDTO))
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Object with id 1 was updated."));

    }

    @Test
    @DisplayName("PUT a Notice by Id to update it and return 404 HTTP Not Found")
    void givenIdAndUpdatedNotice_whenPUTUpdate_thenNoticeNotFound() throws Exception {
        //given
        long noticeId = 1L;
        NoticeDTO noticeDTO = getSingleNoticeDTO(1L);

        when(noticeService.findById(1L))
                .thenReturn(Optional.empty());

        //when
        mockMvc.perform(
                put("/notices/" + noticeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(noticeDTO))
                        .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
