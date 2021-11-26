package com.dietrich.psiu.model.atendimento;

import com.dietrich.psiu.model.chat.Chat;
import com.dietrich.psiu.model.organization.Project;
import com.dietrich.psiu.model.user.User;
import com.dietrich.psiu.model.user.Volunteer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Atendimento")
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledDate;

    private String note;

    @Enumerated(EnumType.ORDINAL)
    private StatusAtendimento status;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "chat_id")
    private Chat chat = new Chat();
}
