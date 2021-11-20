package com.dietrich.psiu.model.user;

import com.dietrich.psiu.model.atendimento.Atendimento;
import com.dietrich.psiu.model.organization.Organization;
import com.dietrich.psiu.model.organization.Project;
import com.dietrich.psiu.model.schedule.Schedule;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Volunteer extends Person {
    @ManyToMany(mappedBy = "volunteers")
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "volunteer")
    private Set<Atendimento> atendimentos = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
