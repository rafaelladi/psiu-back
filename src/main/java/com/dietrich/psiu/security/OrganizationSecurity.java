package com.dietrich.psiu.security;

import com.dietrich.psiu.model.organization.Organization;
import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.Person;
import com.dietrich.psiu.model.user.Volunteer;
import org.springframework.stereotype.Component;

@Component("organizationSec")
public class OrganizationSecurity {
    public static boolean all(Organization org, Person user) {
        if(user instanceof Admin admin)
            return org.getId().equals(admin.getOrganization().getId());
        if(user instanceof Volunteer volunteer)
            return org.getId().equals(volunteer.getOrganization().getId());
        return false;
    }
}
