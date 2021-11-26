package com.dietrich.psiu.controller;

import com.dietrich.psiu.PsiuApplication;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface CrudController<E, C> {
    ModelMapper modelMapper = PsiuApplication.modelMapperBuilder();
    ResponseEntity<?> get();
    ResponseEntity<?> get(Pageable pageable);
    ResponseEntity<?> get(E e);
    ResponseEntity<?> create(C o);
    ResponseEntity<?> update(Object o);
}
