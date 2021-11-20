package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.model.user.SuperAdmin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "superAdmins", path = "superAdmins")
public interface SuperAdminRepository extends PagingAndSortingRepository<SuperAdmin, Long> {
}
