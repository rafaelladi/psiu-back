package com.dietrich.psiu.converter.organization;

import com.dietrich.psiu.converter.Converter;
import com.dietrich.psiu.dto.organization.ProjectDTO;
import com.dietrich.psiu.model.organization.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectConverter implements Converter<Project> {
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProjectDTO convert(Object o) {
        return modelMapper.map(o, ProjectDTO.class);
    }

    @Override
    public Page<?> convertPage(Page<?> page) {
        return page.map(p -> modelMapper.map(p, ProjectDTO.class));
    }

    @Override
    public List<?> convertIterable(Iterable<?> items) {
        List<ProjectDTO> list = new ArrayList<>();
        items.forEach(i -> list.add(modelMapper.map(i, ProjectDTO.class)));
        return list;
    }

    @Override
    public List<?> convertList(List<?> list) {
        return list.stream().map(p -> modelMapper.map(p, ProjectDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Project convertToEntity(Object o) {
        return modelMapper.map(o, Project.class);
    }
}
