package com.dietrich.psiu.dto.organization;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ProjectNewDTO {
    @NotEmpty
    private String name;
}
