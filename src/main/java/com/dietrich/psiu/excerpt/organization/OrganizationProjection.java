package com.dietrich.psiu.excerpt.organization;

import com.dietrich.psiu.model.organization.Organization;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "OrganizationProjection", types = Organization.class)
public interface OrganizationProjection {
}
