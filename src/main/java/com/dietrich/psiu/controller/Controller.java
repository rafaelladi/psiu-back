package com.dietrich.psiu.controller;

import com.dietrich.psiu.converter.Converter;
import lombok.Getter;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
public class Controller<E, C> implements CrudController<E, C> {
    private final Logger LOGGER;
    private final Converter<E> converter;
    private final PagingAndSortingRepository<E, Long> repository;

    public Controller(PagingAndSortingRepository<E, Long> repository,
                      Converter<E> converter,
                      Logger logger) {
        this.repository = repository;
        this.converter = converter;
        this.LOGGER = logger;
    }

    @Override
    @GetMapping
    @Transactional
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(converter.convertIterable(repository.findAll()));
    }

    @Override
    @Transactional
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> get(Pageable pageable) {
        return ResponseEntity.ok(converter.convertPage(repository.findAll(pageable)));
    }

    @Override
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") E e) {
        return ResponseEntity.ok(converter.convert(e));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> create(@RequestBody C c) {
        E e = converter.convertToEntity(c);
        e = repository.save(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(converter.convert(e));
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Object o) {
        return null;
    }
}
