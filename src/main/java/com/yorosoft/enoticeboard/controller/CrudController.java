package com.yorosoft.enoticeboard.controller;

import com.yorosoft.enoticeboard.dto.BaseDTO;
import com.yorosoft.enoticeboard.exception.ResourceNotFoundException;
import com.yorosoft.enoticeboard.service.CrudService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.yorosoft.enoticeboard.config.CrudControllerAPIPath.*;

public abstract class CrudController<T extends BaseDTO> {

    private final CrudService<T> service;

    public CrudController(CrudService<T> crudService){
        this.service = crudService;
    }

    @GetMapping(GET_ALL)
    @ApiOperation(value = "List all")
    public ResponseEntity<List<T>> getAll(){
        List<T> all = service.findAll();
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(GET_BY_ID)
    @ApiOperation(value = "Get by Id")
    public ResponseEntity<T> getById(@PathVariable Long id){
        Optional<T> optionalT = service.findById(id);

        return optionalT.map(T ->
                        new ResponseEntity<>(T, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping(SAVE)
    @ApiOperation(value = "Create a new one")
    public ResponseEntity<T> save(@RequestBody T body){
        return new ResponseEntity<>(service.save(body), HttpStatus.CREATED);
    }

    @DeleteMapping(DELETE_BY_ID)
    @ApiOperation(value = "Delete by Id")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<T> optional = service.findById(id);

        return optional.map(T ->
                        new ResponseEntity<>("Object with the id " + id + " was deleted.", HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PutMapping(UPDATE_BY_ID)
    @ApiOperation(value = "Update by Id")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody T body){
        Optional<T> optional = service.findById(id);
        optional.ifPresent(n -> service.update(id, body));

        return optional.map(n ->
                        new ResponseEntity<>("Object with id " + id + " was updated.", HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
