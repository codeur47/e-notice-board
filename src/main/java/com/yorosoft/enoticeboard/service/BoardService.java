package com.yorosoft.enoticeboard.service;

import com.yorosoft.enoticeboard.dto.BoardDTO;
import com.yorosoft.enoticeboard.mapper.BoardMapper;
import com.yorosoft.enoticeboard.model.Board;
import com.yorosoft.enoticeboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService implements CrudService<BoardDTO> {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    @Override
    public List<BoardDTO> findAll() {
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardRepository.findAll().forEach(board -> boardDTOList.add(boardMapper.boardToDto(board)));
        return boardDTOList;
    }

    @Override
    public Optional<BoardDTO> findById(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        return boardOptional.map(boardMapper::boardToDto);
    }

    @Override
    public BoardDTO save(BoardDTO boardDTO) {
        Board board = boardMapper.dtoToBoard(boardDTO);
        return boardMapper.boardToDto(boardRepository.save(board));
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public BoardDTO update(Long id, BoardDTO boardDTO) {
        Board savedBoard = boardRepository.findById(id).orElseThrow();
        Board boardToUpdate = boardMapper.dtoToBoard(boardDTO);

        savedBoard.setTitle(boardToUpdate.getTitle());
        savedBoard.setNoticeList(boardToUpdate.getNoticeList());

        return boardMapper.boardToDto(boardRepository.save(savedBoard));
    }
}
