package com.dietrich.psiu.excerpt.user;

import com.dietrich.psiu.model.user.Role;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "roleProjection", types = Role.class)
public interface RoleProjection {
}
