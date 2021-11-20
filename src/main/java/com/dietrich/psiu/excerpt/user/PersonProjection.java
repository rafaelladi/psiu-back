package com.dietrich.psiu.excerpt.user;

import com.dietrich.psiu.model.user.Person;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "personProjection", types = Person.class)
public interface PersonProjection {
}
