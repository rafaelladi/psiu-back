package com.dietrich.psiu.converter;

import org.springframework.data.domain.Page;

import java.util.List;

public interface Converter<E> {
    Object convert(Object o);
    Page<?> convertPage(Page<?> page);
    List<?> convertIterable(Iterable<?> items);
    List<?> convertList(List<?> list);
    E convertToEntity(Object o);
}
