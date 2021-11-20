package com.dietrich.psiu.model.organization;

import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.Volunteer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "project_volunteers",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "volunteer_id")
    )
    private Set<Volunteer> volunteers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private boolean active = true;
}
