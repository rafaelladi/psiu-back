package com.dietrich.psiu.excerpt.organization;

import com.dietrich.psiu.model.organization.Project;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "projectProjection", types = Project.class)
public interface ProjectProjection {
    Long getId();
    String getName();
    boolean isActive();
}
