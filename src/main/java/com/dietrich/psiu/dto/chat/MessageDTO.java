package com.dietrich.psiu.dto.chat;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
public class MessageDTO {
    private Long id;
    @NotEmpty
    private String personName;
    @NotEmpty
    private String content;
    private Date sentAt;
}
