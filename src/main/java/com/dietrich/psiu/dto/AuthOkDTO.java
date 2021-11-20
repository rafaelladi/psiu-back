package com.dietrich.psiu.dto;

import com.dietrich.psiu.model.user.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthOkDTO {
    private String person;
    private String token;

    public AuthOkDTO() {
    }

    public AuthOkDTO(String person, String token) {
        this.person = person;
        this.token = token;
    }
}
