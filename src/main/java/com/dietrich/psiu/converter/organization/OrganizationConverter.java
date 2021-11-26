package com.dietrich.psiu.converter.organization;

import com.dietrich.psiu.converter.Converter;
import com.dietrich.psiu.dto.organization.OrganizationDTO;
import com.dietrich.psiu.model.organization.Organization;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizationConverter implements Converter<Organization> {
    private final ModelMapper modelMapper;

    public OrganizationConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrganizationDTO convert(Object o) {
        return modelMapper.map(o, OrganizationDTO.class);
    }

    @Override
    public Page<OrganizationDTO> convertPage(Page<?> page) {
        return page.map(e -> modelMapper.map(e, OrganizationDTO.class));
    }

    @Override
    public List<OrganizationDTO> convertIterable(Iterable<?> items) {
        List<OrganizationDTO> list = new ArrayList<>();
        items.forEach(i -> list.add(modelMapper.map(i, OrganizationDTO.class)));
        return list;
    }

    @Override
    public List<OrganizationDTO> convertList(List<?> list) {
        return list.stream().map(o -> modelMapper.map(o, OrganizationDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Organization convertToEntity(Object organization) {
        return modelMapper.map(organization, Organization.class);
    }
}
