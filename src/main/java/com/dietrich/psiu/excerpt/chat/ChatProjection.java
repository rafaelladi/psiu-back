package com.dietrich.psiu.excerpt.chat;

import com.dietrich.psiu.excerpt.user.UserProjection;
import com.dietrich.psiu.excerpt.user.VolunteerProjection;
import com.dietrich.psiu.model.chat.Chat;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "chatProjection", types = Chat.class)
public interface ChatProjection {
    Long getId();
    UserProjection getUser();
    VolunteerProjection getVolunteer();
    
}
