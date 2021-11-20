package com.dietrich.psiu.excerpt.user;

import com.dietrich.psiu.model.user.Volunteer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "volunteerProjection", types = Volunteer.class)
public interface VolunteerProjection extends PersonProjection {
}
