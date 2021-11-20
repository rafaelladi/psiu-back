package com.dietrich.psiu.excerpt.atendimento;

import com.dietrich.psiu.excerpt.chat.ChatProjection;
import com.dietrich.psiu.excerpt.organization.ProjectProjection;
import com.dietrich.psiu.excerpt.user.UserProjection;
import com.dietrich.psiu.excerpt.user.VolunteerProjection;
import com.dietrich.psiu.model.atendimento.Atendimento;
import com.dietrich.psiu.model.atendimento.StatusAtendimento;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Projection(name = "atendimentoProjection", types = Atendimento.class)
public interface AtendimentoProjection {
    Long getId();
//    UserProjection getUser();
//    VolunteerProjection getVolunteer();
//    ProjectProjection getProject();
    Date getCreationDateTime();
    Date getScheduledDate();
    String getNote();
    StatusAtendimento getStatus();
//    ChatProjection getChat();
}
