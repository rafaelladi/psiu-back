package com.dietrich.psiu.excerpt.user;

import com.dietrich.psiu.model.user.Admin;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "adminProjection", types = Admin.class)
public interface AdminProjection {
}
