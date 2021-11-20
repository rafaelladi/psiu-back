package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.excerpt.user.SuperAdminProjection;
import com.dietrich.psiu.model.user.SuperAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "superAdmins", path = "superAdmins", excerptProjection = SuperAdminProjection.class)
public interface SuperAdminRepository extends PagingAndSortingRepository<SuperAdmin, Long> {
    @Override
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    Page<SuperAdmin> findAll(Pageable pageable);

    @Override
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    Optional<SuperAdmin> findById(Long aLong);
}
