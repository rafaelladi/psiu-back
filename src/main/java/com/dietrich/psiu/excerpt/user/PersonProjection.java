package com.dietrich.psiu.excerpt.user;

import com.dietrich.psiu.model.user.Person;
import com.dietrich.psiu.model.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Projection(name = "personProjection", types = Person.class)
public interface PersonProjection {
    Long getId();
    String getName();
    String getEmail();
    boolean isActive();
    Set<RoleProjection> getRoles();
}
