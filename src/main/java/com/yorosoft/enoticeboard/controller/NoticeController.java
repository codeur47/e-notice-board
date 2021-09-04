package com.yorosoft.enoticeboard.controller;

import com.yorosoft.enoticeboard.config.CrudControllerAPIPath;
import com.yorosoft.enoticeboard.dto.NoticeDTO;
import com.yorosoft.enoticeboard.service.NoticeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CrudControllerAPIPath.NOTICE_BASE_URL)
public class NoticeController extends CrudController<NoticeDTO> {

    public NoticeController(NoticeService noticeService) {
        super(noticeService);
    }
}
