package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.excerpt.user.SuperAdminProjection;
import com.dietrich.psiu.excerpt.user.UserProjection;
import com.dietrich.psiu.model.user.Admin;
import com.dietrich.psiu.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users", excerptProjection = UserProjection.class)
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @RestResource
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    Page<User> findAll(Pageable pageable);

    @Override
    @RestResource
    @Query("select u from User u where u.id = :id and :id = ?#{authentication.principal.person.id}")
    Optional<User> findById(Long id);
}
