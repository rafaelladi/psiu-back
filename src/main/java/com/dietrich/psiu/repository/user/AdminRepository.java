package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.model.user.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "admins", path = "admins")
public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {
}
