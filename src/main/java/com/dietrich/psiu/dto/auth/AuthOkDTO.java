package com.dietrich.psiu.dto.auth;

import com.dietrich.psiu.model.user.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthOkDTO {
    private Person person;
    private String token;

    public AuthOkDTO() {
    }

    public AuthOkDTO(Person person, String token) {
        this.person = person;
        this.token = token;
    }
}
