package com.dietrich.psiu.service;

import com.dietrich.psiu.handler.MyUserDetails;
import com.dietrich.psiu.model.atendimento.Atendimento;
import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.Person;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("Access")
public class SecurityService {
    public static Long id(MyUserDetails userDetails) {
        System.out.println(userDetails.getPerson().getName());
        Person person = getUser();
        if(person == null)
            return 0L;
        else
            return person.getId();
    }

    public static boolean test(Long id) {
        System.out.println(id);
        return true;
    }

    public static int hasAuth(MyUserDetails userDetails, String authority) {
        return userDetails.getPerson().getRoles().stream().anyMatch(a -> a.getName().equals(authority)) ?
                1 : 0;
    }

    public static Long x(MyUserDetails userDetails) {
        if(userDetails.getPerson() instanceof Admin admin)
            return admin.getOrganization().getId();
        else return 0L;
    }

    public static Long y(Long a) {
        return a;
    }

    public static boolean filterUser(Optional<Atendimento> optionalAtendimento) {
        if(optionalAtendimento.isEmpty())
            return true;
        Person user = getUser();
        if(user == null)
            return false;
        Atendimento atendimento = optionalAtendimento.get();
        return atendimento.getUser().getId().equals(user.getId());
    }

    public static boolean filterVolunteer(Optional<Atendimento> optionalAtendimento) {
        if(optionalAtendimento.isEmpty())
            return true;
        Person user = getUser();
        if(user == null)
            return false;
        Atendimento atendimento = optionalAtendimento.get();
        return atendimento.getUser().getId().equals(user.getId());
    }

    private static Person getUser() {
        if(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken auth &&
                auth.getPrincipal() instanceof MyUserDetails user) {
            return user.getPerson();
        }
        return null;
    }
}
