package com.dietrich.psiu.model.chat;

import com.dietrich.psiu.model.user.User;
import com.dietrich.psiu.model.user.Volunteer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @OneToMany(mappedBy = "chat")
    @OrderBy("sentAt")
    private List<Message> messages;
}
