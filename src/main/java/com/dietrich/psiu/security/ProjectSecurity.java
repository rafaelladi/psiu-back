package com.dietrich.psiu.security;

import com.dietrich.psiu.model.organization.Project;
import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.Person;
import com.dietrich.psiu.model.user.Volunteer;
import org.springframework.stereotype.Component;

@Component("projectSec")
public class ProjectSecurity {
    public static boolean all(Project project, Person user) {
        if(user instanceof Admin admin) {
            return project.getOrganization().getId().equals(admin.getOrganization().getId());
        }
        if(user instanceof Volunteer volunteer) {
            return project.getOrganization().getId().equals(volunteer.getOrganization().getId());
        }
        return false;
    }
}
