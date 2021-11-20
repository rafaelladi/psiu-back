package com.dietrich.psiu.repository.organization;

import com.dietrich.psiu.excerpt.organization.OrganizationProjection;
import com.dietrich.psiu.model.organization.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "organizations", path = "orgs", excerptProjection = OrganizationProjection.class)
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {
    @Override
    @RestResource
    @Query("select o from Organization o where (?#{hasAuthority('VOLUNTEER')} = true and o.id = ?#{authentication.principal.person.organization.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and o.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true or " +
            "?#{hasAuthority('USER')} = true")
    Page<Organization> findAll(Pageable pageable);

    @Override
    @RestResource
    @Query("select o from Organization o where o.id = :id and ((?#{hasAuthority('VOLUNTEER')} = true and o.id = ?#{authentication.principal.person.organization.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and o.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true or " +
            "?#{hasAuthority('USER')} = true)")
    Optional<Organization> findById(Long id);
}
