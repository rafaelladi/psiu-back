package com.dietrich.psiu.repository.organization;

import com.dietrich.psiu.model.organization.Project;
import com.dietrich.psiu.model.user.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
    @Override
    @Query("select p from Project p where (?#{hasAuthority('VOLUNTEER')} = true and p.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and p.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true or " +
            "?#{hasAuthority('USER')} = true")
    Iterable<Project> findAll();

    @Override
    @PostAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('USER') or @projectSec.all(returnObject.get(), authentication.principal.person)")
    Optional<Project> findById(Long aLong);

    @Override
    @Query("select p from Project p where (?#{hasAuthority('VOLUNTEER')} = true and p.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and p.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true or " +
            "?#{hasAuthority('USER')} = true")
    Page<Project> findAll(Pageable pageable);

    List<Project> findAllByOrganizationId(Long organizationId);
    Project findByIdAndOrganizationId(Long id, Long organizationId);
}
