package com.dietrich.psiu.repository.organization;

import com.dietrich.psiu.excerpt.organization.ProjectProjection;
import com.dietrich.psiu.model.organization.Organization;
import com.dietrich.psiu.model.organization.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "projects", path = "projects", excerptProjection = ProjectProjection.class)
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
    @Override
    @RestResource
    @Query("select p from Project p where (?#{hasAuthority('VOLUNTEER')} = true and p.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and p.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true or " +
            "?#{hasAuthority('USER')} = true")
    Page<Project> findAll(Pageable pageable);

    @Override
    @RestResource
    @Query("select p from Project p where p.id = :id and ((?#{hasAuthority('VOLUNTEER')} = true and p.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and p.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true or " +
            "?#{hasAuthority('USER')} = true)")
    Optional<Project> findById(Long id);
}
