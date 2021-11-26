package com.dietrich.psiu.repository.user;

import com.dietrich.psiu.model.user.SuperAdmin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends PagingAndSortingRepository<SuperAdmin, Long> {

}
