package com.dietrich.psiu.controller.organization;

import com.dietrich.psiu.controller.Controller;
import com.dietrich.psiu.converter.Converter;
import com.dietrich.psiu.dto.organization.ProjectNewDTO;
import com.dietrich.psiu.model.organization.Project;
import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.Volunteer;
import com.dietrich.psiu.repository.organization.ProjectRepository;
import com.dietrich.psiu.repository.user.VolunteerRepository;
import com.dietrich.psiu.validator.annotation.UserResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController extends Controller<Project, ProjectNewDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectRepository projectRepository;
    private final VolunteerRepository volunteerRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository,
                             VolunteerRepository volunteerRepository,
                             Converter<Project> converter) {
        super(projectRepository, converter, LOGGER);
        this.projectRepository = projectRepository;
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> create(ProjectNewDTO projectNewDTO) {
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> update(Object o) {
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{projectId}/volunteers")
    public ResponseEntity<?> getVolunteers(@PathVariable("projectId") Long projectId, @UserResolver Admin admin) {
        List<Volunteer> volunteers = volunteerRepository.findAllByProjectIdAndOrganizationId(projectId, admin.getOrganization().getId());
        return ResponseEntity.ok(volunteers);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    @PutMapping("/{projectId}/volunteers/{volunteerId}")
    public ResponseEntity<?> addVolunteer(@PathVariable("projectId") Long projectId,
                                          @PathVariable("volunteerId") Long volunteerId,
                                          @UserResolver Admin admin) {
        Project project = projectRepository.findByIdAndOrganizationId(projectId, admin.getOrganization().getId());
        if(project == null)
            return ResponseEntity.notFound().build();
        if(project.getVolunteers().stream().anyMatch(v -> v.getId().equals(volunteerId)))
            return ResponseEntity.ok("Voluntário já cadastrado no projeto");
        Volunteer volunteer = volunteerRepository.findByIdAndOrganizationId(volunteerId, admin.getOrganization().getId());
        if(volunteer == null)
            return ResponseEntity.notFound().build();
        project.getVolunteers().add(volunteer);
        projectRepository.save(project);
        volunteer.getProjects().add(project);
        volunteerRepository.save(volunteer);
        return ResponseEntity.ok("Voluntario adicionado no projeto");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    @DeleteMapping("/{projectId}/volunteers/{volunteerId}")
    public ResponseEntity<?> removeVolunteer(@PathVariable("projectId") Long projectId,
                                          @PathVariable("volunteerId") Long volunteerId,
                                          @UserResolver Admin admin) {
        Project project = projectRepository.findByIdAndOrganizationId(projectId, admin.getOrganization().getId());
        if(project == null)
            return ResponseEntity.notFound().build();
        if(project.getVolunteers().stream().noneMatch(v -> v.getId().equals(volunteerId)))
            return ResponseEntity.ok("Voluntário não está cadastrado no projeto");
        Volunteer volunteer = volunteerRepository.findByIdAndOrganizationId(volunteerId, admin.getOrganization().getId());
        if(volunteer == null)
            return ResponseEntity.notFound().build();
        project.getVolunteers().remove(volunteer);
        projectRepository.save(project);
        volunteer.getProjects().remove(project);
        volunteerRepository.save(volunteer);
        return ResponseEntity.ok("Voluntário removido do projeto");
    }
}
