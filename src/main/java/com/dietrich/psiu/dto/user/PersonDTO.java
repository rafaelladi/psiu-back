package com.dietrich.psiu.dto.user;

import com.dietrich.psiu.model.user.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PersonDTO {
    private Long id;
    private String name;
    private String email;
    private boolean active;
    private Set<Role> roles;
}
