package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.model.user.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {
    boolean existsByEmail(String email);
    Admin findByEmail(String email);
    List<Admin> findAllByOrganizationId(Long organizationId);
    Admin findByIdAndOrganizationId(Long id, Long organizationId);
}
