package com.dietrich.psiu.controller.organization;

import com.dietrich.psiu.controller.Controller;
import com.dietrich.psiu.converter.Converter;
import com.dietrich.psiu.dto.organization.OrganizationNewDTO;
import com.dietrich.psiu.dto.organization.ProjectNewDTO;
import com.dietrich.psiu.dto.user.PersonNewDTO;
import com.dietrich.psiu.model.organization.Organization;
import com.dietrich.psiu.model.organization.Project;
import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.Volunteer;
import com.dietrich.psiu.repository.organization.OrganizationRepository;
import com.dietrich.psiu.repository.organization.ProjectRepository;
import com.dietrich.psiu.repository.user.AdminRepository;
import com.dietrich.psiu.repository.user.RoleRepository;
import com.dietrich.psiu.repository.user.VolunteerRepository;
import com.dietrich.psiu.validator.annotation.UserResolver;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orgs")
public class OrganizationController extends Controller<Organization, OrganizationNewDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);
    private final OrganizationRepository organizationRepository;
    private final AdminRepository adminRepository;
    private final VolunteerRepository volunteerRepository;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganizationController(OrganizationRepository organizationRepository,
                                  Converter<Organization> converter,
                                  AdminRepository adminRepository,
                                  VolunteerRepository volunteerRepository,
                                  RoleRepository roleRepository,
                                  ProjectRepository projectRepository,
                                  PasswordEncoder passwordEncoder,
                                  ModelMapper modelMapper) {
        super(organizationRepository, converter, LOGGER);
        this.organizationRepository = organizationRepository;
        this.adminRepository = adminRepository;
        this.volunteerRepository = volunteerRepository;
        this.roleRepository = roleRepository;
        this.projectRepository = projectRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody @Valid OrganizationNewDTO dto) {
        LOGGER.info("Registering new org: {}", dto.getName());
        Organization org = getConverter().convertToEntity(dto);
        Admin admin = new Admin();
        admin.setMaster(true);
        admin.setName(dto.getAdminName());
        admin.setEmail(dto.getAdminEmail());
        admin.setPassword(passwordEncoder.encode(dto.getAdminPassword()));
        admin.getRoles().add(roleRepository.findByName("ADMIN"));
        admin.getRoles().add(roleRepository.findByName("MASTER"));
        admin = adminRepository.save(admin);
        org.getAdmins().add(admin);
        org = organizationRepository.save(org);
        return ResponseEntity.status(HttpStatus.CREATED).body(org);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admins")
    public ResponseEntity<?> getAdmins(@UserResolver Admin admin) {
        List<Admin> admins = adminRepository.findAllByOrganizationId(admin.getOrganization().getId());
        return ResponseEntity.ok(admins);
    }

    @PreAuthorize("hasAuthority('MASTER')")
    @PostMapping("/admins")
    public ResponseEntity<?> addAdmin(@RequestBody @Valid PersonNewDTO dto, @UserResolver Admin user) {
        Admin admin = modelMapper.map(dto, Admin.class);
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setOrganization(user.getOrganization());
        admin = adminRepository.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(admin);
    }

    @PreAuthorize("hasAuthority('MASTER')")
    @DeleteMapping("/admins/{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable("adminId") Long adminId, @UserResolver Admin user) {
        Admin admin = adminRepository.findByIdAndOrganizationId(adminId, user.getOrganization().getId());
        if(admin == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        admin.setActive(false);
        return ResponseEntity.ok("Administrador excluido");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/volunteers")
    public ResponseEntity<?> getVolunteers(@UserResolver Admin admin) {
        List<Volunteer> volunteers = volunteerRepository.findAllByOrganizationId(admin.getOrganization().getId());
        return ResponseEntity.ok(volunteers);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/volunteers")
    public ResponseEntity<?> addVolunteer(@RequestBody @Valid PersonNewDTO dto, @UserResolver Admin user) {
        Volunteer volunteer = modelMapper.map(dto, Volunteer.class);
        volunteer.setPassword(passwordEncoder.encode(dto.getPassword()));
        volunteer.setOrganization(user.getOrganization());
        volunteer = volunteerRepository.save(volunteer);
        return ResponseEntity.status(HttpStatus.CREATED).body(volunteer);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/volunteers/{volunteerId}")
    public ResponseEntity<?> deleteVolunteer(@PathVariable("volunteerId") Long volunteerId, @UserResolver Admin user) {
        Volunteer volunteer = volunteerRepository.findByIdAndOrganizationId(volunteerId, user.getOrganization().getId());
        if(volunteer == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        volunteer.setActive(false);
        return ResponseEntity.ok("Voluntário excluido");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/projects")
    public ResponseEntity<?> getProjects(@UserResolver Admin admin) {
        List<Project> projects = projectRepository.findAllByOrganizationId(admin.getOrganization().getId());
        return ResponseEntity.ok(projects);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/projects")
    public ResponseEntity<?> addProject(@RequestBody @Valid ProjectNewDTO dto, @UserResolver Admin user) {
        Project project = modelMapper.map(dto, Project.class);
        project.setOrganization(user.getOrganization());
        project = projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PreAuthorize("hasAuthority('MASTER')")
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") Long projectId, @UserResolver Admin user) {
        Project project = projectRepository.findByIdAndOrganizationId(projectId, user.getOrganization().getId());
        if(project == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        project.setActive(false);
        return ResponseEntity.ok("Projeto excluido");
    }

    @PreAuthorize("hasAuthority('MASTER')")
    @PostMapping("/projects/{projectId}")
    public ResponseEntity<?> activateProject(@PathVariable("projectId") Long projectId, @RequestParam("active") boolean active,
                                             @UserResolver Admin user) {
        Project project = projectRepository.findByIdAndOrganizationId(projectId, user.getOrganization().getId());
        if(project == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        project.setActive(active);
        return ResponseEntity.ok(project);
    }
}
