package com.dietrich.psiu.excerpt.user;

import com.dietrich.psiu.model.user.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userProjection", types = User.class)
public interface UserProjection {
}
