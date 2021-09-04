package com.yorosoft.enoticeboard.service;

import com.yorosoft.enoticeboard.dto.BoardDTO;
import com.yorosoft.enoticeboard.model.Board;
import com.yorosoft.enoticeboard.repository.BoardRepository;
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
@DisplayName("Unit tests of BoardService class")
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
    @DisplayName("Get an empty list of Boards")
    void givenNoBoards_whenFindAll_thenGetEmptyList() {
        //given
        when(boardRepository.findAll())
                .thenReturn(Collections.emptyList());

        //when
        List<BoardDTO> boardList = boardService.findAll();

        //then
        Assertions.assertEquals(0, boardList.size());
    }

    @Test
    @DisplayName("Get a list with single Board")
    void givenSingleBoards_whenFindAll_thenSingleBoardList() {
        //given
        when(boardRepository.findAll())
                .thenReturn(getBoardList(1L, 5L));

        //when
        List<BoardDTO> boardList = boardService.findAll();

        //then
        Assertions.assertEquals(1, boardList.size());
        Assertions.assertEquals("Board 1", boardList.get(0).getTitle());
        Assertions.assertEquals(5, boardList.get(0).getNoticeList().size());
    }

    @Test
    @DisplayName("Get a list of 500 Boards")
    void given500Boards_whenFindAll_then500BoardList() {
        //given
        when(boardRepository.findAll())
                .thenReturn(getBoardList(500L, 5L));

        //when
        List<BoardDTO> boardList = boardService.findAll();

        //then
        Assertions.assertEquals(500, boardList.size());
    }

    @Test
    @DisplayName("Get a Board by Id")
    void givenSingleBoard_whenFindById_thenGetSingleBoard(){
        //given
        when(boardRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleBoard(1L, 1L)));

        //when
        Optional<BoardDTO> boardDTOOpt = boardService.findById(1L);

        //then
        Assertions.assertTrue(boardDTOOpt.isPresent());
        Assertions.assertNotNull(boardDTOOpt.get().getId());
        Assertions.assertEquals("Board 1", boardDTOOpt.get().getTitle());
        Assertions.assertEquals(1, boardDTOOpt.get().getNoticeList().size());
        Assertions.assertNotNull(boardDTOOpt.get().getNoticeList().get(0));
        Assertions.assertNotNull(boardDTOOpt.get().getNoticeList().get(0).getId());
        Assertions.assertEquals("Notice 1", boardDTOOpt.get().getNoticeList().get(0).getTitle());
        Assertions.assertEquals("Notice description 1", boardDTOOpt.get().getNoticeList().get(0).getDescription());
    }

    @Test
    @DisplayName("Get a Board by Id and return empty result")
    void givenNoBoard_whenFindById_thenGetEmptyOptional(){
        //given
        when(boardRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        //when
        Optional<BoardDTO> boardDTOOpt = boardService.findById(1L);

        //then
        Assertions.assertFalse(boardDTOOpt.isPresent());
    }

    @Test
    @DisplayName("Save a Board")
    void givenBoard_whenSave_thenGetSavedBoard() {
        //given
        when(boardRepository.save(any(Board.class)))
                .thenReturn(getSingleBoard(1L, 1L));

        BoardDTO boardDTO = getSingleBoardDTO(1L, 1L);

        //when
        BoardDTO savedBoard = boardService.save(boardDTO);

        //then
        Assertions.assertNotNull(savedBoard.getId());
    }

    @Test
    @DisplayName("Update a Board")
    void givenSavedBoard_whenUpdate_thenBoardIsUpdated() {
        //given
        when(boardRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getSingleBoard(1L, 1L)));

        when(boardRepository.save(any(Board.class)))
                .thenReturn(getSingleBoard(2L, 5L));

        BoardDTO toBeUpdatedBoardDTO = getSingleBoardDTO(2L, 5L);

        //when
        BoardDTO updatedBoardDTO = boardService.update(1L, toBeUpdatedBoardDTO);

        //then
        Assertions.assertEquals(toBeUpdatedBoardDTO.getTitle(), updatedBoardDTO.getTitle());
        Assertions.assertEquals(5L, updatedBoardDTO.getNoticeList().size());
    }
}
