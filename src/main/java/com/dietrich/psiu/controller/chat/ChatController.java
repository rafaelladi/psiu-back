package com.dietrich.psiu.controller.chat;

import com.dietrich.psiu.controller.Controller;
import com.dietrich.psiu.dto.chat.ChatDTO;
import com.dietrich.psiu.dto.chat.MessageDTO;
import com.dietrich.psiu.model.chat.Chat;
import com.dietrich.psiu.model.user.Person;
import com.dietrich.psiu.model.user.User;
import com.dietrich.psiu.model.user.Volunteer;
import com.dietrich.psiu.repository.chat.ChatRepository;
import com.dietrich.psiu.validator.annotation.UserResolver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ChatController(ChatRepository chatRepository,
                          ModelMapper modelMapper) {
        this.chatRepository = chatRepository;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('VOLUNTEER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getChat(@PathVariable("id") Chat chat, @UserResolver Person person) {
        if(chat == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if(chat.getUser().getId().equals(person.getId()))
            return ResponseEntity.ok(modelMapper.map(chat, ChatDTO.class));
        if(chat.getVolunteer().getId().equals(person.getId()))
            return ResponseEntity.ok(modelMapper.map(chat, ChatDTO.class));
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('VOLUNTEER')")
    @GetMapping("/{id}/messages")
    public ResponseEntity<?> getMessages(@PathVariable("id") Chat chat, @UserResolver Person person) {
        if(chat == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if(chat.getUser().getId().equals(person.getId()))
            return ResponseEntity.ok(chat.getMessages().stream().map(m -> modelMapper.map(m, MessageDTO.class)).collect(Collectors.toList()));
        if(chat.getVolunteer().getId().equals(person.getId()))
            return ResponseEntity.ok(chat.getMessages().stream().map(m -> modelMapper.map(m, MessageDTO.class)).collect(Collectors.toList()));
        return ResponseEntity.badRequest().build();
    }
}
