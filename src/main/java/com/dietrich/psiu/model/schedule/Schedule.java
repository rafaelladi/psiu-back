package com.dietrich.psiu.model.schedule;

import com.dietrich.psiu.model.user.Volunteer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "schedule")
    private List<Period> periods = new ArrayList<>();

    @OneToOne(mappedBy = "schedule")
    private Volunteer volunteer;
}
