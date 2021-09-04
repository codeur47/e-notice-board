package com.yorosoft.enoticeboard.controller;

import com.yorosoft.enoticeboard.config.CrudControllerAPIPath;
import com.yorosoft.enoticeboard.dto.BoardDTO;
import com.yorosoft.enoticeboard.service.BoardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CrudControllerAPIPath.BOARD_BASE_URL)
public class BoardController extends CrudController<BoardDTO> {

    public BoardController(BoardService boardService) {
        super(boardService);
    }
}
