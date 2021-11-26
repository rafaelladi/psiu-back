package com.dietrich.psiu.repository.organization;

import com.dietrich.psiu.model.organization.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {
    @Override
//    @PostFilter("hasAuthority('SUPER_ADMIN') or hasAuthority('USER') or @organizationSec.all(filterObject, authentication.principal.person)")
    @Query("select o from Organization o where (?#{hasAuthority('VOLUNTEER')} = true and o.id = ?#{authentication.principal.person.organization.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and o.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true or " +
            "?#{hasAuthority('USER')} = true")
    Iterable<Organization> findAll();

    @Override
    @PostAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('USER') or @organizationSec.all(returnObject.get(), authentication.principal.person)")
    Optional<Organization> findById(Long id);

    @Override
    @Query("select o from Organization o where (?#{hasAuthority('VOLUNTEER')} = true and o.id = ?#{authentication.principal.person.organization.id}) or " +
            "(?#{hasAuthority('ADMIN')} = true and o.id = ?#{authentication.principal.person.organization.id}) or " +
            "?#{hasAuthority('SUPER_ADMIN')} = true or " +
            "?#{hasAuthority('USER')} = true")
    Page<Organization> findAll(Pageable pageable);
}
