package com.dietrich.psiu.model.organization;

import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.Volunteer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "organization")
    private Set<Admin> admins = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "organization")
    private Set<Volunteer> volunteers = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "organization")
    private Set<Project> projects = new HashSet<>();

    private boolean active = false;
}
