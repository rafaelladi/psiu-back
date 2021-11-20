package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @PostAuthorize("false")
    Optional<User> findById(Long id);
}
