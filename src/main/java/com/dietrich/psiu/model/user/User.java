package com.dietrich.psiu.model.user;

import com.dietrich.psiu.model.atendimento.Atendimento;
import com.dietrich.psiu.model.chat.Chat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends Person {
    @OneToMany(mappedBy = "user")
    private Set<Atendimento> atendimentos = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Chat> chats = new HashSet<>();
}
