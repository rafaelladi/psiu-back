package com.dietrich.psiu.excerpt.user;

import com.dietrich.psiu.model.user.SuperAdmin;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "superAdminProjection", types = SuperAdmin.class)
public interface SuperAdminProjection {
    Long getId();
    String getUsername();
}
