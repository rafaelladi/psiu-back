package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.excerpt.user.VolunteerProjection;
import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "volunteers", path = "volunteers", excerptProjection = VolunteerProjection.class)
public interface VolunteerRepository extends PagingAndSortingRepository<Volunteer, Long> {
    @Override
    @RestResource
    @Query("select v from Volunteer v where (?#{hasAuthority('VOLUNTEER')} = true and v.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and v.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true")
    Page<Volunteer> findAll(Pageable pageable);

    @Override
    @RestResource
    @Query("select v from Volunteer v where v.id = :id and ((?#{hasAuthority('VOLUNTEER')} = true and :id = ?#{authentication.principal.person.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and v.organization.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true)")
    Optional<Volunteer> findById(Long id);
}
