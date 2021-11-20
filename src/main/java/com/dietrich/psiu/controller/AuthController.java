package com.dietrich.psiu.controller;

import com.dietrich.psiu.dto.AuthOkDTO;
import com.dietrich.psiu.dto.LoginFormDTO;
import com.dietrich.psiu.dto.RegisterFormDTO;
import com.dietrich.psiu.model.user.Person;
import com.dietrich.psiu.model.user.User;
import com.dietrich.psiu.repository.user.PersonRepository;
import com.dietrich.psiu.repository.user.RoleRepository;
import com.dietrich.psiu.repository.user.UserRepository;
import com.dietrich.psiu.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(PersonRepository personRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          UserRepository userRepository) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginFormDTO form) {
        LOGGER.info("Login: {}", form.getEmail());
        Person person = personRepository.findByEmail(form.getEmail());
//        if(person != null && person.isActive() && passwordEncoder.matches(form.getPassword(), person.getPassword())) {
//            return ResponseEntity.ok(new AuthOkDTO(person, JwtUtils.getJwtToken(person)));
        if(person != null && person.isActive()) {
            return ResponseEntity.ok(new AuthOkDTO(person.getName(), JwtUtils.getJwtToken(person)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterFormDTO form) {
        LOGGER.info("Registering a new user: {}", form.getEmail());
        if(personRepository.existsByEmail(form.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email já registrado!");
        }
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setName(form.getName());
        user.getRoles().add(roleRepository.findByName("USER"));
        user = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthOkDTO(user.getName(), JwtUtils.getJwtToken(user)));
    }
}
