package com.yorosoft.enoticeboard.controller;

import com.yorosoft.enoticeboard.config.CrudControllerAPIPath;
import com.yorosoft.enoticeboard.dto.AuthorDTO;
import com.yorosoft.enoticeboard.service.AuthorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CrudControllerAPIPath.AUTHOR_BASE_URL)
public class AuthorController extends CrudController<AuthorDTO> {

    public AuthorController(AuthorService authorService) {
        super(authorService);
    }
}
