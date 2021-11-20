package com.dietrich.psiu.excerpt.chat;


import java.util.Date;

public interface MessageProjection {
    Long getId();
    String getContent();
    Date getSentAt();
}
