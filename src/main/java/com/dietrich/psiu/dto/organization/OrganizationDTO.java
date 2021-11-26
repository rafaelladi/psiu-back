package com.dietrich.psiu.dto.organization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDTO {
    private Long id;
    private String name;
    private String email;
    private boolean active;
}
