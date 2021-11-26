package com.dietrich.psiu.controller.auth;

import com.dietrich.psiu.dto.auth.AuthOkDTO;
import com.dietrich.psiu.dto.auth.LoginFormDTO;
import com.dietrich.psiu.dto.auth.RegisterFormDTO;
import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.Person;
import com.dietrich.psiu.model.user.User;
import com.dietrich.psiu.model.user.Volunteer;
import com.dietrich.psiu.repository.user.*;
import com.dietrich.psiu.util.JwtUtils;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final VolunteerRepository volunteerRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(PersonRepository personRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          UserRepository userRepository,
                          AdminRepository adminRepository,
                          VolunteerRepository volunteerRepository) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.adminRepository = adminRepository;
    }

//    @Transactional
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody @Valid LoginFormDTO form) {
//        LOGGER.info("Login: {}", form.getEmail());
//        Person person = personRepository.findByEmail(form.getEmail());
////        if(person != null && person.isActive() && passwordEncoder.matches(form.getPassword(), person.getPassword())) {
////            return ResponseEntity.ok(new AuthOkDTO(person, JwtUtils.getJwtToken(person)));
//        if(person != null && person.isActive() && passwordEncoder.matches(form.getPassword(), person.getPassword())) {
//            return ResponseEntity.ok(new AuthOkDTO(person,
//                    JwtUtils.getJwtToken(person)));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
//        }
//    }

    @Transactional
    @PostMapping("/login/user")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginFormDTO form) {
        LOGGER.info("Login: {}", form.getEmail());
        User user = userRepository.findByEmail(form.getEmail());
        if(user != null && user.isActive() && passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(new AuthOkDTO(user, JwtUtils.getJwtToken(user)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
    }

    @Transactional
    @PostMapping("/login/volunteer")
    public ResponseEntity<?> loginVolunteer(@RequestBody @Valid LoginFormDTO form) {
        LOGGER.info("Login: {}", form.getEmail());
        Volunteer volunteer = volunteerRepository.findByEmail(form.getEmail());
        if(volunteer != null && volunteer.isActive() && passwordEncoder.matches(form.getPassword(), volunteer.getPassword())) {
            return ResponseEntity.ok(new AuthOkDTO(volunteer, JwtUtils.getJwtToken(volunteer)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
    }

    @Transactional
    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(@RequestBody @Valid LoginFormDTO form) {
        LOGGER.info("Login: {}", form.getEmail());
        Admin admin = adminRepository.findByEmail(form.getEmail());
        if(admin != null && admin.isActive() && passwordEncoder.matches(form.getPassword(), admin.getPassword())) {
            return ResponseEntity.ok(new AuthOkDTO(admin, JwtUtils.getJwtToken(admin)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
    }

    @Transactional
    @PostMapping("/register/user")
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
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthOkDTO(user,
                JwtUtils.getJwtToken(user)));
    }
}
