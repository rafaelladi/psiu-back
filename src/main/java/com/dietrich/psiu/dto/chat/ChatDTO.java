package com.dietrich.psiu.dto.chat;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChatDTO {
    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    @NotEmpty
    private String userName;

    @NotNull
    private Long volunteerId;

    @NotEmpty
    private String volunteerName;
}
