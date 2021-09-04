package com.yorosoft.enoticeboard.config;

public interface CrudControllerAPIPath {
    String AUTHOR_BASE_URL = "/authors";
    String BOARD_BASE_URL = "/boards";
    String NOTICE_BASE_URL = "/notices";
    String GET_ALL = "/";
    String GET_BY_ID = "/{id}";
    String SAVE = "/";
    String DELETE_BY_ID = "/{id}";
    String UPDATE_BY_ID = "/{id}";
}
