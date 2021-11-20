package com.dietrich.psiu.handler;

import com.dietrich.psiu.model.user.Person;
import com.dietrich.psiu.repository.user.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final PersonRepository personRepository;

    @Autowired
    public UserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.info("Loading user for email {}", email);
        Person person;
        person = personRepository.findByEmail(email);
        if(person != null && person.isActive()) {
            return new MyUserDetails(person);
        }

        LOGGER.error("No user found for email: {}", email);
        throw new UsernameNotFoundException("User not found!");
    }
}
