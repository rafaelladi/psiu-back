package com.dietrich.psiu.model.schedule;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer dayOfWeek; //0-6

    @Column(nullable = true)
    @Temporal(TemporalType.TIME)
    private Date start;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private Date finish;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
