package com.dietrich.psiu.handler;

import com.dietrich.psiu.model.user.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
public class MyUserDetails implements UserDetails {
    private Person person;

    public MyUserDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return person.getRoles().stream().
                map(r -> new SimpleGrantedAuthority(r.getName())).
                collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return person.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return person.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return person.isActive();
    }
}
